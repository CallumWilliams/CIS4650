package SymbolTable;

public class Entry
{
   // String name;
    int scope;
    int type;
    int dim;
    Entry next; 
    
    public Entry(/*String name,*/  int type, int scope, int dimensions, Entry next)
    {
     //   this.name = name;
        this.scope = scope;
        this.type = type;
        this.dim = dimensions;
        this.next = next;
    } 
}
