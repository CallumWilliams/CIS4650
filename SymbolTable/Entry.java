package SymbolTable;

public class Entry
{
    int scope;
    int type;
    int dim;
    Entry next; 
    
    public Entry(int type, int scope, int dimensions, Entry next)
    {
        this.scope = scope;
        this.type = type;
        this.dim = dimensions;
        this.next = next;
    } 
}
