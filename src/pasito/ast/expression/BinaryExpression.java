package pasito.ast.expression;

import pasito.ast.PasitoVisitor;


/**
 * Created by ariel on 21/08/17.
 */
public class BinaryExpression extends Expression {
    public BinaryOperator op;
    public Expression leftExp, rightExp;

    public BinaryExpression(BinaryOperator op, Expression leftExp, Expression rightExp) {
        this.op = op;
        this.leftExp = leftExp;
        this.rightExp = rightExp;

    }

    @Override
    public Object accept(PasitoVisitor visitor) { return visitor.VisitBinaryExpression(this); }
}
