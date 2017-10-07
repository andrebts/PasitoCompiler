package pasito.ast.expression;

import pasito.ast.PasitoVisitor;

/**
 * Created by ariel on 21/08/17.
 */
public class SelectorExpression extends Expression {

    public Expression exp;
    public String name;

    public SelectorExpression(Expression exp,String name) {
        this.exp = exp;
        this.name = name;
    }

    @Override
    public Object accept(PasitoVisitor visitor) { return visitor.VisitSelectorExpression(this);  }
}
