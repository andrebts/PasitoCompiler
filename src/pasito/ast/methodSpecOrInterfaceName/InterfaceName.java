package pasito.ast.methodSpecOrInterfaceName;

import pasito.ast.PasitoVisitor;

/**
 * Created by ariel on 21/08/17.
 */
public class InterfaceName extends MethodSpecOrInterfaceName {

    public String name;

    public InterfaceName(String name) {
        this.name = name;
    }

    @Override
    public Object accept(PasitoVisitor visitor) { return visitor.VisitInterfaceName(this); }
}
