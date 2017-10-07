package pasito.ast.statement;

import pasito.ast.PasitoVisitor;
import pasito.ast.expression.Expression;

import java.util.List;

/**
 * Created by ariel on 21/08/17.
 */
public class ShortVarDecl extends Statement {

    public List<Expression> exps;
    public List<String> names; // Obs: na sintaxe concreta é uma lista de expressões
    						   // Ao gerar, é preciso verificar que sejam ids.

    public ShortVarDecl(List<String> names, List<Expression> exps) {
        this.names = names;
        this.exps = exps;
    }

    @Override
    public Object accept(PasitoVisitor visitor) {
        return visitor.VisitShortVarDecl(this);
    }
}
