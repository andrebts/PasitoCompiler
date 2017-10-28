package CompiladorPasito;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;

import java_cup.runtime.ComplexSymbolFactory;
import java_cup.runtime.Symbol;
import pasito.PrettyPrint;
import pasito.Test;
import pasito.ast.Program;
import pasito.staticSemantics.Analyser;
import pasito.syntax.Lexer;
import pasito.syntax.Parser;
import pasito.syntax.sym;
 
public class Main {
	public static void main(String[] args) throws Exception{
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
			//System.out.println("EOF");
		} catch (IOException e) {
			e.printStackTrace();
		}*/
		
		/*try {
	        Parser p = new Parser(new Lexer(new FileReader("input.txt")));
			Object result = p.parse().value;
	        System.out.println("Sucesso :)");    
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		
		/*try {
			PrettyPrint printer = new PrettyPrint(Test.ProgramTeste());
			//printer..mostrar();
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		
		/*try {
	        Parser p = new Parser(new Lexer(new FileReader("input.txt")));
			Object result = p.parse().value;
			PrettyPrint printer = new PrettyPrint((Program) result);
			//printer.erros.mostrar();
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		
		
		Parser p = null;
		try {
	        p = new Parser(new Lexer(new FileReader("input.txt")));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
        Analyser analyser = new Analyser();
        if (p!= null) {
        	Program prog = (Program) analyser.VisitProgram((Program) p.parse().value);
    		PrettyPrint printer = new PrettyPrint(prog);
    		analyser.erros.mostrar();
		}
        
    	/*Parser p = new Parser(
                new Lexer(new FileReader("input.txt")));
    	Program result = (Program) p.parse().value;            
        Analyser analyser = new Analyser();
        analyser.VisitProgram(result);
        
        analyser.erros.mostrar();*/
        
        //new PrettyPrint(result);
	}
}
