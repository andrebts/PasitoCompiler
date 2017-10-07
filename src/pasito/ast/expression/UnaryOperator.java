package pasito.ast.expression;

import pasito.ast.PasitoVisitor;

/**
 * Created by ariel on 21/08/17.
 */
public enum UnaryOperator {
    PLUS, MINUS, NOT, MULT;

    public Object accept(PasitoVisitor visitor) { return visitor.VisitUnaryOperator(this); }
}
