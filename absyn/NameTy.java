package absyn;

public class NameTy
{
    public final static int PLUS = 0;
    public final static int VOID = 1;

    int typ;
    int pos;
    public NameTy(int pos, int typ) 
    {
        this.pos = pos;
        this.typ = typ;
    }
}
