package pasito.staticSemantics.type;

/**
 * Created by Giovanny on 14/09/17.
 */
public class NewType extends Type{
    public Type underlyingType;

    public NewType(Type type) {
        this.underlyingType = type;
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
