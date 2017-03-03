package absyn;

public class CallExp extends Exp
{
    String func;
    ExpList args;
    public CallExp(int pos, String func, ExpList args) 
    {
        this.pos  = pos;
        this.func = func;
        this.args = args;
    }
}
