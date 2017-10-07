package pasito.ast.signature;

import pasito.ast.PasitoVisitor;
import pasito.ast.type.Type;

/**
 * Created by ariel on 21/08/17.
 */
public class FormalParameter {
    public Type type;
    public String name;

    public FormalParameter(String name, Type type) {
        this.name = name;
        this.type = type;
    }

    public Object accept(PasitoVisitor visitor) { return visitor.VisitFormalParameter(this); }
}
