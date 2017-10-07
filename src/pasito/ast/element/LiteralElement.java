package pasito.ast.element;

import pasito.ast.PasitoVisitor;

import java.util.List;

/**
 * Created by ariel on 21/08/17.
 */
public class LiteralElement extends Element {

    public List<KeyedElement> keyedElems;

    public LiteralElement(List<KeyedElement> keyedElems) {
        this.keyedElems = keyedElems;
    }

    @Override
    public Object accept(PasitoVisitor visitor) {
        return visitor.VisitLiteralElement(this);
    }
}
