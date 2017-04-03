import absyn.*;

public class AssemblyGen
{
  
  
  static public void generateAssembly( DecList tree ) {
      
      // write prelude
      while (tree != null) {
          generateAssembly( tree.head );
          tree = tree.tail;
      }
      // write finale
      
  }
  
  static public void generateAssembly( ExpList tree ) {
      
      while (tree != null) {
          generateAssembly( tree.head );
          tree = tree.tail;
      }
      
  }
  
  static public void generateAssembly( VarDecList tree ) {
      
      while (tree != null) {
          generateAssembly( tree.head );
          tree = tree.tail;
      }
      
  }

  static private void generateAssembly( Dec tree ) {
      
    if( tree instanceof FunctionDec )
      generateAssembly( (FunctionDec)tree );
    else if ( tree instanceof VarDec )
        generateAssembly( (VarDec)tree );
    
  }
  
  static private void generateAssembly( VarDec tree ) {
      
      if ( tree instanceof SimpleDec )
        generateAssembly( (SimpleDec)tree );
      else if ( tree instanceof ArrayDec )
        generateAssembly( (ArrayDec)tree );
      
  }
  
  static private void generateAssembly( Var tree ) {
      
      if ( tree instanceof SimpleVar )
        generateAssembly( (SimpleVar)tree );
      else if ( tree instanceof IndexVar )
        generateAssembly( (IndexVar)tree );
      
  }
  
  static private void generateAssembly( Exp tree ) {
      
      if (tree instanceof VarExp )
        generateAssembly( (VarExp)tree );
      else if ( tree instanceof IntExp )
        generateAssembly( (IntExp)tree );
      else if ( tree instanceof CallExp )
        generateAssembly( (CallExp)tree );
      else if ( tree instanceof OpExp )
        generateAssembly( (OpExp)tree );
      else if ( tree instanceof AssignExp )
        generateAssembly( (AssignExp)tree );
      else if ( tree instanceof IfExp )
        generateAssembly( (IfExp)tree );
      else if ( tree instanceof WhileExp )
        generateAssembly( (WhileExp)tree );
      else if ( tree instanceof CompoundExp )
        generateAssembly( (CompoundExp)tree );
      else if ( tree instanceof ReturnExp )
        generateAssembly( (ReturnExp)tree );
        
  }
  
  static private void generateAssembly( ArrayDec tree ) {
      
      
      
  }
  
  static private void generateAssembly( AssignExp tree ) {
      
      
      
  }
  
  static private void generateAssembly( CallExp tree ) {
	  
	  
      
  }
  
  static private void generateAssembly( CompoundExp tree ) {
	  
	  
      
  }
  
  static private void generateAssembly( FunctionDec tree ) {
      
      
      
  }
  
  static private void generateAssembly( IfExp tree ) {
	  
	  
      
  }
  
  static private void generateAssembly( IndexVar tree ) {
	  
	  
      
  }
  
  static private void generateAssembly( IntExp tree ) {
      
      
      
  }
  
  static private void generateAssembly( NameTy tree ) {
	  
	  
      
  }

  static private void generateAssembly( OpExp tree ) {
	  
	  
     
  }
  
  static private void generateAssembly( ReturnExp tree ) {
	  
	  
      
  }
  
  static private void generateAssembly( SimpleDec tree ) {
	  
	  
      
  }
  
  static private void generateAssembly( SimpleVar tree ) {
	  
      
           
  }
  
  static private void generateAssembly( VarExp tree ) {
	  
      
      
  }
  
  static private void generateAssembly( WhileExp tree ) {
	  
	  
      
  }

}
