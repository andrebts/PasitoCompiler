package pasito.ast.type;


import pasito.ast.PasitoVisitor;
import pasito.ast.expression.Expression;

/**
 * Created by ariel on 21/08/17.
 */
public class ArrayType extends Type {
    public Type elemType;
    public Expression length;

    public ArrayType (Expression length, Type elemType) {
        this.length = length;
        this.elemType = elemType;
    }

    @Override
    public Object accept(PasitoVisitor visitor) { return visitor.VisitArrayType(this); }
}
