package pasito.ast.statement;

import pasito.ast.PasitoVisitor;
import pasito.ast.expression.Expression;

/**
 * Created by ariel on 21/08/17.
 */
public class IfStmt extends Statement {

    public Block block;
    public Expression exp;
    public Statement initStmt; // can be null

    public IfStmt(Statement initStmt, Expression exp, Block block) {
        this.initStmt = initStmt;
        this.exp = exp;
        this.block = block;
    }

    @Override
    public Object accept(PasitoVisitor visitor) {
        return visitor.VisitIfStmt(this);
    }
}
