package pasito.ast.expression;

import pasito.ast.signature.Signature;
import pasito.ast.statement.Block;
import pasito.ast.PasitoVisitor;

/**
 * Created by ariel on 21/08/17.
 */
public class FunctionLiteral extends Expression {
    public Signature sig;
    public Block body;

    public FunctionLiteral(Signature sig, Block body) {
        this.sig = sig;
        this.body = body;
    }

    @Override
    public Object accept(PasitoVisitor visitor) { return visitor.VisitFunctionLiteral(this); }
}
