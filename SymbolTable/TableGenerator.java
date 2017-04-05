package SymbolTable;

import absyn.*;
import SymbolTable.*;

import java.io.FileWriter;


public class TableGenerator
{
  static int scope = 0;
  static Boolean drawTable = false;
  static int expectedReturn = -1;

  public int pos;

  final static int SPACES = 4;
  
  
  
  //Assembly stuff
  static int fpOff = 0,
             gpOff = 0;

  final static int PC_REG = 7,
                   GP_REG = 6,
                   FP_REG = 5;

  static final int ofpFO = 0,   //I don't think these change
                   retFO = -1,
                   initFO = -2;          
             
  static int paramsOffset = 0; //Each paramater stored needs to be 1 lower in memory than the last.
                               //This keeps track of that
                               
  static int pc = 0;
  static int jumpCount = 0;
  
  
  static FileWriter writer = null;
  static String outputFile = "UNPATCHED.asm";
  ////////
  
             
  static public void indent( int spaces ) {
    for( int i = 0; i < spaces; i++ )  System.out.print( " " );
  }
  
  static public void generateTable( DecList tree, int spaces, Boolean draw ) {
      
      try
      {
        writer = new FileWriter(outputFile); 
      }
      catch(Exception ex)
      {
        
      }
      
      //Patcher.Run("Patcher/test.txt");
      //if(true) return;
      
      drawTable = draw;
      SymTable.insert("input", 0, 1, 0, gpOff, 4); 
      gpOff--;
      SymTable.insert("output", 1, 1, 0, gpOff, 7);
      gpOff--;
      
      //Just commented out to keep output tidy for debugging
      comment("Standard prelude:");
      emitRM("LD", 6, 0, 0);
      emitRM("LDA", 5, 0, 6);
      emitRM("ST", 0, 0, 0);
      //Input
      emitRM("LDA", PC_REG, 7, PC_REG, "jump around the IO routines");
      emitRM("ST", 0, -1, 5);
      emitRO("IN", 0, 0, 0);
      emitRM("LD", 7, -1, 5);
      //output
      emitRM("ST", 0, -1, 5);
      emitRM("LD", 0, -2, 5);
      emitRO("OUT", 0, 0, 0);
      emitRM("LD", 7, -1, 5);
     // emitRM("LDA", 7, 7, 7);   
      comment("End of standard prelude");

      
      
      
      while (tree != null) {
          generateTable( tree.head, spaces );
          tree = tree.tail;
      }
      

      comment("\n*\n*Finale");
      emitRM("ST", 5, -1, 5, "push ofp");
      emitRM("LDA", 5, -1, 5, "push frame");
      emitRM("LDA", 0, 1, 7, "load ac with ret ptr");
      //emitRM("LDA", 7, -35, 7, "jump to main loc");
      Entry e = SymTable.lookup("main");

      int difference = e.pc - pc; //Calculate the distance between the two
                                    //and use it to jump
      emitRM("LDA", PC_REG, difference - 1, PC_REG, "jump to main");
      
      emitRM("LD", 5, 0, 5, "pop frame");
      emitRO("HALT", 0, 0, 0, "terminate");
      comment("End of finale");
      
      try
      {
        writer.close();
      }
      catch(Exception ex)
      {
      
      }
      
  }
  
  static public void generateTable( ExpList tree, int spaces ) {
  
      if(spaces == 8) paramsOffset = 0; //reset for a new function's parameter list
      
      while (tree != null) 
      {
          generateTable( tree.head, spaces );
          
                    
          if(spaces == 8)
          {
            emitRM("ST", 0, fpOff + initFO + paramsOffset, FP_REG, "(call seq) move r0 to memory");
            paramsOffset--;
          }
          
          tree = tree.tail;
      }
      
  }
  
  static public void generateTable( VarDecList tree, int spaces ) {
      
      while (tree != null) 
      {
          generateTable( tree.head, spaces );   
          tree = tree.tail;
      }
      
  }

