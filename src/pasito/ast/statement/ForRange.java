package pasito.ast.statement;

import java.util.List;

import pasito.ast.PasitoVisitor;
import pasito.ast.expression.Expression;

/**
 * Created by ariel on 21/08/17.
 */
public class ForRange extends Statement {

    public List<Expression> exp;
    public Expression rangExp;
    public Block body;

    public ForRange(List<Expression> exp, Expression rangExp, Block body) {
        this.exp = exp;
        this.rangExp = rangExp;
        this.body = body;
    }

    @Override
    public Object accept(PasitoVisitor visitor) {
        return visitor.VisitForRange(this);
    }
}