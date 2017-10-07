package pasito.staticSemantics.type;

/**
 * Created by Giovanny on 14/09/17.
 */
public class SliceType extends Type {
    public Type elementType;

    public SliceType(Type elementType) {
        this.elementType = elementType;
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
