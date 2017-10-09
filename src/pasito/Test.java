package pasito;
import java.util.*;

import pasito.ast.Program;
import pasito.ast.declaration.VarDecl;
import pasito.ast.expression.BinaryExpression;
import pasito.ast.expression.BinaryOperator;
import pasito.ast.expression.IdExpression;
import pasito.ast.expression.IntLiteral;
import pasito.ast.signature.FormalParameter;
import pasito.ast.signature.Signature;
import pasito.ast.statement.Block;
import pasito.ast.statement.DeclarationStm;
import pasito.ast.statement.ReturnStmt;
import pasito.ast.statement.Statement;
import pasito.ast.topLevelDecl.FunctionDecl;
import pasito.ast.topLevelDecl.TopLevelDecl;
import pasito.ast.type.TypeName;

public class Test {
	public static Program ProgramTeste() {
        /* 	func split(sum int) (x, y int) {
				x = sum * 4 / 9
				y = sum - x
				return
			}
         */
		
        List<FormalParameter> inPars = new LinkedList<>();
        inPars.add(new FormalParameter("sum", new TypeName("int")));
        
        List<FormalParameter> outPars = new LinkedList<>();
        outPars.add(new FormalParameter("x", new TypeName("int")));
        outPars.add(new FormalParameter("y", new TypeName("int")));

        Signature sig = new Signature(inPars, null, outPars);
        
        List<Statement> stmts = new LinkedList<>();
        BinaryExpression op1 = new BinaryExpression(BinaryOperator.MULT, new IdExpression("sum"), new IntLiteral(4));
        BinaryExpression op2 = new BinaryExpression(BinaryOperator.DIV, op1, new IntLiteral(9));
        VarDecl vdecl = new VarDecl("x", null, op2);
        DeclarationStm declarationStm = new DeclarationStm(vdecl);
        stmts.add(declarationStm);
        
        BinaryExpression op3 = new BinaryExpression(BinaryOperator.MINUS, new IdExpression("sum"), new IdExpression("x"));
        VarDecl vdecl2 = new VarDecl("y", null, op3);
        DeclarationStm declarationStm2 = new DeclarationStm(vdecl2);
        stmts.add(declarationStm2);

        ReturnStmt r = new ReturnStmt(null);
        stmts.add(r);

        Block body = new Block(stmts);
        
        FunctionDecl fdcl = new FunctionDecl("split", sig, body);
        
        List<TopLevelDecl> declarations = new LinkedList<>();
        declarations.add(fdcl);
        Program program = new Program(declarations);

        return program;
    }
}