  static private void generateTable( Dec tree, int spaces ) {
      
    if( tree instanceof FunctionDec )
      generateTable( (FunctionDec)tree, spaces );
    else if ( tree instanceof VarDec )
        generateTable( (VarDec)tree, spaces );
    else {
    }
    
  }
  
  static private void generateTable( VarDec tree, int spaces ) {
      
      if ( tree instanceof SimpleDec )
        generateTable( (SimpleDec)tree, spaces );
      else if ( tree instanceof ArrayDec )
        generateTable( (ArrayDec)tree, spaces );
      else {
      }
      
  }
  
  static private int generateTable( Var tree, int spaces ) {
      
      if ( tree instanceof SimpleVar )
        return generateTable( (SimpleVar)tree, spaces );
      else if ( tree instanceof IndexVar )
        return generateTable( (IndexVar)tree, spaces );
      else {
        return -1;
      }
      
  }
  
  static private int generateTable( Exp tree, int spaces ) {
      
      if (tree instanceof VarExp )
        return generateTable( (VarExp)tree, spaces );
      else if ( tree instanceof IntExp )
        return generateTable( (IntExp)tree, spaces );
      else if ( tree instanceof CallExp )
        return generateTable( (CallExp)tree, spaces );
      else if ( tree instanceof OpExp )
        return generateTable( (OpExp)tree, spaces );
      else if ( tree instanceof AssignExp )
        return generateTable( (AssignExp)tree, spaces );
      else if ( tree instanceof IfExp )
        return generateTable( (IfExp)tree, spaces );
      else if ( tree instanceof WhileExp )
        return generateTable( (WhileExp)tree, spaces );
      else if ( tree instanceof CompoundExp )
        return generateTable( (CompoundExp)tree, spaces );
      else if ( tree instanceof ReturnExp )
        return generateTable( (ReturnExp)tree, spaces );
      else {
        return -1;
      }
  }
  
  static private void generateTable( ArrayDec tree, int spaces ) {
      
      int array_size = 1;
      int reg = spaces;
      int offset = SymTable.getOffset(tree.name);
      generateTable( tree.typ, spaces );
      if ( tree.size != null) array_size = tree.size.value;
      if(!SymTable.insert(tree.name, tree.typ.typ, array_size, scope, fpOff, pc))
      {
        for (int i = 0; i < array_size; i++) {
            System.out.println("Added " + i);
            fpOff--;
        }
        System.err.println("\nError: Line " + (tree.pos + 1) + ". Redefinition of '" + tree.name + "'.");
      }
      
  }
  
  static private int generateTable( AssignExp tree, int spaces ) {
       
      comment("AssignExp"); 
    
      int LHS = generateTable(tree.lhs, -1);
      int RHS = generateTable(tree.rhs, 0); //This will store the result of RHS in a register (I chose r0 for now)
      
      if (LHS != RHS) 
      {
        errorMessage(tree.pos,"Type mismatch", LHS, RHS);
        return -1;
      }
      
      Var v = tree.lhs.variable;
      //issue here - calculating iv.index without knowing values in preprocessor
      if (v instanceof IndexVar) {
          /*IndexVar iv = (IndexVar)tree.lhs.variable;
          String vname = iv.name;
          int offset = SymTable.getOffset(vname) - iv.index;
          emitRM("ST", 1, 
          emitRM("ST", 0, offset, FP_REG, "move r0 in to " + vname + "[" + iv.index + "]");*/
      } else {
          SimpleVar sv = (SimpleVar)tree.lhs.variable;
          String vname = sv.name;
          int offset = SymTable.getOffset(vname);
          emitRM("ST", 0, offset, FP_REG, "move r0 in to " + vname);
      
      }
     return LHS;
      
  }
  
