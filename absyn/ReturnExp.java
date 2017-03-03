package absyn;

public class ReturnExp extends Exp
{
    Exp exp;
    public ReturnExp(int pos, Exp exp) 
    {
        this.pos = pos;
        this.exp = exp;
    }
}
