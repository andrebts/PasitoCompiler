package pasito.staticSemantics.binding;

import pasito.staticSemantics.type.Type;

public class Ty implements Binding {
	public Type type;

	public Ty(Type type) {
		super();
		this.type = type;
	}	
}
