package pasito.ast.type;

import pasito.ast.PasitoVisitor;
import pasito.ast.methodSpecOrInterfaceName.MethodSpecOrInterfaceName;

import java.util.List;

/**
 * Created by ariel on 21/08/17.
 */
public class InterfaceType extends Type {
    public List<MethodSpecOrInterfaceName> interfaceElems;

    public InterfaceType(List<MethodSpecOrInterfaceName> interfaceElems) {
        this.interfaceElems = interfaceElems;
    }

    @Override
    public Object accept(PasitoVisitor visitor) { return visitor.VisitInterfaceType(this); }
}
