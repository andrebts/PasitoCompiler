package pasito.syntax;
import java_cup.runtime.ComplexSymbolFactory;
import java_cup.runtime.ComplexSymbolFactory.Location;
import java_cup.runtime.Symbol;
import java.lang.*;
import java.io.InputStreamReader;

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
	public Lexer(ComplexSymbolFactory sf, java.io.InputStream is){
		this(is);
        symbolFactory = sf;
    }
	public Lexer(ComplexSymbolFactory sf, java.io.Reader reader){
		this(reader);
        symbolFactory = sf;
    }
    
    private StringBuffer sb;
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
NewLine        = \n
InputCharacter = [^\r\n]
LineTerminator = [\n|\r|\r\n] // fim de linha
WhiteSpace 	   = {LineTerminator}|[\t|\f| ] // espaço em branco

GeralComment   = "/*"([^*]|\*+[^*/])*\*+"/"
LinhaComment   = "//" {InputCharacter}* {LineTerminator}? //Podem tbm estar no fim do arquivo sem LineTerminator
Comment 	   = {GeralComment}|{LinhaComment}

Letter 		   = [a-z|A-Z] // letras
Digit          = 0 | [1-9][0-9]* // números
Ignore 		   = {WhiteSpace}|{Comment} // tudo isso será ignorado

Id 		   	   = {Letter} ( {Letter} | {Digit} )*
Decimais  	   = {Digit} {Digit}*

Float_number   = {FLit1} {Exponent}? | {FLit2} {Exponent}? | {FLit3} Exponent?
FLit1    	   = [0-9]+ "." [0-9]* 
FLit2    	   = "." [0-9]+ 
FLit3    	   = [0-9]+ 
Exponent 	   = [eE] [+-]? [0-9]+

Int_number   = {Decimais}            		
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
    {Ignore}                     { }
    
    {True} 						 { return symbol("TRUE", TRUE, true); }
    {False} 					 { return symbol("FALSE", FALSE, false); }
     
	{Boolean} 				 	 { return symbol("BOOLEAN", BOOLEAN, new Boolean(yytext())); }
	{Float_number} 				 { return symbol("FLOAT_NUMBER", FLOAT_NUMBER, new Float(yytext())); }
	{Int_number} 				 { return symbol("INT_NUMBER", INT_NUMBER, new Integer(yytext())); }
    
    {BOOLEAN} 				 	 { return symbol("BOOLEAN", BOOLEAN); }
    {Float64} 					 { return symbol("FLOAT64", FLOAT64); }
	{Int32}						 { return symbol("INT32", INT32); }
  	
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
    {Dassign}					 { return symbol("DASSIGN", DASSIGN); }
	
    {Dot}			             { return symbol("DOT", DOT); }
    {Colon}		        	     { return symbol("COLON", COLON); }
    {Dotdotdot}		         	 { return symbol("DOTDOTDOT", DOTDOTDOT); }
    {Comma}		        	     { return symbol("COMMA", COMMA); }
    {Semicolon}		        	 { return symbol("SEMICOLON", SEMICOLON); }

    {Default}					 { return symbol("DEFAULT", DEFAULT); }
    {Func}				      	 { return symbol("FUNC", FUNC); }
	{Interface}				     { return symbol("INTERFACE", INTERFACE); }
	{Case}				      	 { return symbol("CASE", CASE); }
	{Struct}				     { return symbol("STRUCT", STRUCT); }
	{Type}				      	 { return symbol("TYPE", TYPE); }
	{If}				      	 { return symbol("IF", IF); }
	{Else}				      	 { return symbol("ELSE", ELSE); }
	{Switch}				     { return symbol("SWITCH", SWITCH); }
	{Const}				      	 { return symbol("CONST", CONST); }
	{Fallthrough}				 { return symbol("FALLTHROUGH", FALLTHROUGH); }
	{Var}				      	 { return symbol("VAR", VAR); }
	{For}				      	 { return symbol("FOR", FOR); }
	{Return}				     { return symbol("RETURN", RETURN); }
	{Id} 				 	 	 { return symbol("ID", ID); }
	{Range} 				 	 { return symbol("RANGE", Range); }
}

// aviso de erro
.|\n						 { emit_warning("Caracter não reconhecido '" + yytext() + "' -- ignorado"); }