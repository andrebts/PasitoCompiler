package pasito.ast.statement;

import pasito.ast.PasitoVisitor;
import pasito.ast.expression.Expression;

/**
 * Created by ariel on 21/08/17.
 */
public class ExpressionStmt extends Statement {

    public Expression exp;

    public ExpressionStmt(Expression exp) {
        this.exp = exp;
    }

    @Override
    public Object accept(PasitoVisitor visitor) {
        return visitor.VisitExpressionStmt(this);
    }
}
