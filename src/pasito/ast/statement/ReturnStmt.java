package pasito.ast.statement;

import pasito.ast.PasitoVisitor;
import pasito.ast.expression.Expression;

import java.util.List;

/**
 * Created by ariel on 21/08/17.
 */
public class ReturnStmt extends Statement {

    public List<Expression> exps;

    public ReturnStmt(List<Expression> exps) {
        this.exps = exps;
    }

    @Override
    public Object accept(PasitoVisitor visitor) { return visitor.VisitReturnStmt(this); }
}
