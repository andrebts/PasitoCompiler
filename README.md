# PasitoCompiler

### Pasito Language

#### Lexical and Syntactic specification

The Pasito language is a subset of Go, created by Google. The goal of this project is to create an implementation of some steps of a compiler for the Pasito language. More detais can be found at https://golang.org/ref/spec

#### 1. Types
The types in Pasito are integer (int32), float (float64), Boolean (bool), arrays, pointers and structs.

#### 2. Lexical

•	Line and block comments

•	Identifiers

•	Reserved words: 
default		func		interface	
	case		struct		type
	else		switch		if
	const		fallthrough	var
	for		return		range
  
•	Predefined types names: 
boolean, int32, float64

•	Operators and delimitators: +  &&  ==  (  )  -  <  [  ]  *  {  }	/  =  :=  ,  ;  !  ...  :  . 
  
•	Integer literals

•	Float point literals.

Semicolon treatment:
Similarly to the C language, semicolon “;” is a symbol that indicates the end of commands. However, this symbol can be omitted most of the time according to the rules at  https://golang.org/ref/spec#Semicolons.
For more details of the lexical access https://golang.org/ref/spec#Lexical_elements

#### 3. Syntactic
The grammar below is described using E-BNF as in https://golang.org/ref/spec#Notation.

```
Program       =  { TopLevelDecl ";" } .
TopLevelDecl  = Declaration | FunctionDecl | MethodDecl .

Declaration   = ConstDecl | VarDecl | TypeDecl .

ConstDecl      = "const"  ConstSpec .
ConstSpec      = IdentifierList [ [ Type ] "=" ExpressionList ] .

TypeDecl     = "type" ( TypeSpec | "(" { TypeSpec ";" } ")" ) .
TypeSpec     = identifier Type .

VarDecl     = "var" ( VarSpec | "(" { VarSpec ";" } ")" ) .
VarSpec     = IdentifierList ( Type [ "=" ExpressionList ] | "=" ExpressionList ) .

Type  	= TypeName | TypeLit .
TypeName  = identifier .
TypeLit   = ArrayType | StructType | PointerType | InterfaceType |
 	SliceType .


ArrayType   = "[" ArrayLength "]" ElementType .
ArrayLength = Expression .
ElementType = Type .

SliceType = "[" "]" ElementType .

StructType 	= "struct" "{" { FieldDecl ";" } "}" .
FieldDecl  	= (IdentifierList Type | EmbeddedField) .
EmbeddedField = [ "*" ] TypeName .

PointerType = "*" BaseType .
BaseType	= Type .

InterfaceType  	= "interface" "{" { MethodSpec ";" } "}" .
MethodSpec     	= MethodName Signature | InterfaceTypeName .
MethodName     	= identifier .
InterfaceTypeName  = TypeName .

Signature  	= Parameters [ Result ] .
Result     	= Parameters | Type .
Parameters 	= "(" [ ( ParameterList | TypeParameterList)  [ "," ]  ] ")" 
ParameterList  = ParameterDecl { "," ParameterDecl }  [ "," Identifier  "..."  Type ]
                           | Identifier  "..."  Type .
ParameterDecl  =  IdentifierList  Type .
TypeParameterList = Type { "," Type}  [ ","  "..."  Type ]
    |    "..."  Type .

IdentifierList = identifier { "," identifier } 

ExpressionList = Expression { "," Expression } .

FunctionDecl = "func" FunctionName  Function .
FunctionName = identifier .
Function     = Signature FunctionBody .
FunctionBody = Block .

Block = "{" StatementList "}" .
StatementList = { Statement ";" } .

MethodDecl   = "func" Receiver MethodName Function  .
Receiver     = "(" Identifier ReceiverType ")" .
MethodName  = identifier .

Expression = UnaryExpr | Expression binary_op Expression .

binary_op  = "&&" | add_op | mul_op | rel_op .
add_op     = "+" | "-"  .
mul_op     = "*" | "/"  .
rel_op	     = “<” | “==” .
unary_op   = "+" | "-" | "!" | "*"  .
```

#### P.S.: Preferences and associations of operators follows the specified at https://golang.org/ref/spec#Operators

