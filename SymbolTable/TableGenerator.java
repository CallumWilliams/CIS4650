package SymbolTable;

import absyn.*;
import SymbolTable.*;


public class TableGenerator
{
  static int scope = 0;
  static Boolean drawTable = false;
  static int expectedReturn = -1;

  public int pos;

  final static int SPACES = 4;
  
  
  
  //Assembly stuff
  static int gp = 100, //Top of memory. NEEDS REAL INITIALISATION
             fp = 0,
             fpOff = 0,
             gpOff = 0,
             pc = 0;

  final static int PC_REG = 7,
                   GP_REG = 6,
                   FP_REG = 5;

  static int ofpFO = 0,   //I don't think these change
             retFO = -1,
             initFO = -2;          
  ////////
  
             
  static public void indent( int spaces ) {
    for( int i = 0; i < spaces; i++ )  System.out.print( " " );
  }
  
  static public void generateTable( DecList tree, int spaces, Boolean draw ) {
      
      drawTable = draw;
      SymTable.insert("input", 0, 1, 0, gpOff);
      gpOff--;
      SymTable.insert("output", 1, 1, 0, gpOff);
      gpOff--;
      while (tree != null) {
          generateTable( tree.head, spaces );
          tree = tree.tail;
      }
      
  }
  
  static public void generateTable( ExpList tree, int spaces ) {
      
      while (tree != null) {
          generateTable( tree.head, spaces );
          tree = tree.tail;
      }
      
  }
  
  static public void generateTable( VarDecList tree, int spaces ) {
      
      while (tree != null) {
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
      generateTable( tree.typ, spaces );
      if ( tree.size != null) array_size = tree.size.value;
      if(!SymTable.insert(tree.name, tree.typ.typ, array_size, scope, fpOff))
      {
        System.err.println("\nError: Line " + (tree.pos + 1) + ". Redefinition of '" + tree.name + "'.");
      }
      fpOff--;
      
  }
  
  static private int generateTable( AssignExp tree, int spaces ) {
    

        
      SimpleVar sv = (SimpleVar)tree.lhs.variable;
      String varName = sv.name;
      int offset = SymTable.getOffset(varName);
      System.out.println("Assigning a value to " + varName); 
      
      int LHS = generateTable(tree.lhs, spaces);
      int RHS = generateTable(tree.rhs, spaces); //This will store the result of RHS in a register (I chose r0 for now -- does it matter??)
      
      if (LHS != RHS) 
      {
        errorMessage(tree.pos,"Type mismatch", LHS, RHS);
        return -1;
      }
      

      
      //System.out.println("LDA 0, " + offset + "(" + FP_REG + ")");
      System.out.println("ST 0, " + offset + "(" + FP_REG + ")");
      
      
      
      
      
      
      return LHS;
      
  }
  
  static private int generateTable( CallExp tree, int spaces ) {
      
      
      generateTable( tree.args, spaces );
    
      Entry e = SymTable.lookup(tree.func);
      if(e == null)
      {
        System.err.println("");
        System.err.println("Error:  Line " + (tree.pos+1) + ". Symbol '" + tree.func + "' not defined.");
        return -1;
      }
      else
      {
        return e.type;
      }
    
    
      
  }
  
  static private int generateTable( CompoundExp tree, int spaces ) {
  
      enterScope("CompoundExp");
      
      generateTable( tree.decs, spaces );
      generateTable( tree.exps, spaces );
      leaveScope("CompoundExp");

      
      return -1; //does nothing, only to make generateTable(Exp) work
      
  }
  
  static private void generateTable( FunctionDec tree, int spaces ) {
      
      
      System.out.println("function setup stuff goes here"); 
      expectedReturn = generateTable(tree.result, spaces); 
      
      if(!SymTable.insert(tree.func, tree.result.typ, 1, scope, gpOff))
      {
        System.err.println("\nError: Line " + (tree.pos+1) + ". Redefinition of '" + tree.func + "'.");
      }
      gpOff--;
      
      System.out.println("gp: " + gp + " gpOff: " + gpOff);
      fp = gp + gpOff;   //Set fp to the next empty spot in memory
      
      fpOff = 0;
      
      
      enterScope(tree.func);
      generateTable( tree.params, spaces );
      scope--;
      generateTable( tree.body, spaces );
      
      if(drawTable)
      {
            System.err.println("Exiting " + tree.func + ".\nSymbol Table at exit: ");
            SymTable.print();
      }
      System.out.println("function cleanup stuff goes here"); 
      
  }
  
  static private int generateTable( IfExp tree, int spaces ) {
      

      
      int TEST = generateTable( tree.test, spaces );
      generateTable( tree.thenpart, spaces );
      generateTable( tree.elsepart, spaces );
      
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
      
    //  System.out.println("(IntExp) Storing " + tree.value + " in r0");
      System.out.println("LDC 0, " + tree.value + "(0)");
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
    
    int LHS = generateTable( tree.left, spaces );
    int RHS = generateTable( tree.right, spaces );
    
    if (LHS != RHS) 
    {
        //errorMessage(tree.pos, "Type mismatch", LHS, RHS);
        /* TYPE INFERENCE - ASSERT THE RESULT TO BE AN INTEGER (TAKES PRECEDENCE) */
        System.err.println("Warning: Line " + (tree.pos+1) + ". Operands not equal, result assumed to be integer.");
        return 0;
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
      
      if(!SymTable.insert(tree.name, tree.typ.typ, 1, scope, offset))
      {
        System.err.println("\nError: Line " + (tree.pos+1) + ". Redefinition of '" + tree.name + "'.");
      }
      int dummy = scope == 0 ? gpOff-- : fpOff--; 
      
      
  }
  
  static private int generateTable( SimpleVar tree, int spaces ) {
    
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


  private static void emitRM(String opcode)
  {
    System.out.println(opcode);
  }



}


