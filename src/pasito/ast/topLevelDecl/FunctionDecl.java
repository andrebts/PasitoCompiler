package pasito.ast.topLevelDecl;

import pasito.ast.signature.Signature;
import pasito.ast.statement.Block;
import pasito.ast.PasitoVisitor;

/**
 * Created by ariel on 20/08/17.
 */
public class FunctionDecl extends TopLevelDecl {
    public String name;
    public Signature sig;
    public Block body;

    public FunctionDecl(String name, Signature sig, Block body) {
        this.name = name;
        this.sig = sig;
        this.body = body;
    }


    @Override
    public Object accept(PasitoVisitor visitor) {
        return visitor.VisitFunctionDecl(this);
    }
}
