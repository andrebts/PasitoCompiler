package pasito.ast.expression;

import pasito.ast.PasitoVisitor;

/**
 * Created by andre on 27/09/17.
 */
public class BooleanLiteral extends Expression {

    public boolean value;

    public BooleanLiteral(boolean value) {
        this.value = value;
    }

    @Override
    public Object accept(PasitoVisitor visitor) { return visitor.VisitBoolLiteral(this); }
}
