package pasito.syntax;
import java_cup.runtime.ComplexSymbolFactory;
import java_cup.runtime.ComplexSymbolFactory.Location;
import java_cup.runtime.Symbol;
import java.lang.*;
import java.io.InputStreamReader;
import java.util.regex.Pattern;

%%

%class Lexer
%implements sym
%public
%unicode
%line
%column
%cup
%char

%{
	private StringBuffer sb;
    private ComplexSymbolFactory symbolFactory;
    
	public Lexer(ComplexSymbolFactory sf, java.io.InputStream is){
		this(is);
        symbolFactory = sf;
    }
	public Lexer(ComplexSymbolFactory sf, java.io.Reader reader){
		this(reader);
        symbolFactory = sf;
    }
    
    /*private StringBuffer sb;
    private ComplexSymbolFactory symbolFactory;
    private int csline,cscolumn;

    public Symbol symbol(String name, int code){
        System.out.println("<" + name + ", " + yytext() + "> (Linha = " + yyline + "; Coluna = " + yycolumn + ")");
    
		return symbolFactory.newSymbol(name, code,
						new Location(yyline+1,yycolumn+1, yychar), // -yylength()
						new Location(yyline+1,yycolumn+yylength(), yychar+yylength())
				);
    }
    
    private Symbol symbol(String name, int sym, Object val) {
            System.out.println("<" + name + ", " + yytext() + "> (" + yyline + " - " + yycolumn + ")");
            
            Location left = new Location(yyline+1,yycolumn+1,yychar);
            Location right= new Location(yyline+1,yycolumn+yylength(), yychar+yylength());
        
            return symbolFactory.newSymbol(name, sym, left, right,val);
    }*/
    
    public Symbol ultimo;
	public String tok;
	
    /*
    * Retorna de sÃ­mbolos identificados pelo analisador lexico
    * O simbolo retornado Ã© um objeto da classe ComplexSymbolFactory implementada pelo CUP
    * Tambem Ã© retornada a localizaÃ§Ã£o do sÃ­mbolo no arquivo, Ãºtil para fins de feedback ao usuÃ¡rio sobre a  posiÃ§Ã£o do erro (caso haja)
    * Voces provavelmente vao precisar sobrecarregar essa funcao para cobrir todos os tipos de simbolo q vcs precisam retornar
    */
    public Symbol symbol(String nome, int code) {
    	System.out.println("<" + nome + ", " + yytext() + "> (" + yyline + " - " + yycolumn + ")");
    	
        ultimo = symbolFactory.newSymbol(nome, code,
                            new Location(yyline+1, yycolumn+1, yychar),
                            new Location(yyline+1, yycolumn+yylength(), yychar+yylength()));
                                  
       tok = yytext();
       return ultimo;
    }
	
	public Symbol symbol(String nome, int code, Object val) {
    	System.out.println("<" + nome + ", " + yytext() + "> (" + yyline + " - " + yycolumn + ")");
    	
		ultimo = symbolFactory.newSymbol(nome, code,
                            new Location(yyline+1, yycolumn+1, yychar),
                            new Location(yyline+1, yycolumn+yylength(), yychar+yylength()),
                            val);                            
        
	    tok = yytext();
	    return ultimo;
    }
    
    public Symbol symbol(String nome, int code, String val) {
    	System.out.println("<" + nome + ", " + val + "> (" + yyline + " - " + yycolumn + ")");
    	
        ultimo = symbolFactory.newSymbol(nome, code,
                            new Location(yyline+1, yycolumn+1, yychar),
                            new Location(yyline+1, yycolumn+yylength(), yychar+yylength()),
                            val);
                                    
		tok = yytext();
		return ultimo;
	}
    
    protected void emit_warning(String message){
    	System.out.println("scanner warning: " + message + " at : 2 "+ 
    			(yyline+1) + " " + (yycolumn+1) + " " + yychar);
    }
    
    protected void emit_error(String message){
    	System.out.println("scanner error: " + message + " at : 2" + 
    			(yyline+1) + " " + (yycolumn+1) + " " + yychar);
    }
%}

