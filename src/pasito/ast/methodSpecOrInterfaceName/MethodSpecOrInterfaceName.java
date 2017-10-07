package pasito.ast.methodSpecOrInterfaceName;

import pasito.ast.PasitoVisitor;

/**
 * Created by ariel on 21/08/17.
 */
public abstract class MethodSpecOrInterfaceName {
    public abstract Object accept(PasitoVisitor visitor);
}
