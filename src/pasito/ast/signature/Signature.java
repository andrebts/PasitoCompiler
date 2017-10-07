package pasito.ast.signature;

import java.util.List;

import pasito.ast.PasitoVisitor;


/**
 * Created by ariel on 20/08/17.
 */
public class Signature {
    public List<FormalParameter> outPars;
    public FormalParameter variadicPar; /* can be null */
    public List<FormalParameter> inPars;

    public Signature(List<FormalParameter> inPars, FormalParameter variadicPar,
                     List<FormalParameter> outPars) {
        this.inPars = inPars;
        this.variadicPar = variadicPar;
        this.outPars = outPars;
    }

    public Object accept(PasitoVisitor visitor) { return visitor.VisitSignature(this); }

}
