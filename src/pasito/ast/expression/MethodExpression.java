package pasito.ast.expression;

import pasito.ast.PasitoVisitor;
import pasito.ast.type.Type;

/**
 * Created by ariel on 21/08/17.
 */
public class MethodExpression extends Expression {
    public Type type;
    public String name;

    public MethodExpression(Type type, String name) {
        this.type = type;
        this.name = name;
    }

    @Override
    public Object accept(PasitoVisitor visitor) { return visitor.VisitMethodExpression(this); }
}
