package pasito.staticSemantics.type;

public class Primitive extends Type {
	public Kind kind;

	private Primitive(Kind kind) {
		this.kind = kind;
	}
	
	public static Primitive INT64 = new Primitive(Kind.INT);
	public static Primitive FLOAT64 = new Primitive(Kind.FLOAT);
	public static Primitive BOOLEAN = new Primitive(Kind.BOOLEAN);

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
