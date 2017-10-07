package CompiladorPasito;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import java_cup.runtime.ComplexSymbolFactory;
import java_cup.runtime.Symbol;
import pasito.PrettyPrint;
import pasito.ast.Program;
import pasito.ast.declaration.VarDecl;
import pasito.ast.expression.BinaryExpression;
import pasito.ast.expression.BinaryOperator;
import pasito.ast.expression.Expression;
import pasito.ast.expression.IdExpression;
import pasito.ast.expression.IntLiteral;
import pasito.ast.signature.FormalParameter;
import pasito.ast.signature.Signature;
import pasito.ast.statement.Block;
import pasito.ast.statement.DeclarationStm;
import pasito.ast.statement.ReturnStmt;
import pasito.ast.statement.ShortVarDecl;
import pasito.ast.statement.Statement;
import pasito.ast.topLevelDecl.FunctionDecl;
import pasito.ast.topLevelDecl.TopLevelDecl;
import pasito.ast.type.TypeName;
import pasito.syntax.Lexer;
import pasito.syntax.Parser;
import pasito.syntax.sym;
 
public class Main {
	public static void main(String[] args) {
        //String caminho = Paths.get("").toAbsolutePath().toString();
        /*String codigoFonte = "input.txt";
        
        ComplexSymbolFactory f = new ComplexSymbolFactory(); // Cria uma instância do ComplexSymbolFactory

        File file = new File(codigoFonte); // Lê arquivo de entrada em Pasito
        
        FileInputStream fis = null;
         
        try {
            fis = new FileInputStream(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Lexer lexical = new Lexer(f, fis); // Instância o Analisador Léxico

        try {
        	Symbol s = lexical.next_token();
			while (s.sym != sym.EOF && s != null) { // Enquanto houverem novos tokens...
			    s = lexical.next_token();
			}
			System.out.println("EOF");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		/*try {
	        Parser p = new Parser(new Lexer(new FileReader("input.txt")));
			Object result = p.parse().value;
	        System.out.println(result.toString());    
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		/*try {
	        Parser p = new Parser(new Lexer(new FileReader("input.txt")));
			Object result = p.parse().value;
			PrettyPrint printer = new PrettyPrint((Program) result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		try {
			PrettyPrint printer = new PrettyPrint(ProgramTeste());
			printer.erros.mostrar();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
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
