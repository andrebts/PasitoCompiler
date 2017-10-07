package pasito.ast.element;

import pasito.ast.PasitoVisitor;
import pasito.ast.expression.Expression;

/**
 * Created by ariel on 21/08/17.
 */
public class KeyedElement {

    public Element elem;
    public Expression exp;

    public KeyedElement(Expression exp, Element elem) {
        this.exp = exp;
        this.elem = elem;
    }

    public Object accept(PasitoVisitor visitor) {
        return visitor.VisitKeyedElement(this);
    }
}
