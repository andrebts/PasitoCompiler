package pasito.ast.statement;


import pasito.ast.PasitoVisitor;
import pasito.ast.declaration.Declaration;

/**
 * Created by ariel on 21/08/17.
 */
public class DeclarationStm extends Statement {

    public Declaration decl;

    public DeclarationStm(Declaration decl) {
        this.decl = decl;
    }
    @Override
    public Object accept(PasitoVisitor visitor) {
        return visitor.VisitDeclarationStm(this);
    }
}
