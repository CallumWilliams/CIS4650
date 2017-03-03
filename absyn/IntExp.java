package absyn;

public class IntExp extends Exp
{
    int value;
    
    public IntExp(int pos, int value) 
    {
        this.pos = pos;
        this.value = value;
    }
}
