package pasito.ast.topLevelDecl;

import pasito.ast.PasitoVisitor;

/**
 * Created by ariel on 20/08/17.
 */
public abstract class TopLevelDecl {

    public abstract Object accept(PasitoVisitor visitor);
}
