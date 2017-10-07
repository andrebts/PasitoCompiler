package pasito.ast.statement;

import java.util.List;

import pasito.ast.PasitoVisitor;
import pasito.ast.expression.Expression;

/**
 * Created by ariel on 21/08/17.
 */
public class ForRange extends Statement {

    public Statement initStmt;
    public Expression rangExp;
    public Block body;

    public ForRange(Statement initStmt, Expression rangExp, Block body) {
        this.initStmt = initStmt;
        this.rangExp = rangExp;
        this.body = body;
    }
    
    /*public ForRange(List<Expression> initStmt, Expression rangExp, Block body) {
        this.initStmt = initStmt;
        this.rangExp = rangExp;
        this.body = body;
    }*/

    @Override
    public Object accept(PasitoVisitor visitor) {
        return visitor.VisitForRange(this);
    }
}
