package absyn;

public class VarDecList
{
    VarDec head;
    VarDecList tail;
    public VarDecList(VarDec head, VarDecList tail) 
    {
        this.head = head;
        this.tail = tail;
    }
}
