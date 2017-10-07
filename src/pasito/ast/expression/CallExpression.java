package pasito.ast.expression;

import java.util.List;

import pasito.ast.PasitoVisitor;

/**
 * Created by ariel on 21/08/17.
 */
public class CallExpression extends Expression {

    public Expression variadicArg;
    public List<Expression> args;
    public Expression exp;

    public CallExpression(Expression exp, List<Expression> args, Expression variadicArg) {
        this.exp = exp;
        this.args = args;
        this.variadicArg = variadicArg;
    }

    @Override
    public Object accept(PasitoVisitor visitor) {
        return visitor.VisitCallExpression(this);
    }
}
