package pasito.ast.topLevelDecl;

import pasito.ast.signature.FormalParameter;
import pasito.ast.signature.Signature;
import pasito.ast.statement.Block;
import pasito.ast.PasitoVisitor;

/**
 * Created by ariel on 20/08/17.
 */
public class MethodDecl extends TopLevelDecl {
    public FormalParameter receiver;
    public String name;
    public Signature sig;
    public Block body;

    public MethodDecl(FormalParameter receiver, String name, Signature sig, Block body) {
        this.receiver = receiver;
        this.name = name;
        this.sig = sig;
        this.body = body;
    }

    @Override
    public Object accept(PasitoVisitor visitor) {
        return visitor.VisitMethodDecl(this);
    }
}