```
UnaryExpr  = PrimaryExpr | unary_op UnaryExpr .

PrimaryExpr =
	Operand |
	// Conversion |
	PrimaryExpr Selector |
	PrimaryExpr Index |
	PrimaryExpr Slice |
	PrimaryExpr Arguments .

Operand 	= Literal | identifier | MethodExpr | "(" Expression ")" .
Literal 	= BasicLit | CompositeLit |  FunctionLit .

//Conversion = Type "(" Expression [ "," ] ")" .

Selector   	= "." identifier .
Index      	= "[" Expression "]" .
Slice      	= "[" [ Expression ] ":" [ Expression ] "]" |
             	"[" [ Expression ] ":" Expression ":" Expression "]" .
Arguments  	= "(" [ ( ExpressionList | Type [ "," ExpressionList ] ) [ "..." ] [ "," ] ] ")" .

BasicLit	= int_lit | float_lit .

FunctionLit = "func" Function .

CompositeLit  = LiteralType LiteralValue .
LiteralType   = StructType | ArrayType | "[" "..." "]" ElementType |
           	SliceType | TypeName .
LiteralValue  = "{" [ ElementList [ "," ] ] "}" .
ElementList   = KeyedElement { "," KeyedElement } .
KeyedElement  = [ Key ":" ] Element .
Key       	= FieldName | Expression | LiteralValue .
FieldName 	= identifier .
Element   	= Expression | LiteralValue .

MethodExpr	= ReceiverType "." MethodName .
ReceiverType  = TypeName | "(" "*" TypeName ")" | "(" ReceiverType ")" .

Statement =
	Declaration | SimpleStmt | ReturnStmt | Block | IfStmt |  ForStmt .

SimpleStmt = EmptyStmt | ExpressionStmt | Assignment | ShortVarDecl .
EmptyStmt = .
ExpressionStmt = Expression .
Assignment = ExpressionList "=" ExpressionList .
ShortVarDecl = IdentifierList ":=" ExpressionList .

ReturnStmt = "return" [ ExpressionList ] .
```

### Abstract syntax of Pasito

```
Program(List<TopLevelDecl> declarations)

TopLevelDecl
	Dec (Declaration decl)
	FunctionDecl(Identifier name,Signature sig, Block body) 
	MethodDecl(FormalParameter receiver, Identifier name, Signature sig , Block body)

Signature(List<FormalParameter> inPars, FormalParameter variadicPar,
     List<FormalParameter> outPars)
 //variadicPar/*can be null*/

Declaration
ConstDecl(Identifier name, Type type, Expression exp)
	VarDecl(Identifier name, Type type, Expression exp) 
	TypeDecl(Identifier name, Type type)

FormalParameter(Identifier name, Type type) 

Type
	TypeName(Identifier name)
	ArrayType (Expression length, Type elemType)
	PointerType(Type baseType)
StructType(List<FieldDecl> fieldDecls)
	InterfaceType(List<MethodSpecOrInterfaceName> interfaceElems)
	SliceType(Type  elementType)

FieldDecl(Identifier name, Type type) 

MethodSpecOrInterfaceName
	MethodSpec(Identifier name, Signature sig)
	InterfaceName(Identifier name)

Expression
	UnaryExpression(UnaryOperator op, Expression exp)
	BinaryExpression(BinaryOperator op, Expression lefExp, Expression rightExp)
IntLiteral(int value)
FloatLiteral(float value)
BoolLiteral(bool value) ***************** (adicionar na sintaxe abstrata)
FunctionLiteral(Signature sig, Block body)
CompositeLit(Type type, List<KeyedElement> elems)
IdExpression(Identifier name)
MethodExpression(Type type, Identifier name)
SelectorExpression(Expression exp, Identifier name) // dot expression
IndexExpression(Expression exp, Expression indexExp)
SliceExpression(Expression exp, Expression low, Expression high)
FullSliceExpression(Expression exp, Expression low, Expression high, Expression max) 
	CallExpression(Expression exp, List<Expression> args, Expression variadicArg)

KeyedElement(Expression exp, Element elem)

Element
	ExpressionElement(Expression exp) 
	LiteralElement(List<KeyedElement> keyedElems)

Statement
	DeclarationStm(Declaration decl)
	EmptyStmt()
	ReturnStmt(List<Expression> exps)
	ExpressionStmt(Expression exp)
	Assignment(List<Expression> leftExps, List<Expression> rightExps)
	ShortVarDecl(List<Identifier> names, List<Expression> exps)
	Block(List<Statement> stmts)
	IfStmt(Statement initStmt, Expression exp, Block block)
IfElseStmt(Statement initStmt, Expression exp, Block leftBlock, Block rightBlock)
ForStmt(Statement initStmt, Expression exp, Statement postStmt, Block body) 
ForRange(Statement initStmt, Expression rangExp, Block body)

BinaryOperator
  //can be defined as enum with the values:
  AND, OR, TIMES, PLUS, LESS, EQ


UnaryOperator
  //can be defined as enum with the values:
  PLUS, MINUS, NOT, POINTED_BY
```
