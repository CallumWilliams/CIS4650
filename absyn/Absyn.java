package absyn;

abstract public class Absyn 
{
  public int pos;

  final static int SPACES = 4;

  static private void indent( int spaces ) {
    for( int i = 0; i < spaces; i++ ) System.out.print( " " );
  }
  
  static public void showTree( DecList tree, int spaces ) {
	  
	  while (tree != null) {
		  showTree( tree.head, spaces );
		  tree = tree.tail;
	  }
	  
  }
  
  static public void showTree( ExpList tree, int spaces ) {
	  
	  while (tree != null) {
		  showTree( tree.head, spaces );
		  tree = tree.tail;
	  }
	  
  }
  
  static public void showTree( VarDecList tree, int spaces ) {
	  
	  while (tree != null) {
		  showTree( tree.head, spaces );
		  tree = tree.tail;
	  }
	  
  }

  static private void showTree( Dec tree, int spaces ) {
	  
    if( tree instanceof FunctionDec )
      showTree( (FunctionDec)tree, spaces );
    else {
      indent( spaces );
      System.out.println( "Illegal expression at line " + tree.pos  );
    }
    
  }
  
  static private void showTree( VarDec tree, int spaces ) {
	  
	  if ( tree instanceof SimpleDec )
		showTree( (SimpleDec)tree, spaces );
	  else if ( tree instanceof ArrayDec )
		showTree( (ArrayDec)tree, spaces );
	  else {
        indent( spaces );
        System.out.println( "Illegal expression at line " + tree.pos  );
	  }
	  
  }
  
  static private void showTree( Var tree, int spaces ) {
	  
	  if ( tree instanceof SimpleVar )
		showTree( (SimpleVar)tree, spaces );
	  else if ( tree instanceof IndexVar )
		showTree( (IndexVar)tree, spaces );
	  else {
        indent( spaces );
        System.out.println( "Illegal expression at line " + tree.pos  );
	  }
	  
  }
  
  static private void showTree( Exp tree, int spaces ) {
	  
	  if (tree instanceof VarExp )
		showTree( (VarExp)tree, spaces );
	  else if ( tree instanceof IntExp )
		showTree( (IntExp)tree, spaces );
	  else if ( tree instanceof CallExp )
		showTree( (CallExp)tree, spaces );
	  else if ( tree instanceof OpExp )
		showTree( (OpExp)tree, spaces );
	  else if ( tree instanceof AssignExp )
		showTree( (AssignExp)tree, spaces );
	  else if ( tree instanceof IfExp )
		showTree( (IfExp)tree, spaces );
	  else if ( tree instanceof WhileExp )
		showTree( (WhileExp)tree, spaces );
	  else if ( tree instanceof CompoundExp )
		showTree( (CompoundExp)tree, spaces );
	  else if ( tree instanceof ReturnExp )
		showTree( (ReturnExp)tree, spaces );
	  else {
        indent( spaces );
        /* v crashes code when not commented (NullPointerException) v */
        System.out.println( "Illegal expression at line "  );
	  }
  }
  
  static private void showTree( ArrayDec tree, int spaces ) {
	  
	  indent( spaces );
	  System.out.println( "ArrayDec:" );
	  spaces += SPACES;
      showTree( tree.typ, spaces );
      indent( spaces );
      System.out.println( tree.name );
      showTree( tree.size, spaces );
	  
  }
  
  static private void showTree( AssignExp tree, int spaces ) {
	  
	  indent( spaces );
	  System.out.println( "AssignExp:" );
	  spaces += SPACES;
      showTree( tree.lhs, spaces );
      spaces += SPACES;
      indent( spaces );
      System.out.println(" = ");
      showTree( tree.rhs, spaces );
	  
  }
  
  static private void showTree( CallExp tree, int spaces ) {
	  
	  indent( spaces );
	  System.out.println( "CallExp:" );
	  spaces += SPACES;
	  indent( spaces );
	  System.out.println( tree.func );
      showTree( tree.args, spaces );
	  
  }
  
  static private void showTree( CompoundExp tree, int spaces ) {
	  
	  indent( spaces );
	  System.out.println( "CompoundExp:" );
	  spaces += SPACES;
	  showTree( tree.decs, spaces );
      showTree( tree.exps, spaces );
	  
  }
  
  static private void showTree( FunctionDec tree, int spaces ) {
	  
	  indent( spaces );
	  System.out.println( "FunctionDec:" );
	  spaces += SPACES;
	  showTree( tree.result, spaces );
	  indent( spaces );
	  System.out.println( tree.func );
      showTree( tree.params, spaces );
      showTree( tree.body, spaces );
	  
  }
  
  static private void showTree( IfExp tree, int spaces ) {
	  
	  indent( spaces );
	  System.out.println( "IfExp:" );
	  spaces += SPACES;
	  showTree( tree.test, spaces );
      showTree( tree.thenpart, spaces );
      showTree( tree.elsepart, spaces );
	  
  }
  
  static private void showTree( IndexVar tree, int spaces ) {
	  
	  indent( spaces );
	  System.out.println( "IndexVar:" );
	  spaces += SPACES;
	  indent( spaces );
	  System.out.println( tree.name );
      showTree( tree.index, spaces );
	  
  }
  
  static private void showTree( IntExp tree, int spaces ) {
	  
	  indent( spaces );
	  System.out.println( "IntExp:" );
	  spaces += SPACES;
	  indent( spaces );
      System.out.println( tree.value );
	  
  }
  
  static private void showTree( NameTy tree, int spaces ) {
	  
	  indent( spaces );
	  System.out.println( "NameTy:" );
	  spaces += SPACES;
	  indent( spaces );
	  switch( tree.typ ) {
		  case NameTy.INT:
			System.out.println( "INT" );
			break;
		  case NameTy.VOID:
			System.out.println( "VOID" );
			break;
		  default:
			System.out.println( "Unrecognized operator at line " + tree.pos);
	  }
	  
  }

  static private void showTree( OpExp tree, int spaces ) {
	  
    indent( spaces );
    System.out.print( "OpExp:" ); 
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
    }
    spaces += SPACES;
    showTree( tree.left, spaces );
    showTree( tree.right, spaces );
     
  }
  
  static private void showTree( ReturnExp tree, int spaces ) {
	  
	  indent( spaces );
	  System.out.println( "ReturnExp:" );
	  spaces += SPACES;
      showTree( tree.exp, spaces );
	  
  }
  
  static private void showTree( SimpleDec tree, int spaces ) {
	  
	  indent( spaces );
	  System.out.println( "SimpleDec:" );
	  spaces += SPACES;
      showTree( tree.typ, spaces );
      spaces += SPACES;
      indent( spaces );
      System.out.println( tree.name );
	  
  }
  
  static private void showTree( SimpleVar tree, int spaces ) {
	  
	  indent( spaces );
	  System.out.println( "SimpleVar:" );
	  spaces += SPACES;
	  indent( spaces );
      System.out.println( tree.name );
	  
  }
  
  static private void showTree( VarExp tree, int spaces ) {
	  
	  indent( spaces );
	  System.out.println( "VarExp:" );
	  spaces += SPACES;
      showTree( tree.variable, spaces );
	  
  }
  
  static private void showTree( WhileExp tree, int spaces ) {
	  
	  indent( spaces );
	  System.out.println( "WhileExp:" );
	  spaces += SPACES;
      showTree( tree.test, spaces );
      showTree( tree.body, spaces );
	  
  }

}
