package pasito.ast.declaration;

import pasito.ast.PasitoVisitor;

/**
 * Created by ariel on 21/08/17.
 */
public abstract class Declaration {

    public abstract Object accept (PasitoVisitor visitor);
}
