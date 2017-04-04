package AssemblyGen;

import absyn.*;

public class AssemblyGen
{
    static int line = 0;
  
  static public String generateAssembly( DecList tree ) {
      

      String ret = "0: LD 6,0(0)\n1: LDA 5,0(6)\n2: ST 0,0(0)\n";
      //i/o routines
      ret += "4: ST  0,-1(5)\n";
      ret += "5: IN 0,0,0\n";
      ret += "6: LD 7,-1(5)\n";
      ret += "7: ST  0,-1(5)\n";
      ret += "8: LD  0,-2(5)\n";
      ret += "9: OUT  0,0,0\n";
      ret += "10: LD  7,-1(5)\n";
      ret += "3: LDA  0,-1(5)\n";
      line = 12;
      
      while (tree != null) {
          ret += generateAssembly( tree.head );
          tree = tree.tail;
      }
      ret += (line++) + ": ST 5, -1(5)\n";
      ret += (line++) + ": LDA 5,-1(5)\n";
      ret += (line++) + ": LDA 0, 1(7)\n";
      ret += (line++) + ": LDA 7,-35(7)\n";
      ret += (line++) + ": LD 5, 0(5)\n";
      ret += (line++) + ": HALT 0, 0, 0\n";
      return ret;
      
  }
  
  static public String generateAssembly( ExpList tree ) {
      
      String ret = "";
      while (tree != null) {
          ret = generateAssembly( tree.head);
          tree = tree.tail;
      }
      
      return ret;
  }
  
  static public String generateAssembly( VarDecList tree ) {
      
      String ret = "";
      while (tree != null) {
          ret = generateAssembly( tree.head);
          tree = tree.tail;
      }
      
      return ret;
  }

  static private String generateAssembly( Dec tree ) {
    
    String ret = "";
      
    if( tree instanceof FunctionDec )
      ret = generateAssembly( (FunctionDec)tree);
    else if ( tree instanceof VarDec )
      ret = generateAssembly( (VarDec)tree);
    
    return ret;
  }
  
  static private String generateAssembly( VarDec tree ) {
      
      String ret = "";
      if ( tree instanceof SimpleDec )
        ret = generateAssembly( (SimpleDec)tree);
      else if ( tree instanceof ArrayDec )
        ret = generateAssembly( (ArrayDec)tree);
      
      return ret;
  }
  
  static private String generateAssembly( Var tree ) {
      
      String ret = "";
      if ( tree instanceof SimpleVar )
        ret = generateAssembly( (SimpleVar)tree);
      else if ( tree instanceof IndexVar )
        ret = generateAssembly( (IndexVar)tree);
      
      return null;
  }
  
  static private String generateAssembly( Exp tree ) {
      
      if (tree instanceof VarExp )
        return generateAssembly( (VarExp)tree);
      else if ( tree instanceof IntExp )
        return generateAssembly( (IntExp)tree);
      else if ( tree instanceof CallExp )
        return generateAssembly( (CallExp)tree);
      else if ( tree instanceof OpExp )
        return generateAssembly( (OpExp)tree);
      else if ( tree instanceof AssignExp )
        return generateAssembly( (AssignExp)tree);
      else if ( tree instanceof IfExp )
        return generateAssembly( (IfExp)tree);
      else if ( tree instanceof WhileExp )
        return generateAssembly( (WhileExp)tree);
      else if ( tree instanceof CompoundExp )
        return generateAssembly( (CompoundExp)tree);
      else if ( tree instanceof ReturnExp )
        return generateAssembly( (ReturnExp)tree);
      
      return null;
  }
  
  static private String generateAssembly( ArrayDec tree ) {
      
      
      String ret = "";
      generateAssembly( tree.typ);
      return ret;
      
  }
  
  static private String generateAssembly( AssignExp tree ) {
      
      String ret = "";
      generateAssembly(tree.lhs);
      generateAssembly(tree.rhs);
      return ret;
      
  }
  
  static private String generateAssembly( CallExp tree ) {
	  
	  String ret = "";
      generateAssembly( tree.args);
      return ret;
      
  }
  
  static private String generateAssembly( CompoundExp tree ) {
	  
	  String ret = "";
	  generateAssembly( tree.decs);
      generateAssembly( tree.exps);
      return ret;
      
  }
  
  static private String generateAssembly( FunctionDec tree ) {
      
      String ret = (line++) + "ST 0,-1(5)";
      generateAssembly( tree.params);
      generateAssembly( tree.body);
      return ret;
      
  }
  
  static private String generateAssembly( IfExp tree ) {
	  
	  String ret = "";
	  generateAssembly( tree.test);
      generateAssembly( tree.thenpart);
      generateAssembly( tree.elsepart);
      return ret;
      
  }
  
  static private String generateAssembly( IndexVar tree ) {
	  
	  String ret = "";
	  generateAssembly( tree.index);
      return ret;
      
  }
  
  static private String generateAssembly( IntExp tree ) {
      
      String ret = "";
      return ret;
      
  }
  
  static private String generateAssembly( NameTy tree ) {
	  
	  String ret = "";
	  

	  
      return ret;
      
  }

  static private String generateAssembly( OpExp tree ) {
	  
	  String ret = "";
      generateAssembly( tree.left);
      generateAssembly( tree.right);
      return ret;
     
  }
  
  static private String generateAssembly( ReturnExp tree ) {
	  
	  String ret = "";
	  	  if (tree.exp != null) 
        generateAssembly(tree.exp);
      return ret;
      
  }
  
  static private String generateAssembly( SimpleDec tree ) {
	  
	  String ret = "";
	  generateAssembly( tree.typ);
      return ret;
      
  }
  
  static private String generateAssembly( SimpleVar tree ) {
	  
      String ret = "";
      return ret;
           
  }
  
  static private String generateAssembly( VarExp tree ) {
	  
      String ret = "";
      generateAssembly(tree.variable);
      return ret;
      
  }
  
  static private String generateAssembly( WhileExp tree ) {
	  
	  String ret = "";
      generateAssembly( tree.body);
      generateAssembly(tree.test);
      return ret;
      
  }

}
