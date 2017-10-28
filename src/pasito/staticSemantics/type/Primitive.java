package pasito.staticSemantics.type;

public class Primitive extends Type {
	public Kind kind;

	public Primitive(Kind kind) {
		this.kind = kind;
	}
	
	public static Primitive INT32 = new Primitive(Kind.INT);
	public static Primitive FLOAT64 = new Primitive(Kind.FLOAT);
	public static Primitive BOOLEAN = new Primitive(Kind.BOOLEAN);

	@Override
	public boolean equivalent(Type ty) {
		if (ty instanceof Primitive) {
			Primitive t = (Primitive) ty;
			if (this.kind == t.kind)
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
	
	@Override
	public String toString() {
		return kind.name();
	}
}
