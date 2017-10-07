package pasito.ast.type;

import pasito.ast.PasitoVisitor;

/**
 * Created by ariel on 21/08/17.
 */
public abstract class Type {

    public abstract Object accept(PasitoVisitor visitor);
}
