package absyn;

public class ArrayDec extends VarDec
{
    NameTy typ;
    String name;
    IntExp size;
    public ArrayDec(int pos, NameTy typ, String name, IntExp size) 
    {
        this.pos = pos;
        this.typ = typ;
        this.name = name;
        this.size = size;
    }
}
