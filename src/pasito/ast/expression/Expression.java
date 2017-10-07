package pasito.ast.expression;

import pasito.ast.PasitoVisitor;

/**
 * Created by ariel on 21/08/17.
 */
public abstract class Expression {
    public abstract Object accept (PasitoVisitor visitor);
}
