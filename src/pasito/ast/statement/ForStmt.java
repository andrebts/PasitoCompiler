package pasito.ast.statement;

import pasito.ast.PasitoVisitor;
import pasito.ast.expression.Expression;

/**
 * Created by ariel on 21/08/17.
 */
public class ForStmt extends Statement {

    public Block body;
    public Statement postStmt; // Can be null
    public Expression exp; // Can be null
    public Statement initStmt; // Can be null

    public ForStmt(Statement initStmt, Expression exp, Statement postStmt, Block body) {
        this.initStmt = initStmt;
        this.exp = exp;
        this.postStmt = postStmt;
        this.body = body;
    }

    @Override
    public Object accept(PasitoVisitor visitor) {
        return visitor.VisitForStmt(this);
    }
}
