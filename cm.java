import java.io.*;
import java.util.*;


public class cm {

  public static Boolean displayTree = false;
  public static Boolean displaySymbolTable = false;
  
  //public static HashMap<String, ArrayList<String>> hm = new HashMap<String, ArrayList<String>>();
  public static ArrayList<String> int_list = new ArrayList<String>();
  public static ArrayList<String> void_list = new ArrayList<String>();
  
  static public void main(String argv[]) { 
  
    for(String s : argv)
    {
        if(s.equals("-a")) displayTree = true;
        else if (s.equals("-s")) displaySymbolTable = true;
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


