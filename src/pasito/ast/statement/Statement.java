package pasito.ast.statement;

import pasito.ast.PasitoVisitor;

/**
 * Created by ariel on 21/08/17.
 */
public abstract class Statement {
    public abstract Object accept(PasitoVisitor visitor);
}