  static private int generateTable( CallExp tree, int spaces ) {
      comment("<- call " + tree.func);  
      generateTable( tree.args, 8 );
      
      Entry e = SymTable.lookup(tree.func);
      if(e == null)
      {
        System.err.println("");
        System.err.println("Error:  Line " + (tree.pos+1) + ". Symbol '" + tree.func + "' not defined.");
        return -1;
      }
      else
      {
        //is this right at all? (lec. 11, slide 9)


        
        emitRM("ST", FP_REG, fpOff--, FP_REG, "store current fp");
        emitRM("LDA", FP_REG, fpOff + 1, FP_REG, "push new frame");
        emitRM("LDA", 0, 1, PC_REG, "save return in r0");
        
        int difference = e.pc - pc; //Calculate the distance between the two
                                    //and use it to jump
        emitRM("LDA", PC_REG, difference - 1, PC_REG, "jump to " + tree.func);
        
        emitRM("LD", FP_REG, ofpFO, FP_REG, "pop current frame");
        return e.type;
      }
    
    
      
  }
  
  static private int generateTable( CompoundExp tree, int spaces ) {
  
      enterScope("CompoundExp");
      comment("compound statement");
      
      generateTable( tree.decs, spaces );
      generateTable( tree.exps, spaces );
      leaveScope("CompoundExp");

      
      return -1; //does nothing, only to make generateTable(Exp) work
      
  }
  
  static private void generateTable( FunctionDec tree, int spaces ) {
      
      

      
      comment("processing function: " + tree.func); 
      fpOff = -2;



      expectedReturn = generateTable(tree.result, spaces); 
      if(!SymTable.insert(tree.func, tree.result.typ, 1, scope, gpOff, pc + 1))
      {
        System.err.println("\nError: Line " + (tree.pos+1) + ". Redefinition of '" + tree.func + "'.");
      }
      gpOff--;     
      
      
      int jumpNum = jumpCount; //save the jump number
      
      emitJUMP(jumpNum, pc, "Jump over " + tree.func + " body");
      pc++;
      jumpCount++;
      emitRM("ST", 0, retFO, FP_REG, "move return addr from r0 to memory");
      
      enterScope(tree.func);
      generateTable( tree.params, 8 );
      scope--;
      generateTable( tree.body, spaces );
      
      if(drawTable)
      {
            System.err.println("Exiting " + tree.func + ".\nSymbol Table at exit: ");
            SymTable.print();
      }
      
      emitRM("LD", PC_REG, retFO, FP_REG);
      emitLAND(jumpNum, pc);
      
  }
  
