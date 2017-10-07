package pasito.ast.statement;

import pasito.ast.PasitoVisitor;

/**
 * Created by ariel on 21/08/17.
 */
public class EmptyStmt extends Statement {

    @Override
    public Object accept(PasitoVisitor visitor) { return visitor.VisitEmptyStmt(this); }
}
