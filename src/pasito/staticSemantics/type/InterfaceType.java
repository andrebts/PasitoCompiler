package pasito.staticSemantics.type;

import pasito.ast.methodSpecOrInterfaceName.MethodSpecOrInterfaceName;

import java.util.List;

/**
 * Created by Giovanny on 14/09/17.
 */
public class InterfaceType extends Type {
    public List<MethodSpecOrInterfaceName> interfaceElems;

    public InterfaceType(List<MethodSpecOrInterfaceName> interfaceElems) {
        this.interfaceElems = interfaceElems;
    }

	@Override
	public boolean equivalent(Type ty) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean assignableTo(Type ty) {
		// TODO Auto-generated method stub
		return false;
	}

}
