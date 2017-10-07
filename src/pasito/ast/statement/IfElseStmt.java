package pasito.ast.statement;

import pasito.ast.PasitoVisitor;
import pasito.ast.expression.Expression;

/**
 * Created by ariel on 21/08/17.
 */
public class IfElseStmt extends Statement {

    public Statement initStmt;
    public Expression exp;
    public Block leftBlock;
    public Block rightBlock;

    public IfElseStmt(Statement initStmt, Expression exp, Block leftBlock, Block rightBlock) {
        this.initStmt = initStmt;
        this.exp = exp;
        this.leftBlock = leftBlock;
        this.rightBlock = rightBlock;
    }

    @Override
    public Object accept(PasitoVisitor visitor) {
        return visitor.VisitIfElseStmt(this);
    }
}
