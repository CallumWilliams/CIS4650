import java.io.*;
import java.util.HashMap;
   
class cm {

  public static Boolean displayTree = false;
  public static HashMap<String, Integer> hm = new HashMap<String, Integer>();
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
      System.out.println(hm.size());

    } catch (Exception e) {
      /* do cleanup here -- possibly rethrow e */
      e.printStackTrace();
    }
  }
}


