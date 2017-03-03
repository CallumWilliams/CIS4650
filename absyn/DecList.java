package absyn;

public class DecList
{
    Dec head;
    DecList tail;
    public DecList(Dec head, DecList tail) 
    {
        this.head = head;
        this.tail = tail;
    }
}
