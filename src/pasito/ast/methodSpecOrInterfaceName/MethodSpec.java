package pasito.ast.methodSpecOrInterfaceName;

import pasito.ast.PasitoVisitor;
import pasito.ast.signature.Signature;

/**
 * Created by ariel on 21/08/17.
 */
public class MethodSpec extends MethodSpecOrInterfaceName {
    public String name;
    public Signature sig;

    public MethodSpec(String name, Signature sig) {
        this.name = name;
        this.sig = sig;
    }

    @Override
    public Object accept(PasitoVisitor visitor) { return visitor.VisitMethodSpec(this); }
}
