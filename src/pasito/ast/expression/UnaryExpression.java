package pasito.ast.expression;

import pasito.ast.PasitoVisitor;

/**
 * Created by ariel on 21/08/17.
 */
public class UnaryExpression extends Expression {
    public UnaryOperator op;
    public Expression exp;

    public UnaryExpression(UnaryOperator op, Expression exp) {
        this.op = op;
        this.exp = exp;
    }

    @Override
    public Object accept(PasitoVisitor visitor) { return visitor.VisitUnaryExpression(this); }
}
