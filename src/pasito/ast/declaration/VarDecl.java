package pasito.ast.declaration;

import java.util.List;

import pasito.ast.PasitoVisitor;
import pasito.ast.expression.Expression;
import pasito.ast.type.Type;

/**
 * Created by ariel on 21/08/17.
 */
public class VarDecl extends Declaration {
    public List<String> names;
    public Type type; // type or exp can be null
    public List<Expression> exps; // type or exp can be null

    public VarDecl(List<String> names, Type type, List<Expression> exps) {
        this.names = names;
        this.type = type;
        this.exps = exps;
    }

    @Override
    public Object accept(PasitoVisitor visitor) { return visitor.VisitVarDecl(this); }
}
