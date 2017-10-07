package pasito.ast.type;

import pasito.ast.PasitoVisitor;

/**
 * Created by ariel on 21/08/17.
 */
public class PointerType extends Type {
    public Type baseType;

    public PointerType(Type baseType) {
        this.baseType = baseType;
    }

    @Override
    public Object accept(PasitoVisitor visitor) { return visitor.VisitBaseType(this); }
}
