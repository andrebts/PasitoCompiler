package pasito.staticSemantics;

import pasito.ast.PasitoVisitor;
import pasito.ast.Program;
import pasito.ast.declaration.ConstDecl;
import pasito.ast.declaration.TypeDecl;
import pasito.ast.declaration.VarDecl;
import pasito.ast.element.ExpressionElement;
import pasito.ast.element.KeyedElement;
import pasito.ast.element.LiteralElement;
import pasito.ast.expression.BinaryExpression;
import pasito.ast.expression.BinaryOperator;
import pasito.ast.expression.BooleanLiteral;
import pasito.ast.expression.CallExpression;
import pasito.ast.expression.CompositeLit;
import pasito.ast.expression.FloatLiteral;
import pasito.ast.expression.FullSliceExpression;
import pasito.ast.expression.FunctionLiteral;
import pasito.ast.expression.IdExpression;
import pasito.ast.expression.IndexExpression;
import pasito.ast.expression.IntLiteral;
import pasito.ast.expression.MethodExpression;
import pasito.ast.expression.SelectorExpression;
import pasito.ast.expression.SliceExpression;
import pasito.ast.expression.UnaryExpression;
import pasito.ast.expression.UnaryOperator;
import pasito.ast.methodSpecOrInterfaceName.InterfaceName;
import pasito.ast.methodSpecOrInterfaceName.MethodSpec;
import pasito.ast.signature.FormalParameter;
import pasito.ast.signature.Signature;
import pasito.ast.statement.Assignment;
import pasito.ast.statement.Block;
import pasito.ast.statement.DeclarationStm;
import pasito.ast.statement.EmptyStmt;
import pasito.ast.statement.ExpressionStmt;
import pasito.ast.statement.ForRange;
import pasito.ast.statement.ForStmt;
import pasito.ast.statement.IfElseStmt;
import pasito.ast.statement.IfStmt;
import pasito.ast.statement.ReturnStmt;
import pasito.ast.statement.ShortVarDecl;
import pasito.ast.topLevelDecl.Dec;
import pasito.ast.topLevelDecl.FunctionDecl;
import pasito.ast.topLevelDecl.MethodDecl;
import pasito.ast.type.ArrayType;
import pasito.ast.type.FieldDecl;
import pasito.ast.type.InterfaceType;
import pasito.ast.type.PointerType;
import pasito.ast.type.SliceType;
import pasito.ast.type.StructType;
import pasito.ast.type.TypeName;
import pasito.staticEnvironment.SymbolTable;
import pasito.staticSemantics.binding.Binding;
import pasito.util.ErrorRegister;

/* The object returned by the evaluation is an Integer, Float or Boolean,
 * depending on the type of the constant
*/
public class ConstantExpressionEavaluator implements PasitoVisitor {
 
	SymbolTable<Binding> env;
	
	public ConstantExpressionEavaluator(SymbolTable<Binding> env) {
		this.env = env;
	}

	@Override
	public Object VisitProgram(Program program) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object VisitDec(Dec dec) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object VisitFunctionDecl(FunctionDecl functionDecl) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object VisitMethodDecl(MethodDecl methodDecl) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object VisitSignature(Signature signature) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object VisitConstDecl(ConstDecl constDecl) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object VisitVarDecl(VarDecl varDecl) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object VisitTypeDecl(TypeDecl typeDecl) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object VisitFormalParameter(FormalParameter formalParameter) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object VisitTypeName(TypeName typeName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object VisitArrayType(ArrayType arrayType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object VisitBaseType(PointerType pointerType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object VisitStructType(StructType structType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object VisitInterfaceType(InterfaceType interfaceType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object VisitSliceType(SliceType sliceType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object VisitFieldDecl(FieldDecl fieldDecl) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object VisitMethodSpec(MethodSpec methodSpec) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object VisitInterfaceName(InterfaceName interfaceName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object VisitUnaryExpression(UnaryExpression unaryExpression) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object VisitBinaryExpression(BinaryExpression binaryExpression) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object VisitIntLiteral(IntLiteral intLiteral) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object VisitFloatLiteral(FloatLiteral floatLiteral) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object VisitFunctionLiteral(FunctionLiteral functionLiteral) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object VisitCompositLit(CompositeLit compositeLit) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object VisitIdExpression(IdExpression idExpression) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object VisitMethodExpression(MethodExpression methodExpression) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object VisitSelectorExpression(SelectorExpression selectorExpression) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object VisitIndexExpression(IndexExpression indexExpression) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object VisitSliceExpression(SliceExpression sliceExpression) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object VisitFullSliceExpression(FullSliceExpression fullSliceExpression) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object VisitCallExpression(CallExpression callExpression) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object VisitKeyedElement(KeyedElement keyedElement) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object VisitExpressionElement(ExpressionElement expressionElement) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object VisitLiteralElement(LiteralElement literalElement) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object VisitDeclarationStm(DeclarationStm declarationStm) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object VisitEmptyStmt(EmptyStmt emptyStmt) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object VisitReturnStmt(ReturnStmt returnStmt) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object VisitExpressionStmt(ExpressionStmt expressionStmt) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object VisitAssignment(Assignment assignment) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object VisitShortVarDecl(ShortVarDecl shortVarDecl) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object VisitBlock(Block block) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object VisitIfStmt(IfStmt ifStmt) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object VisitIfElseStmt(IfElseStmt ifElseStmt) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object VisitForStmt(ForStmt forStmt) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object VisitForRange(ForRange forRange) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object VisitBinaryOperator(BinaryOperator binaryOperator) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object VisitUnaryOperator(UnaryOperator unaryOperator) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object VisitBoolLiteral(BooleanLiteral boolLiteral) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object IncrStmt(pasito.ast.statement.IncrStmt incrStmt) {
		// TODO Auto-generated method stub
		return null;
	}

}
