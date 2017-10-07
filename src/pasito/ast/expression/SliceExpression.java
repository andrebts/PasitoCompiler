package pasito.ast.expression;

import pasito.ast.PasitoVisitor;

/**
 * Created by ariel on 21/08/17.
 */
public class SliceExpression extends Expression {

    public Expression exp;
    public Expression low;
    public Expression high;

    public SliceExpression(Expression exp, Expression low, Expression high) {
        this.exp = exp;
        this.low = low;
        this.high = high;
    }

    @Override
    public Object accept(PasitoVisitor visitor) { return visitor.VisitSliceExpression(this); }
}
