package absyn;

public class VarExp extends Exp
{
    Var variable;
    
    public VarExp(int pos, Var variable) 
    {
        this.pos = pos;
        this.variable = variable;
    }
}
