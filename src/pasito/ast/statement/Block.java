package pasito.ast.statement;

import java.util.List;

import pasito.ast.PasitoVisitor;

/**
 * Created by ariel on 21/08/17.
 */
public class Block extends Statement {

    public List<Statement> stmts;

    public Block(List<Statement> stmts) {
        this.stmts = stmts;
    }

    @Override
    public Object accept(PasitoVisitor visitor) {
        return visitor.VisitBlock(this);
    }
}
