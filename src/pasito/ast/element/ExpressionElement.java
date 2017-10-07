package pasito.ast.element;

import pasito.ast.PasitoVisitor;
import pasito.ast.expression.Expression;

/**
 * Created by ariel on 21/08/17.
 */
public class ExpressionElement extends Element {

    public Expression exp;

    public ExpressionElement(Expression exp) {
        this.exp = exp;
    }


    @Override
    public Object accept(PasitoVisitor visitor) {
        return visitor.VisitExpressionElement(this);
    }
}
