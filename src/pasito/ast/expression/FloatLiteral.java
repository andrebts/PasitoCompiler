package pasito.ast.expression;

import pasito.ast.PasitoVisitor;

/**
 * Created by ariel on 21/08/17.
 */
public class FloatLiteral extends Expression {

    public float value;

    public FloatLiteral(float value) {
        this.value = value;
    }

    @Override
    public Object accept(PasitoVisitor visitor) { return visitor.VisitFloatLiteral(this); }
}
