import java.io.*;
   
class cm {
  static public void main(String argv[]) {    
    /* Start the parser */
    try 
    {
      parser p = new parser(new Lexer(new FileReader(argv[0])));
      Object result = p.parse().value;
      System.out.println("Parse Tree Complete");
    } catch (Exception e) {
      /* do cleanup here -- possibly rethrow e */
      e.printStackTrace();
    }
  }
}


