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
		if (ty instanceof SliceType) {
			SliceType t = (SliceType) ty;
			if (this.elementType == t.elementType)
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
