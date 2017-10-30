package pasito.ast.statement;

import pasito.ast.PasitoVisitor;
import pasito.ast.expression.Expression;

/**
 * Created by ariel on 21/08/17.
 */
public class IncrStmt extends Statement {

    public Expression exp;
    public boolean up;

    public IncrStmt(Expression exp, boolean up) {
        this.exp = exp;
        this.up = up;
    }

    @Override
    public Object accept(PasitoVisitor visitor) {
        return visitor.IncrStmt(this);
    }
}
