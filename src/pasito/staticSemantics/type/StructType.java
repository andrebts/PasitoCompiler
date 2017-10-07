package pasito.staticSemantics.type;

import java.util.Map;

/**
 * Created by Giovanny on 14/09/17.
 */
public class StructType extends Type {

    Map<String, Type> fieldDecls;

    public StructType(Map<String, Type> fieldDecls) {
        this.fieldDecls = fieldDecls;
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
