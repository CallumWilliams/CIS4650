package SymbolTable;

public class Entry
{
   // String name;
    int scope;
    int type;
    Entry next; 
    
    public Entry(/*String name,*/  int type, int scope, Entry next)
    {
     //   this.name = name;
        this.scope = scope;
        this.type = type;
        this.next = next;
    } 
}
