package pasito.ast.element;

import pasito.ast.PasitoVisitor;

/**
 * Created by ariel on 21/08/17.
 */
public abstract class Element {
    public abstract Object accept(PasitoVisitor visitor);
}
