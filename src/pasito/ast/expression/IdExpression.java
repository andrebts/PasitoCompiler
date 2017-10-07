package pasito.ast.expression;

import pasito.ast.PasitoVisitor;

/**
 * Created by ariel on 21/08/17.
 */
public class IdExpression extends Expression {

    public String name;

    public IdExpression(String name) {
        this.name = name;
    }

    @Override
    public Object accept(PasitoVisitor visitor) { return visitor.VisitIdExpression(this); }
}