  static private int generateTable( IfExp tree, int spaces ) {
      
       comment("IfExp");
       
       int TEST = generateTable( tree.test, 0); //Stores result of test in r0
       
       OpExp testExp = (OpExp)tree.test;
       if(tree.elsepart != null)
       {
               int jumpNum = jumpCount;
            jumpCount++;
           //If there's an else, we supposed to jump to the true branch or fall through to the else
           
           //Callum note - Our jump conditions need to be inverted
           //E.x. if (x == 0), we want to "jump" when x is not equal to 0, and run execution after jump. 
           //if it's true, no jump (until end of if statement), and run normally
           if(testExp.op == OpExp.EQLTY) {
                //emitRM("JNE", 0, 999, 999, "We jump to the true branch, but how do we know the right place to jump to? --Backpatching???");
               // generateTable( tree.elsepart, spaces );
               emitJUMP("JNE", jumpNum, pc, "JNE jump");
           } else if (testExp.op == OpExp.NE) {
                emitJUMP("JEQ", jumpNum, pc, "JEQ jump");
           } else if (testExp.op == OpExp.LT) {
                emitJUMP("JGE", jumpNum, pc, "JGE jump");
           } else if (testExp.op == OpExp.LE) {
                emitJUMP("JGT", jumpNum, pc, "JGT jump");
           } else if (testExp.op == OpExp.GT) {
                emitJUMP("JLE", jumpNum, pc, "JLE jump");
           } else if (testExp.op == OpExp.GE) {
                emitJUMP("JLT", jumpNum, pc, "JLT jump");
           } 
           
               pc++;
            generateTable( tree.thenpart, spaces );
               emitLAND(jumpNum, pc);
            generateTable( tree.elsepart, spaces );

           
       }
       else //if statement with no else part
       {
            //Callum note - same jumping as before, except we're just jumping over the whole conditional
            //again, how do we know how far we need to jump?
            int jumpNum = jumpCount;
            jumpCount++;
            if(testExp.op == OpExp.EQLTY) 
            {
                emitJUMP("JNE", jumpNum, pc, "JNE jump");
        /*        pc++;
                generateTable( tree.thenpart, spaces );
                emitLAND(jumpNum, pc);*/
            }
            else if (testExp.op == OpExp.NE) 
            {
                emitJUMP("JEQ", jumpNum, pc, "JEQ jump");
            /*    pc++;
                generateTable( tree.thenpart, spaces );
                emitLAND(jumpNum, pc);*/
            }
            else if (testExp.op == OpExp.LT)
            {
                emitJUMP("JGE", jumpNum, pc, "JGE jump");
        /*        pc++;
                generateTable( tree.thenpart, spaces );
                emitLAND(jumpNum, pc);*/
            }
            else if (testExp.op == OpExp.LE)
            {
                emitJUMP("JGT", jumpNum, pc, "JGT jump");
            /*    pc++;
                generateTable( tree.thenpart, spaces );
                emitLAND(jumpNum, pc);*/
            }
            else if (testExp.op == OpExp.GT)
            {
                emitJUMP("JLE", jumpNum, pc, "JLE jump");
            /*    pc++;
                generateTable( tree.thenpart, spaces );
                emitLAND(jumpNum, pc);*/
            }
            else if (testExp.op == OpExp.GE)
            {
                emitJUMP("JLT", jumpNum, pc, "JLT jump");
            /*    pc++;
                generateTable( tree.thenpart, spaces );
                emitLAND(jumpNum, pc);*/
            }
            pc++;
            generateTable( tree.thenpart, spaces );
            emitLAND(jumpNum, pc);
       }
     



//      generateTable( tree.thenpart, spaces );        //Exp
//      generateTable( tree.elsepart, spaces );        //Exp
      
      if (TEST == 0 ) return 0;
      if (TEST == 1) System.err.println("Error: Line " + (tree.pos+1) + ". Condition cannot be of type void.");
      return -1;
      
  }
  
  static private int generateTable( IndexVar tree, int spaces ) {
      
      
   
      int INDEX = generateTable( tree.index, spaces );
      
      if (INDEX != 0)
      {
          System.err.println("");
          System.err.println("Error: Line " + (tree.pos+1) + ". Index for'" + tree.name + "' not int.");
      }
      
      Entry e = SymTable.lookup(tree.name);
      if(e == null)
      {
        System.err.println("");
        System.err.println("Error: Line " + (tree.pos+1) + ". Symbol '" + tree.name + "' not defined.");
        return -1;
      }
      else
      {
        return e.type;
      }
   
   
      
  }
  
  static private int generateTable( IntExp tree, int spaces ) {
      
      //0 or 1 = store it in a register
      //8 = store it in to a register then load it in to memory (for argument parameters)
      int reg = spaces;
      if(reg == 0 || reg == 1) emitRM("LDC", reg, tree.value, 0, "moving literal " + tree.value + " to r" + reg);
      if(reg == 8) //I don't think we really need this but the different comment is good for debugging
      {
        emitRM("LDC", 0, tree.value, 0, "(call seq) move constant parameter " + tree.value + " to r0");
      }
      return 0;
      
  }
  
  static private int generateTable( NameTy tree, int spaces ) {
      
      switch( tree.typ ) {
          case NameTy.INT:
            return 0;
          case NameTy.VOID:
            return 1;
          default:
            return -1;
      }
      
  }

