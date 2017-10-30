package pasito.staticSemantics.type;

/**
 * Created by Giovanny on 21/09/17.
 */
public class PointerTp extends Type {
    public Type baseType;

    public PointerTp(Type baseType) {
        this.baseType = baseType;
    }

    @Override
	public boolean equivalent(Type ty) {
		if (ty instanceof PointerTp) {
			PointerTp t = (PointerTp) ty;
			if (this.baseType == t.baseType)
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
