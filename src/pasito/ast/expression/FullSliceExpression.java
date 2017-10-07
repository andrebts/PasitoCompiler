package pasito.ast.expression;

import pasito.ast.PasitoVisitor;

/**
 * Created by ariel on 21/08/17.
 */
public class FullSliceExpression extends Expression {

    public Expression exp;
    public Expression low;
    public Expression high;
    public Expression max;

    public FullSliceExpression(Expression exp, Expression low, Expression high, Expression max) {
        this.exp = exp;
        this.low = low;
        this.high = high;
        this.max = max;
    }

    @Override
    public Object accept(PasitoVisitor visitor) {
        return visitor.VisitFullSliceExpression(this);
    }
}