  static private int generateTable( OpExp tree, int spaces ) {
    
    comment("op exp");
    int LHS = generateTable( tree.left, 0 ); //Stores result in r0   
    int tmpOffset1 = fpOff;
    fpOff--;
    emitRM("ST", 0, tmpOffset1, FP_REG, "move r0 to tempory storage");
    
    
    int RHS = generateTable( tree.right, 0 );//Stores result in r0
    int tmpOffset2 = fpOff;
    fpOff--;
    emitRM("ST", 0, tmpOffset2, FP_REG, "move r0 to tempory storage");

        
    if (LHS != RHS) 
    {
        //errorMessage(tree.pos, "Type mismatch", LHS, RHS);
        /* TYPE INFERENCE - ASSERT THE RESULT TO BE AN INTEGER (TAKES PRECEDENCE) */
        System.err.println("Warning: Line " + (tree.pos+1) + ". Operands not equal, result assumed to be integer.");
        return 0;
    }
    
    emitRM("LD", 0, tmpOffset1, FP_REG, "move LHS from tmp storage to r0");
    emitRM("LD", 1, tmpOffset2, FP_REG, "move RHS from tmp storage to r1");
    fpOff += 2; //Free up the temporary storage
    
    
    if(tree.op == OpExp.PLUS)        emitRO("ADD", 0, 0, 1, "r0 add r1. store result in r0");
    else if (tree.op == OpExp.MINUS) emitRO("SUB", 0, 0, 1, "r0 sub r1. store result in r0");
    else if (tree.op == OpExp.MUL)   emitRO("MUL", 0, 0, 1, "r0 mul r1. store result in r0");
    else if (tree.op == OpExp.DIV)   emitRO("DIV", 0, 0, 1, "r0 div r1. store result in r0");
    else
    {
         comment("conditional test");
         emitRO("SUB", 0, 0, 1, "op test. store result in r0");
    }
    return LHS;
     
  }
  
  static private int generateTable( ReturnExp tree, int spaces ) {
      
      int returnType;
      
      if (tree.exp == null) 
        returnType = 1; //return statement is void
      else 
        returnType = generateTable(tree.exp, spaces);
        
      if(returnType != expectedReturn)
      {
        System.err.println("Error: Line " + (tree.pos+1) + ". Invalid return type.");
      }
      return returnType;

      
  }
  
  static private void generateTable( SimpleDec tree, int spaces ) {
  
      generateTable( tree.typ, spaces );
      
      int offset = scope == 0 ? gpOff : fpOff; //Use gp if it's a global var, fp otherwise
            
      if(!SymTable.insert(tree.name, tree.typ.typ, 1, scope, offset, pc))
      {
        System.err.println("\nError: Line " + (tree.pos+1) + ". Redefinition of '" + tree.name + "'.");
      }
      int dummy = scope == 0 ? gpOff-- : fpOff--; 
      
      
  }
  
  static private int generateTable( SimpleVar tree, int spaces) {
    
    
      int reg = spaces;
      int offset = SymTable.getOffset(tree.name);
      if(reg == 0 || reg == 1)
      {
        emitRM("LD", reg, offset, FP_REG, "move value of " + tree.name + " in to r" + reg);
      }
      if(reg == 8)//When it's an argument to a function
      {
        emitRM("LD", 0, offset, FP_REG, "(call seq) move value of " + tree.name + " in to r0");
     //   emitRM("ST", 0, fpOff + initFO, FP_REG, "(call seq) move r0 to memory");
        //fpOff--;
      }
      
      Entry e = SymTable.lookup(tree.name);
      if(e == null)
      {
        System.err.println("Error: Line " + (tree.pos+1) + ". Symbol '" + tree.name + "' not defined.");
        return -1;
      }
      else
      {
        return e.type;
      }      
  }
  
