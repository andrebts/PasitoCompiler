package pasito.ast;

import java.util.List;

import pasito.ast.topLevelDecl.TopLevelDecl;

/**
 * Created by ariel on 20/08/17.
 */

public class Program {
    public List<TopLevelDecl> declarations;

    public Program(List<TopLevelDecl> declarations) {
        this.declarations = declarations;
    }

    public Object accept(PasitoVisitor visitor) {
        return visitor.VisitProgram(this);
    }

}
