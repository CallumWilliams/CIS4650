package SymbolTable;

public class Entry
{
    int scope;
    int type;
    int dim;
    Entry next; 
    int offset;
    int pc;
    
    public Entry(int type, int scope, int dimensions, Entry next, int offset, int pc)
    {
        this.scope = scope;
        this.type = type;
        this.dim = dimensions;
        this.next = next;
        this.offset = offset;
        this.pc = pc;
    } 
}
