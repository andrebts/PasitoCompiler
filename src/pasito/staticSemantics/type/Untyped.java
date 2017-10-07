package pasito.staticSemantics.type;

public class Untyped extends Type {
	public Kind kind;

	public Untyped(Kind kind) {
		super();
		this.kind = kind;
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
