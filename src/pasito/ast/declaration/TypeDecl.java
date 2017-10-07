package pasito.ast.declaration;

import pasito.ast.PasitoVisitor;
import pasito.ast.type.Type;

/**
 * Created by ariel on 21/08/17.
 */
public class TypeDecl extends Declaration {
    public Type type;
    public String name;

    public TypeDecl(String name, Type type) {
        this.name = name;
        this.type = type;
    }

    @Override
    public Object accept(PasitoVisitor visitor) { return visitor.VisitTypeDecl(this); }
}
