package absyn;

public class ExpList
{
    Exp head;
    ExpList tail;
    public ExpList(Exp head, ExpList tail) 
    {
        this.head = head;
        this.tail = tail;
    }
}
