package pasito.ast.type;

import pasito.ast.PasitoVisitor;

/**
 * Created by ariel on 21/08/17.
 */
public class SliceType extends Type {
    public Type elementType;

    public SliceType(Type elementType) {
        this.elementType = elementType;
    }

    @Override
    public Object accept(PasitoVisitor visitor) { return visitor.VisitSliceType(this); }
}