%eofval{
    return symbolFactory.newSymbol("EOF",sym.EOF);
%eofval}

/* Básico */
InputCharacter = [^\r\n]
LineTerminator = [\n|\r|\r\n] // fim de linha
WhiteSpace 	   = {LineTerminator}|[\t|\f| ] // espaço em branco
Number = [0-9]+ //era = Digit

GeralComment   = "/*"([^*]|\*+[^*/])*\*+"/"
LinhaComment   = "//" {InputCharacter}* {LineTerminator}? //Podem tbm estar no fim do arquivo sem LineTerminator
Comment 	   = {GeralComment}|{LinhaComment}

Letter 		   = [a-z|A-Z] // letras
Digit          = [0-9]+ // números
Ignore 		   = {WhiteSpace}|{Comment} // tudo isso será ignorado

Id 		   	   = ([:jletter:] | "_" ) ([:jletterdigit:] | [:jletter:] | "_" )*
Decimais  	   = 0|[1-9]|[1-9]+{Digit}+

OctalDigit 	   = [0-7]
Octal 		   = 0+ [1-3]? {OctalDigit} {1,15}

HexDigit 	   = [0-9a-fA-F]
Hexa 		   = 0 [xX] 0* {HexDigit} {1,8}

Float_number   = {FLit1} {Exponent}? | {FLit2} {Exponent}? | {Digit} Exponent?
FLit1    	   = [0-9]+ "." [0-9]* 
FLit2    	   = "." [0-9]+ 
Exponent 	   = [eE] [+-]? [0-9]+

Int_number     = {Decimais}            		
Boolean		   = True | False

/***** Operadores aritméticos *****/
Plus  		   = "+" // operador de soma
Minus 		   = "-" // operador de subtração
Mult 		   = "*"
Div 		   = "/"

/* Operadores lógicos */
And 		   = "&&" // operador lógico And
Not	   		   = "!"
Eq 		   	   = "​=="
Lpar  		   = "("
Rpar 		   = ")"
Lt 	   		   = "<"
Lsbrack  	   = "["
Rsbrack        = "]"
Lbrack 	   	   = "{"
Rbrack 	   	   = "}"
Assign		   = "="
Dassign		   = ":="
         
/* Símbolos */
Dot 		   = "." // ponto
Colon 		   = ":" // ponto
Dotdotdot	   = "..." // ponto
Comma 		   = "," // vírgula
Semicolon 	   = ";" //Ponto e virgula

/* Tipos primitivos */
BOOLEAN		   = "bool" // boleano
Int32  		   = "int"
Float64	 	   = "float"

/* Palavras reservadas */
Var 		   = "var" // palavra reservada var
Default 	   = "default" // palavra reservada default
Func 		   = "func" // palavra reservada func
Interface 	   = "interface" // palavra reservada interface
Case 		   = "case" // palavra reservada case
Struct 		   = "struct" // palavra reservada struct
Type 		   = "type" // palavra reservada type
If 		   	   = "if" // palavra reservada if
Else 		   = "else" // palavra reservada else
Switch 		   = "switch" // palavra reservada switch
Const 		   = "const" // palavra reservada const
Fallthrough    = "fallthrough" // palavra reservada fallthrough
For 		   = "for" // palavra reservada for
Return 		   = "return" // palavra reservada return
Range 		   = "range" // palavra reservada range
True 		   = "true" // palavra reservada True
False 		   = "false" // palavra reservada False


// fim das expressões regulares

%state CODESEG
%%

