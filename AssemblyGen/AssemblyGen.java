package AssemblyGen;

import absyn.*;

public class AssemblyGen
{
  
  static public String generateAssembly( DecList tree, int line ) {
      
      
      String ret = "0: LD 6,0(0)\n1: LDA 5,0(6)\n2: ST 0,0(0)\n";
      line = 3;
      //while (tree != null) {
      //    ret += generateAssembly( tree.head, line );
      //    tree = tree.tail;
      //}
      ret += (line++) + ": ST 5, -1(5)\n";
      ret += (line++) + ": LDA 5,-1(5)\n";
      ret += (line++) + ": LDA 0, 1(7)\n";
      ret += (line++) + ": LDA 7,-35(7)\n";
      ret += (line++) + ": LD 5, 0(5)\n";
      ret += (line++) + ": HALT 0, 0, 0\n";
      return ret;
      
  }
  
  static public String generateAssembly( ExpList tree, int line ) {
      
      String ret = "";
      while (tree != null) {
          ret = generateAssembly( tree.head, line );
          tree = tree.tail;
      }
      
      return ret;
  }
  
  static public String generateAssembly( VarDecList tree, int line ) {
      
      String ret = "";
      while (tree != null) {
          ret = generateAssembly( tree.head, line );
          tree = tree.tail;
      }
      
      return ret;
  }

  static private String generateAssembly( Dec tree, int line ) {
    
    String ret = "";
      
    if( tree instanceof FunctionDec )
      ret = generateAssembly( (FunctionDec)tree, line );
    else if ( tree instanceof VarDec )
      ret = generateAssembly( (VarDec)tree, line );
    
    return ret;
  }
  
  static private String generateAssembly( VarDec tree, int line ) {
      
      String ret = "";
      if ( tree instanceof SimpleDec )
        ret = generateAssembly( (SimpleDec)tree, line );
      else if ( tree instanceof ArrayDec )
        ret = generateAssembly( (ArrayDec)tree, line );
      
      return ret;
  }
  
  static private String generateAssembly( Var tree, int line ) {
      
      String ret = "";
      if ( tree instanceof SimpleVar )
        ret = generateAssembly( (SimpleVar)tree, line );
      else if ( tree instanceof IndexVar )
        ret = generateAssembly( (IndexVar)tree, line );
      
      return null;
  }
  
  static private String generateAssembly( Exp tree, int line ) {
      
      if (tree instanceof VarExp )
        return generateAssembly( (VarExp)tree, line );
      else if ( tree instanceof IntExp )
        return generateAssembly( (IntExp)tree, line );
      else if ( tree instanceof CallExp )
        return generateAssembly( (CallExp)tree, line );
      else if ( tree instanceof OpExp )
        return generateAssembly( (OpExp)tree, line );
      else if ( tree instanceof AssignExp )
        return generateAssembly( (AssignExp)tree, line );
      else if ( tree instanceof IfExp )
        return generateAssembly( (IfExp)tree, line );
      else if ( tree instanceof WhileExp )
        return generateAssembly( (WhileExp)tree, line );
      else if ( tree instanceof CompoundExp )
        return generateAssembly( (CompoundExp)tree, line );
      else if ( tree instanceof ReturnExp )
        return generateAssembly( (ReturnExp)tree, line );
      
      return null;
  }
  
  static private String generateAssembly( ArrayDec tree, int line ) {
      
      String ret = "";
      return ret;
      
  }
  
  static private String generateAssembly( AssignExp tree, int line ) {
      
      String ret = "";
      return ret;
      
  }
  
  static private String generateAssembly( CallExp tree, int line ) {
	  
	  String ret = "";
      return ret;
      
  }
  
  static private String generateAssembly( CompoundExp tree, int line ) {
	  
	  String ret = "";
      return ret;
      
  }
  
  static private String generateAssembly( FunctionDec tree, int line ) {
      
      String ret = "";
      return ret;
      
  }
  
  static private String generateAssembly( IfExp tree, int line ) {
	  
	  String ret = "";
      return ret;
      
  }
  
  static private String generateAssembly( IndexVar tree, int line ) {
	  
	  String ret = "";
      return ret;
      
  }
  
  static private String generateAssembly( IntExp tree, int line ) {
      
      String ret = "";
      return ret;
      
  }
  
  static private String generateAssembly( NameTy tree, int line ) {
	  
	  String ret = "";
      return ret;
      
  }

  static private String generateAssembly( OpExp tree, int line ) {
	  
	  String ret = "";
      return ret;
     
  }
  
  static private String generateAssembly( ReturnExp tree, int line ) {
	  
	  String ret = "";
      return ret;
      
  }
  
  static private String generateAssembly( SimpleDec tree, int line ) {
	  
	  String ret = "";
      return ret;
      
  }
  
  static private String generateAssembly( SimpleVar tree, int line ) {
	  
      String ret = "";
      return ret;
           
  }
  
  static private String generateAssembly( VarExp tree, int line ) {
	  
      String ret = "";
      return ret;
      
  }
  
  static private String generateAssembly( WhileExp tree, int line ) {
	  
	  String ret = "";
      return ret;
      
  }

}
