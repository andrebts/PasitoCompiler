package pasito.ast.expression;

import pasito.ast.PasitoVisitor;
import pasito.ast.element.KeyedElement;
import pasito.ast.type.Type;

import java.util.List;

/**
 * Created by ariel on 21/08/17.
 */
public class CompositeLit extends Expression {

    public Type type;
    public List<KeyedElement> elems;

    public CompositeLit(Type type, List<KeyedElement> elems) {
        this.type = type;
        this.elems = elems;
    }

    @Override
    public Object accept(PasitoVisitor visitor) { return visitor.VisitCompositLit(this); }
}
