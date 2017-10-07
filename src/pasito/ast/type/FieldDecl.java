package pasito.ast.type;

import pasito.ast.PasitoVisitor;

/**
 * Created by ariel on 21/08/17.
 */
public class FieldDecl {
    public String name;
    public Type type;
    public FieldDecl(String name, Type type) {
        this.name = name;
        this.type = type;
    }

    public Object accept(PasitoVisitor visitor) { return visitor.VisitFieldDecl(this); }

}
