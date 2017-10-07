package pasito.staticSemantics.type;

/**
 * Created by Giovanny on 21/09/17.
 */
public class PointerType extends Type {
    public Type baseType;

    public PointerType(Type baseType) {
        this.baseType = baseType;
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
