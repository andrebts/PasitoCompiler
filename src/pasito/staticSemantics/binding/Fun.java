package pasito.staticSemantics.binding;

import java.util.List;

import pasito.staticSemantics.type.Type;

public class Fun implements Binding {
	public List<Type> outTypes; /* can be null */
	public List<Type> inTypes;
	public Type variadicType; /* can be null */
	
	public Fun(List<Type> outTypes, List<Type> inTypes, Type variadicType) {
		this.outTypes = outTypes;
		this.inTypes = inTypes;
		this.variadicType = variadicType;
	}
}
