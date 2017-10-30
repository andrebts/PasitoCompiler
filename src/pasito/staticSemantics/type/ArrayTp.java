package pasito.staticSemantics.type;

/**
 * Created by Giovanny on 14/09/17.
 */
public class ArrayTp extends Type {
    public Type elemType;
    public int length;

    public ArrayTp (int length, Type elemType) {
        this.length = length;
        this.elemType = elemType;
    }

	@Override
	public boolean equivalent(Type ty) {
		if (ty instanceof ArrayTp) {
			ArrayTp t = (ArrayTp) ty;
			if ((this.length == t.length) && (this.elemType == t.elemType))
				return true;
		}
		
		return false;
	}

	@Override
	public boolean assignableTo(Type ty) {
		if (equivalent(ty))
			return true;
		
		return false;
	}

}
