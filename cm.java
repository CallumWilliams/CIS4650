import java.io.*;
   
class cm {

  public static Boolean displayTree = false;
  static public void main(String argv[]) {    
  
    for(String s : argv)
    {
        if(s.equals("-a"))
        {
            displayTree = true;
        }   
    }

    try 
    {
      parser p = new parser(new Lexer(new FileReader(argv[0])));
      Object result = p.parse().value;

    } catch (Exception e) {
      /* do cleanup here -- possibly rethrow e */
      e.printStackTrace();
    }
  }
}


