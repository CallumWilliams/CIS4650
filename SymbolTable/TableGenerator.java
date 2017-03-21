package SymbolTable;

import absyn.*;


public class TableGenerator
{
  static int scope = 0;
  static Boolean drawTable = false;

  public int pos;

  final static int SPACES = 4;

  static private void indent( int spaces ) {
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
  
  static private void generateTable( Var tree, int spaces ) {
	  
	  if ( tree instanceof SimpleVar )
		generateTable( (SimpleVar)tree, spaces );
	  else if ( tree instanceof IndexVar )
		generateTable( (IndexVar)tree, spaces );
	  else {
        // indent( spaces );
        // System.out.println( "Illegal expression at line " + tree.pos  );
	  }
	  
  }
  
  static private void generateTable( Exp tree, int spaces ) {
	  
	  if (tree instanceof VarExp )
		generateTable( (VarExp)tree, spaces );
	  else if ( tree instanceof IntExp )
		generateTable( (IntExp)tree, spaces );
	  else if ( tree instanceof CallExp )
		generateTable( (CallExp)tree, spaces );
	  else if ( tree instanceof OpExp )
		generateTable( (OpExp)tree, spaces );
	  else if ( tree instanceof AssignExp )
		generateTable( (AssignExp)tree, spaces );
	  else if ( tree instanceof IfExp )
		generateTable( (IfExp)tree, spaces );
	  else if ( tree instanceof WhileExp )
		generateTable( (WhileExp)tree, spaces );
	  else if ( tree instanceof CompoundExp )
		generateTable( (CompoundExp)tree, spaces );
	  else if ( tree instanceof ReturnExp )
		generateTable( (ReturnExp)tree, spaces );
	  else {
        // indent( spaces );
        /* v crashes code when not commented (NullPointerException) v */
        // System.out.println( "Illegal expression at line "  );
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
  
  static private void generateTable( AssignExp tree, int spaces ) {
	  
	//  // indent( spaces );
	//  // System.out.println( "AssignExp:" );
	  spaces += SPACES;
      generateTable( tree.lhs, spaces );
      spaces += SPACES;
    //  // indent( spaces );
     // // System.out.println(" = ");
      generateTable( tree.rhs, spaces );
	  
  }
  
  static private void generateTable( CallExp tree, int spaces ) {
	  
	//indent( spaces );
    //System.out.println( "CallExp:" );
	  spaces += SPACES;
    //indent( spaces );
	//System.out.println( tree.func );
      generateTable( tree.args, spaces );
	  
  }
  
  static private void generateTable( CompoundExp tree, int spaces ) {///////////////////
	  
	//indent( spaces );
	//System.out.println( "CompoundExp:" );
	  scope++;
	  System.out.println("ENTERING SCOPE " + scope);
	  spaces += SPACES;
	  generateTable( tree.decs, spaces );
      generateTable( tree.exps, spaces );
      leaveScope();
 	  System.out.println("EXITED SCOPE " + (scope+1) );
      
	  
  }
  
  static private void generateTable( FunctionDec tree, int spaces ) { /////////////////////////
	  
	//indent( spaces );
	//System.out.println( "FunctionDec:" );
	  
	  spaces += SPACES;
	  generateTable( tree.result, spaces );
   // indent( spaces );
   // System.out.println( tree.func );
      generateTable( tree.params, spaces );
      generateTable( tree.body, spaces );
      
       
      System.out.println("ADDING FUNCTION: " + tree.func);
      SymTable.insert(tree.func, tree.result.typ, scope);
   //   scope++; 
      generateTable( tree.params, spaces );
      generateTable( tree.body, spaces );
   //   leaveScope();
  
	  
  }
  
  static private void generateTable( IfExp tree, int spaces ) {
	  
   // indent( spaces );
   // System.out.println( "IfExp:" );
	  spaces += SPACES;
	  generateTable( tree.test, spaces );
      generateTable( tree.thenpart, spaces );
      generateTable( tree.elsepart, spaces );
      
  }
  
  static private void generateTable( IndexVar tree, int spaces ) {
	  
   // indent( spaces );
   // System.out.println( "IndexVar:" );
	  spaces += SPACES;
   // indent( spaces );
   // System.out.println( tree.name );
      generateTable( tree.index, spaces );
	  
  }
  
  static private void generateTable( IntExp tree, int spaces ) {
	  
	  // indent( spaces );
	  // System.out.println( "IntExp:" );
	  spaces += SPACES;
	  // indent( spaces );
      // System.out.println( tree.value );
	  
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

  static private void generateTable( OpExp tree, int spaces ) {
	  
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
    generateTable( tree.left, spaces );
    generateTable( tree.right, spaces );
     
  }
  
  static private void generateTable( ReturnExp tree, int spaces ) {
	  
	  // indent( spaces );
	  // System.out.println( "ReturnExp:" );
	  spaces += SPACES;
      generateTable( tree.exp, spaces );
	  
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
  
  static private void generateTable( SimpleVar tree, int spaces ) {
	  
	  // indent( spaces );
	  // System.out.println( "SimpleVar:" );
	  spaces += SPACES;
	  // indent( spaces );
      // System.out.println( tree.name );
	  
  }
  
  static private void generateTable( VarExp tree, int spaces ) {
	  
	  // indent( spaces );
	  // System.out.println( "VarExp:" );
	  spaces += SPACES;
      generateTable( tree.variable, spaces );
	  
  }
  
  static private void generateTable( WhileExp tree, int spaces ) {
	  
	  // indent( spaces );
	  // System.out.println( "WhileExp:" );
	  spaces += SPACES;
      generateTable( tree.test, spaces );
      generateTable( tree.body, spaces );
	  
  }

    private static void leaveScope()
    {
      if(drawTable)
        SymTable.print();
        
      SymTable.removeScope(scope);
      scope--;
    }




}


