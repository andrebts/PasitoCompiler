package pasito.staticSemantics.type;

import java.util.Iterator;
import java.util.ListIterator;
import java.util.Map;
import java.util.Map.Entry;

import pasito.ast.expression.Expression;

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
		boolean eq = true;
    	if (ty instanceof StructType) {
			StructType t = (StructType) ty;
			Iterator<Entry<String, Type>> ite = null;
			if (t != null) 
				ite = t.fieldDecls.entrySet().iterator();
				
			if (ite != null) {
				for (Entry<String, Type> entry : fieldDecls.entrySet())
				{
					Entry<String, Type> entryT = null;
					if (ite.hasNext()) 
						entryT = ite.next();
					else 
						eq = false;
					
					if (entryT != null) {
						if ((entry.getKey() != entryT.getKey()) || (entry.getValue() != entryT.getValue())) {
							eq = false;
						}
					} else {
						eq = false;
					}
				}
	    	}
		}

		return eq;
	}
	@Override
	public boolean assignableTo(Type ty) {
		if (equivalent(ty))
			return true;
		
		return false;
	}
}
