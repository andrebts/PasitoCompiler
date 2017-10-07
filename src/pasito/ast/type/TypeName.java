package pasito.ast.type;

import pasito.ast.PasitoVisitor;

/**
 * Created by ariel on 21/08/17.
 */
public class TypeName extends Type{
    public String name;

    public TypeName(String name) {
        this.name = name;
    }

    @Override
    public Object accept(PasitoVisitor visitor) { return visitor.VisitTypeName(this); }
}