  static private int generateTable( VarExp tree, int spaces ) {
  
    
        
      return generateTable(tree.variable, spaces);
      
  }
  
  static private int generateTable( WhileExp tree, int spaces ) {

      
      generateTable( tree.body, spaces );
      return generateTable(tree.test, spaces);
      
  }

  private static void leaveScope(String name)
  {
    if(drawTable)
    {   
        System.out.println("Exiting" + name + " scope");
        System.out.println("Symbol table at exit:");
        SymTable.print();
    }   
       
    SymTable.removeScope(scope);
    scope--;
  }
  
  private static void enterScope(String name)
  {
    if(drawTable)
    {   
        System.out.println("Entering " + name + " scope");
        System.out.println("Symbol table at entry:");
        SymTable.print();
    }   
    scope++;
  }
  
  
  private static void errorMessage(int line, String message, int LHS, int RHS)
  {
        if(LHS == -1 || RHS == -1) return;
        System.err.println("");
        System.err.print("Error: Line " + (line+1) + " " + message + ". Expected type ");
        if(LHS == 0) System.err.print("INT");
        if(LHS == 1) System.err.print("VOID");
        System.err.print(", received ");
        if(RHS == 0) System.err.println("INT.");
        if(RHS == 1) System.err.println("VOID.");
        System.err.println("");
  }


  private static void emitRM(String opcode, int r, int d, int s, String comment)
  {
    //System.out.println(pc + ":  " + opcode + " " + r + ", " + d + "(" + s + ")       *" + comment);
    try
    {
        writer.write(pc + ":  " + opcode + " " + r + ", " + d + "(" + s + ")       *" + comment + "\n");
    }
    catch(Exception ex)
    {
    
    }

    pc++;
  }
  
  private static void emitRM(String opcode, int r, int d, int s)
  {
    //System.out.println(pc + ":  " + opcode + " " + r + ", " + d + "(" + s + ")");
    try
    {
        writer.write(pc + ":  " + opcode + " " + r + ", " + d + "(" + s + ")" + "\n");
    }
    catch(Exception ex)
    {
    
    }
    pc++;
  }
  
  private static void emitRO(String opcode, int r, int s, int t, String comment)
  {
      //System.out.println(pc + ":  " + opcode + " " + r + ", " + s + ", "  + t + "       *" + comment);
    try
    {
        writer.write(pc + ":  " + opcode + " " + r + ", " + s + ", "  + t + "       *" + comment + "\n");
    }
    catch(Exception ex)
    {
    
    }
      pc++;
  }
  
  private static void emitRO(String opcode, int r, int s, int t)
  {
      //System.out.println(pc + ":  " + opcode + " " + r + ", " + s + ", "  + t + "       *");
    try
    {
        writer.write(pc + ":  " + opcode + " " + r + ", " + s + ", "  + t + "       *" + "\n");
    }
    catch(Exception ex)
    {
    
    }
    pc++;
  }
  
  private static void emitJUMP(int jumpNum, int pc, String comment)
  {
    try
    {
        writer.write("JUMP|" + jumpNum + "|" + pc + "|" + comment + "\n");
    }
    catch(Exception ex)
    {
    
    }
    pc++;
  }
  
  private static void emitJUMP(String jumpCode, int jumpNum, int pc, String comment)
  {
    try
    {
        writer.write(jumpCode + "|" + jumpNum + "|" + pc + "|" + comment + "\n");
    }
    catch(Exception ex)
    {
    
    }
    pc++;
  }
  
  private static void emitLAND(int jumpNum, int pc)
  {
    try
    {
        writer.write("LAND|" + jumpNum + "|" + pc + "\n");
    }
    catch(Exception ex)
    {
    
    }
  }
  
  

  private static void comment(String s)
  {
    try
    {
        writer.write("* " + s + "\n");
    }
    catch(Exception ex)
    {
    
    }
  } 



}


