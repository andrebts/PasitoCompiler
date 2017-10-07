package pasito.ast.expression;

import pasito.ast.PasitoVisitor;

/**
 * Created by ariel on 21/08/17.
 */
public class IndexExpression extends Expression{

    public Expression exp;
    public Expression indexExp;

    public IndexExpression(Expression exp, Expression indexExp) {
        this.exp = exp;
        this.indexExp = indexExp;
    }

    @Override
    public Object accept(PasitoVisitor visitor) { return visitor.VisitIndexExpression(this); }
}
