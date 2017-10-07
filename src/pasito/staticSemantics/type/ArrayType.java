package pasito.staticSemantics.type;

/**
 * Created by Giovanny on 14/09/17.
 */
public class ArrayType extends Type {
    public Type elemType;
    public int length;

    public ArrayType (int length, Type elemType) {
        this.length = length;
        this.elemType = elemType;
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
