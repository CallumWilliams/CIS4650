package SymbolTable;

import absyn.*;


public class TableGenerator
{
  static int scope = 0;
  static Boolean drawTable = false;

  public int pos;

  final static int SPACES = 4;

  static public void indent( int spaces ) {
    for( int i = 0; i < spaces; i++ )  System.out.print( " " );
  }
  
  static public void generateTable( DecList tree, int spaces, Boolean draw ) {
	  
	  drawTable = draw;
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
      // indent( spaces );
      // System.out.println( "Illegal expression at line " + tree.pos  );
    }
    
  }
  
  static private void generateTable( VarDec tree, int spaces ) {
	  
	  if ( tree instanceof SimpleDec )
		generateTable( (SimpleDec)tree, spaces );
	  else if ( tree instanceof ArrayDec )
		generateTable( (ArrayDec)tree, spaces );
	  else {
        // indent( spaces );
        // System.out.println( "Illegal expression at line " + tree.pos  );
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
  
  static private void generateTable( ArrayDec tree, int spaces ) {///////////////////////////////////////////
	  
      // indent( spaces );
	  // System.out.println( "ArrayDec:" );
	  spaces += SPACES;
      generateTable( tree.typ, spaces );
      // indent( spaces );
      // System.out.println( tree.name );
      if ( tree.size != null) generateTable( tree.size, spaces );
      
      SymTable.insert(tree.name, tree.typ.typ, scope);
	  
  }
  
  static private int generateTable( AssignExp tree, int spaces ) {
	  
	//  // indent( spaces );
	//  // System.out.println( "AssignExp:" );
	  spaces += SPACES;
      generateTable( tree.lhs, spaces );
      spaces += SPACES;
    //  // indent( spaces );
     // // System.out.println(" = ");
      generateTable( tree.rhs, spaces );
      int LHS = generateTable(tree.lhs, spaces);
      int RHS = generateTable(tree.rhs, spaces);
	  
	  if (LHS != RHS) return -1;
	  return LHS;
	  
  }
  
  static private int generateTable( CallExp tree, int spaces ) {
	  
	//indent( spaces );
    //System.out.println( "CallExp:" );
	  spaces += SPACES;
    //indent( spaces );
	//System.out.println( tree.func );
      generateTable( tree.args, spaces );
      if (SymTable.hashMap.containsValue(tree.func)) return SymTable.hashMap.get(tree.func).type;
	  return -1;
	  
  }
  
  static private int generateTable( CompoundExp tree, int spaces ) {///////////////////
	  
	//indent( spaces );
	//System.out.println( "CompoundExp:" );
	  scope++;
	  if (drawTable) System.out.println("ENTERING SCOPE " + scope);
	  spaces += SPACES;
	  generateTable( tree.decs, spaces );
      generateTable( tree.exps, spaces );
      leaveScope();
 	  if (drawTable) System.out.println("EXITED SCOPE " + (scope+1) );
      
      return -1; //does nothing, only to make generateTable(Exp) work
	  
  }
  
  static private void generateTable( FunctionDec tree, int spaces ) { /////////////////////////
	  
	//indent( spaces );
	//System.out.println( "FunctionDec:" );
	  
	  spaces += SPACES;
	  generateTable( tree.result, spaces );
   // indent( spaces );
   // System.out.println( tree.func );
   // generateTable( tree.params, spaces );
   // generateTable( tree.body, spaces );
      
       
      if (drawTable) System.out.println("ADDING FUNCTION: " + tree.func);
      SymTable.insert(tree.func, tree.result.typ, scope);
      scope++; 
      generateTable( tree.params, spaces );
      scope--;
      generateTable( tree.body, spaces );
   //   leaveScope();
  
	  
  }
  
  static private int generateTable( IfExp tree, int spaces ) {
	  
   // indent( spaces );
   // System.out.println( "IfExp:" );
	  spaces += SPACES;
	  generateTable( tree.test, spaces );
      generateTable( tree.thenpart, spaces );
      generateTable( tree.elsepart, spaces );
      return 0;
      
  }
  
  static private int generateTable( IndexVar tree, int spaces ) {
	  
   // indent( spaces );
   // System.out.println( "IndexVar:" );
	  spaces += SPACES;
   // indent( spaces );
   // System.out.println( tree.name );
      generateTable( tree.index, spaces );
      if (SymTable.hashMap.containsValue(tree.name)) return SymTable.hashMap.get(tree.name).type;
      return -1; //fail case
	  
  }
  
  static private int generateTable( IntExp tree, int spaces ) {
	  
	  // indent( spaces );
	  // System.out.println( "IntExp:" );
	  spaces += SPACES;
	  // indent( spaces );
      // System.out.println( tree.value );
      return 1; //return integer type
	  
  }
  
  static private void generateTable( NameTy tree, int spaces ) {
	  
	//indent( spaces );
	//System.out.println( "NameTy:" );
	  spaces += SPACES;
   // indent( spaces );
	  switch( tree.typ ) {
		  case NameTy.INT:
			// System.out.println( "INT" );
			break;
		  case NameTy.VOID:
			// System.out.println( "VOID" );
			break;
		  default:
			// System.out.println( "Unrecognized operator at line " + tree.pos);
	  }
	  
  }

  static private int generateTable( OpExp tree, int spaces ) {
	  
 //    indent( spaces );
 /*    System.out.print( "OpExp:" ); 
    switch( tree.op ) {
      case OpExp.PLUS:
         System.out.println( " + " );
        break;
      case OpExp.MINUS:
         System.out.println( " - " );
        break;
      case OpExp.MUL:
         System.out.println( " * " );
        break;
      case OpExp.DIV:
         System.out.println( " / " );
        break;
      case OpExp.EQLTY:
         System.out.println( " == " );
        break;
      case OpExp.NE:
         System.out.println( " != " );
        break;
      case OpExp.LT:
         System.out.println( " < " );
        break;
      case OpExp.LE:
         System.out.println( " <= " );
        break;
      case OpExp.GT:
         System.out.println( " > " );
        break;
      case OpExp.GE:
         System.out.println( " >= " );
        break;
      default:
         System.out.println( "Unrecognized operator at line " + tree.pos);
    }*/
    spaces += SPACES;
    int LHS = generateTable( tree.left, spaces );
    int RHS = generateTable( tree.right, spaces );
    
    if (LHS != RHS) return -1;
    else return LHS;
     
  }
  
  static private int generateTable( ReturnExp tree, int spaces ) {
	  
	  // indent( spaces );
	  // System.out.println( "ReturnExp:" );
	  if (tree.exp == null) return 1; //return statement is void
	  else return generateTable(tree.exp, spaces);
	  //spaces += SPACES;
      //generateTable( tree.exp, spaces );
	  
  }
  
  static private void generateTable( SimpleDec tree, int spaces ) {
	  
	  // indent( spaces );
	  // System.out.println( "SimpleDec:" );
	  spaces += SPACES;
      generateTable( tree.typ, spaces );
      spaces += SPACES;
      // indent( spaces );
      // System.out.println( tree.name );
      SymTable.insert(tree.name, tree.typ.typ, scope);
	  
  }
  
  static private int generateTable( SimpleVar tree, int spaces ) {
	  
	  // indent( spaces );
	  // System.out.println( "SimpleVar:" );
	  spaces += SPACES;
	  // indent( spaces );
      // System.out.println( tree.name );
      if (SymTable.hashMap.containsValue(tree.name)) return SymTable.hashMap.get(tree.name).type;
      return -1; //error case
	  
  }
  
  static private int generateTable( VarExp tree, int spaces ) {
	  
	  // indent( spaces );
	  // System.out.println( "VarExp:" );
	  spaces += SPACES;
      // generateTable( tree.variable, spaces );
      return generateTable(tree.variable, spaces);
	  
  }
  
  static private int generateTable( WhileExp tree, int spaces ) {
	  
	  // indent( spaces );
	  // System.out.println( "WhileExp:" );
	  spaces += SPACES;
      // generateTable( tree.test, spaces );
      generateTable( tree.body, spaces );
      return generateTable(tree.test, spaces);
	  
  }

    private static void leaveScope()
    {
      if(drawTable)
        SymTable.print();
        
      SymTable.removeScope(scope);
      scope--;
    }




}


