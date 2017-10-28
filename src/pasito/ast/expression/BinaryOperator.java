package pasito.ast.expression;

import pasito.ast.PasitoVisitor;

/**
 * Created by ariel on 21/08/17.
 */
public enum BinaryOperator{
    AND, OR, MULT, DIV, PLUS, MINUS, LT, EQ;

    public Object accept(PasitoVisitor visitor) { return visitor.VisitBinaryOperator(this); }
}
