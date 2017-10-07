package pasito.ast.expression;

import pasito.ast.PasitoVisitor;

/**
 * Created by ariel on 21/08/17.
 */
public class IntLiteral extends Expression {

    public int value;

    public IntLiteral(int value) {
        this.value = value;
    }

    @Override
    public Object accept(PasitoVisitor visitor) { return visitor.VisitIntLiteral(this); }
}
