package pasito.ast.statement;

import pasito.ast.PasitoVisitor;
import pasito.ast.expression.Expression;

import java.util.List;

/**
 * Created by ariel on 21/08/17.
 */
public class Assignment extends Statement {

    public List<Expression> leftExps, rightExps;

    public Assignment(List<Expression> leftExps, List<Expression> rightExps) {
        this.leftExps = leftExps;
        this.rightExps = rightExps;
    }

    @Override
    public Object accept(PasitoVisitor visitor) {
        return visitor.VisitAssignment(this);
    }
}
