package pasito.staticSemantics.binding;

import java.util.List;

import pasito.staticSemantics.type.Type;

public class Meth implements Binding {
	public Type receiverType;
	public List<Type> outTypes; /* can be null */
	public List<Type> inTypes;
	public Type variadicPar; /* can be null */
	
	public Meth(Type receiverType, List<Type> outTypes, List<Type> inTypes, Type variadicType) {
		super();
		this.receiverType = receiverType;
		this.outTypes = outTypes;
		this.inTypes = inTypes;
		this.variadicPar = variadicType;
	}
}
