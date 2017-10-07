package pasito.ast.topLevelDecl;

import pasito.ast.PasitoVisitor;
import pasito.ast.declaration.Declaration;

/**
 * Created by ariel on 20/08/17.
 */
public class Dec extends TopLevelDecl {
    public Declaration decl;

    public Dec(Declaration decl) {
        this.decl = decl;
    }

    @Override
    public Object accept(PasitoVisitor visitor) { return visitor.VisitDec(this); }
}
