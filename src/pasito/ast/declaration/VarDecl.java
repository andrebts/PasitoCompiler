package pasito.ast.declaration;

import pasito.ast.PasitoVisitor;
import pasito.ast.expression.Expression;
import pasito.ast.type.Type;

/**
 * Created by ariel on 21/08/17.
 */
public class VarDecl extends Declaration {
    public String name;
    public Type type; // type or exp can be null
    public Expression exp; // type or exp can be null

    public VarDecl(String name, Type type, Expression exp) {
        this.name = name;
        this.type = type;
        this.exp = exp;
    }

    @Override
    public Object accept(PasitoVisitor visitor) { return visitor.VisitVarDecl(this); }
}