/****** OPERADORES E DELIMITADORES ******/
<YYINITIAL> {
	{LineTerminator}			 { 	String num = "[-]?\\d*[.]?\\d+";
    								String id = "\\b[_a-zA-Z][_a-zA-Z0-9]*\\b";
    								if (Pattern.matches(num, tok )){ return symbol("SEMICOLON",SEMICOLON);}
    								if (Pattern.matches(id, tok )){ return symbol("SEMICOLON",SEMICOLON);}
    								if (tok.equals("return")){ return symbol("SEMICOLON",SEMICOLON);}
    								if (tok.equals("}")){ return symbol("SEMICOLON",SEMICOLON);}
    								if (tok.equals("]")){ return symbol("SEMICOLON",SEMICOLON);}
    								if (tok.equals(")")){ return symbol("SEMICOLON",SEMICOLON);}
    								if (tok.equals("fallthrough")){ return symbol("SEMICOLON",SEMICOLON);}							
    							 } 
    							
    {Ignore}                     { }
    
    {True} 						 { return symbol("TRUE", TRUE, true); }
    {False} 					 { return symbol("FALSE", FALSE, false); }
     
    {Octal}						 { return symbol ("OCTAL", DIGIT, (yytext())); }
	{Hexa}						 { return symbol ("HEXA", DIGIT, (yytext())); }
	{Boolean} 				 	 { return symbol("BOOLEAN", BOOLEAN, new Boolean(yytext())); }
	{Float_number} 				 { return symbol("FLOAT_NUMBER", FLOAT_NUMBER, new Float(yytext())); }
	{Int_number} 				 { return symbol("INT_NUMBER", INT_NUMBER, new Integer(yytext())); }
	
    {BOOLEAN} 				 	 { return symbol("BOOLEAN", BOOLEAN); }
    {Float64} 					 { return symbol("FLOAT64", FLOAT64); }
	
	{Int32}						 { return symbol("INT32", INT32, yytext()); }
  	
    {Plus}                       { return symbol("PLUS", PLUS); }
    {Minus}			             { return symbol("MINUS", MINUS); }
	{Mult}                       { return symbol("MULT", MULT); }
    {Div}			             { return symbol("DIV", DIV); }
	
    {And}						 { return symbol("AND", AND); }
	{Not}					 	 { return symbol("NOT", NOT); }
    {Eq}						 { return symbol("EQ", EQ); }
    {Lpar}				 		 { return symbol("LPAR", LPAR); }
    {Rpar}			 			 { return symbol("RPAR", RPAR); }
    {Lt}					 	 { return symbol("LT", LT); }
    {Lsbrack}				 	 { return symbol("LSBRACK", LSBRACK); }
    {Rsbrack}				 	 { return symbol("RSBRACK", RSBRACK); }
    {Lbrack}					 { return symbol("LBRACK", LBRACK); }
    {Rbrack}				 	 { return symbol("RBRACK", RBRACK); }
    {Assign}					 { return symbol("ASSIGN", ASSIGN); }
    {Dassign}					 { return symbol("DASSIGN", DASSIGN, yytext()); }
	
    {Dot}			             { return symbol("DOT", DOT); }
    {Colon}		        	     { return symbol("COLON", COLON); }
    {Dotdotdot}		         	 { return symbol("DOTDOTDOT", DOTDOTDOT); }
    {Comma}		        	     { return symbol("COMMA", COMMA); }
    {Semicolon}		        	 { return symbol("SEMICOLON", SEMICOLON, yytext().intern()); }
	{Range} 				 	 { return symbol("RANGE", RANGE); }

    {Default}					 { return symbol("DEFAULT", DEFAULT); }
    {Func}				      	 { return symbol("FUNC", FUNC, yytext()); }
	{Interface}				     { return symbol("INTERFACE", INTERFACE); }
	{Case}				      	 { return symbol("CASE", CASE); }
	{Struct}				     { return symbol("STRUCT", STRUCT); }
	{Type}				      	 { return symbol("TYPE", TYPE); }
	{If}				      	 { return symbol("IF", IF); }
	{Else}				      	 { return symbol("ELSE", ELSE); }
	{Switch}				     { return symbol("SWITCH", SWITCH); }
	{Const}				      	 { return symbol("CONST", CONST); }
	{Fallthrough}				 { return symbol("FALLTHROUGH", FALLTHROUGH); }
	{Var}				      	 { return symbol("VAR", VAR, yytext().intern()); }
	{For}				      	 { return symbol("FOR", FOR); }
	{Return}				     { return symbol("RETURN", RETURN, yytext()); }
	{Id} 				 	 	 { return symbol("ID", ID, yytext()); }
} 
 
// aviso de erro
.|\n						 { emit_warning("Caracter não reconhecido '" + yytext() + "' -- ignorado"); }