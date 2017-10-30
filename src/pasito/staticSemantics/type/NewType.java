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
		if (ty instanceof NewType) {
			NewType t = (NewType) ty;
			if (this.underlyingType == t.underlyingType)
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
