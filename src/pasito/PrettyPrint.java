package pasito;

import pasito.ast.*;
import pasito.ast.declaration.*;
import pasito.ast.element.*;
import pasito.ast.expression.*;
import pasito.ast.methodSpecOrInterfaceName.*;
import pasito.ast.signature.*;
import pasito.ast.statement.*;
import pasito.ast.topLevelDecl.*;
import pasito.ast.type.*;
import pasito.util.ErrorRegister;
import pasito.util.StringUtils;

import java.util.Collections;
import java.util.List;

/**
 * Created by ariel on 27/08/17.
 */
public class PrettyPrint implements PasitoVisitor {
    public ErrorRegister erros = new ErrorRegister();
	private int indentLevel = 0;
	private StringBuilder result = new StringBuilder();

	public PrettyPrint() {
	}

	public PrettyPrint(Program program) {
		System.out.println(this.VisitProgram(program));
	}

	private void indent() {
		++indentLevel;
	}

	private void unindent() {
		--indentLevel;
	}

	/**
	 * Método auxiliar para impressão de listas de strings
	 * 
	 * @param list
	 *            - lista de strings
	 * @param parentheses
	 *            - vai haver parenteses?
	 * @return
	 */
	private String printList(List<?> list, boolean parentheses) {
		StringBuilder sb = new StringBuilder();
		sb.append(list.get(0));
		for (Object item : list.subList(1, list.size()))
			sb.append(", " + item);
		if (parentheses) {
			sb.insert(0, '(');
			sb.append(')');
		}
		return sb.toString();
	}

	/**
	 * Método auxiliar para impressão de strings
	 * 
	 * @param str
	 *            - string
	 * @param indent
	 *            - identação?
	 * @return
	 */
	private Object print(Object str, boolean indent) {
		String tabs = indent && indentLevel > 0 ? String.join("",
				Collections.nCopies(indentLevel, "   ")) : "";
		return tabs + str;
	}

	@Override
	public Object VisitProgram(Program program) {
		StringBuilder result = new StringBuilder();
		if(program != null)
			for (TopLevelDecl declaration : program.declarations) {
				result.append(print(declaration.accept(this) + ";\n", true));
			}
		return result.toString();
	}

	@Override
	public Object VisitDec(Dec dec) {
		StringBuilder result = new StringBuilder();
		result.append(dec.decl.accept(this));
		return result.toString();
	}

	@Override
	public Object VisitFunctionDecl(FunctionDecl functionDecl) {
		StringBuilder sb = new StringBuilder();
		
		sb.append("func " + functionDecl.name + " ");
		sb.append(functionDecl.sig.accept(this));
		sb.append(functionDecl.body.accept(this));
		return sb.toString();
	}

	@Override
	public Object VisitMethodDecl(MethodDecl methodDecl) {
		StringBuilder sb = new StringBuilder();

		sb.append("func " + methodDecl.receiver.accept(this) + methodDecl.name + " ");
		sb.append(methodDecl.sig.accept(this));
		sb.append(methodDecl.body.accept(this));

		return sb.toString();
	}

	@Override
	public Object VisitSignature(Signature signature) {
		StringBuilder result = new StringBuilder();
		
		result.append('(');
		if (signature.inPars != null){
			for (FormalParameter parameter : signature.inPars) {
				result.append(parameter.accept(this));
				result.append(", ");
			}
			result.replace(result.length() -2, result.length(), "");
		}

		if (signature.variadicPar != null || signature.variadicPar != null) {
			result.append(", ");
			result.append(signature.variadicPar.accept(this));
		}
		result.append(')');

		result.append(" (");
		if (signature.outPars != null){
			for (FormalParameter parameter : signature.outPars) {
				result.append(parameter.accept(this));
				result.append(", ");
			}
			result.replace(result.length() -2, result.length(), "");
		}
		result.append(')');
		
		return result.toString();
	}

	@Override
	public Object VisitConstDecl(ConstDecl constDecl) {
		StringBuilder sb = new StringBuilder();
		sb.append("const " + constDecl.name);
		
		if (constDecl.type != null) 
			sb.append(" " + constDecl.type.accept(this));
		
		sb.append(" = ");
		sb.append(constDecl.exp.accept(this));
		return sb.toString();
	}

	@Override
	public Object VisitVarDecl(VarDecl varDecl) {
		StringBuilder sb = new StringBuilder();
		sb.append("var " + varDecl.name);
		
		if (varDecl.type != null) 
			sb.append(" " + varDecl.type.accept(this));
		
		sb.append(" = ");
		sb.append(varDecl.exp.accept(this));
		return sb.toString();
	}

	@Override
	public Object VisitTypeDecl(TypeDecl typeDecl) {
		StringBuilder sb = new StringBuilder();

		sb.append("type " + typeDecl.name + " ");
		sb.append(typeDecl.type.accept(this));

		return sb.toString();
	}

	@Override
	public Object VisitFormalParameter(FormalParameter formalParameter) {
		StringBuilder sb = new StringBuilder();

		if (formalParameter.name != null) {
			sb.append(formalParameter.name);
			sb.append(" ");
		}
		
		if (formalParameter.type != null) 
			sb.append(formalParameter.type.accept(this));

		return sb.toString();
	}

	@Override
	public Object VisitTypeName(TypeName typeName) {
		StringBuilder sb = new StringBuilder();
		sb.append(typeName.name);
		return sb.toString();
	}

	@Override
	public Object VisitArrayType(ArrayType arrayType) {
		StringBuilder sb = new StringBuilder();
		sb.append("[" + arrayType.length + "]");
		sb.append(arrayType.elemType.accept(this));
		return sb.toString();
	}

	@Override
	public Object VisitBaseType(PointerType pointerType) {
		StringBuilder sb = new StringBuilder();
		sb.append(pointerType.baseType.accept(this));
		return sb.toString();
	}

	@Override
	public Object VisitStructType(StructType structType) {
		StringBuilder result = new StringBuilder();

		result.append("struct { ");
		for (FieldDecl fieldDecl : structType.fieldDecls) {
			result.append(fieldDecl.accept(this));
		}
		result.append("}");

		return result.toString();
	}

	@Override
	public Object VisitInterfaceType(InterfaceType interfaceType) {
		StringBuilder result = new StringBuilder();
		for (MethodSpecOrInterfaceName interfaceElem : interfaceType.interfaceElems) {
			result.append(interfaceElem.accept(this));
		}
		return result.toString();
	}

	@Override
	public Object VisitSliceType(SliceType sliceType) {
		StringBuilder sb = new StringBuilder();
		sb.append("[]");
		sb.append(sliceType.elementType.accept(this));
		return sb.toString();
	}

	@Override
	public Object VisitFieldDecl(FieldDecl fieldDecl) {
		StringBuilder sb = new StringBuilder();
		sb.append(fieldDecl.name);
		sb.append(fieldDecl.type.accept(this));
		return sb.toString();
	}

	@Override
	public Object VisitMethodSpec(MethodSpec methodSpec) {
		StringBuilder sb = new StringBuilder();
		sb.append(methodSpec.name);
		sb.append(methodSpec.sig.accept(this));
		return sb.toString();
	}

	@Override
	public Object VisitInterfaceName(InterfaceName interfaceName) {
		StringBuilder sb = new StringBuilder();
		sb.append(interfaceName.name);
		return sb.toString();
	}

	@Override
	public Object VisitUnaryExpression(UnaryExpression unaryExpression) {
		StringBuilder sb = new StringBuilder();

		switch (unaryExpression.op) {
		case PLUS:
			sb.append("+");
		case MINUS:
			sb.append("-");
		case NOT:
			sb.append("!");
		default:
			sb.append(unaryExpression.exp.accept(this));
		}

		return sb;
	}

	@Override
	public Object VisitBinaryExpression(BinaryExpression binaryExpression) {
		StringBuilder sb = new StringBuilder();
		sb.append(binaryExpression.leftExp.accept(this));
		sb.append(binaryExpression.op.accept(this));
		sb.append(binaryExpression.rightExp.accept(this));
		return sb.toString();
	}
	
	@Override
	public Object VisitBoolLiteral(BooleanLiteral boolLiteral) {
		StringBuilder sb = new StringBuilder();
		sb.append(boolLiteral.value);
		return sb.toString();
	}

	@Override
	public Object VisitIntLiteral(IntLiteral intLiteral) {
		StringBuilder sb = new StringBuilder();
		sb.append(intLiteral.value);
		return sb.toString();
	}

	@Override
	public Object VisitFloatLiteral(FloatLiteral floatLiteral) {
		StringBuilder sb = new StringBuilder();
		sb.append(floatLiteral.value);
		return sb.toString();
	}

	@Override
	public Object VisitFunctionLiteral(FunctionLiteral functionLiteral) {
		StringBuilder sb = new StringBuilder();

		sb.append("func ");
		sb.append(functionLiteral.sig.accept(this));
		sb.append(functionLiteral.body.accept(this));

		return sb.toString();
	}

	@Override
	public Object VisitCompositLit(CompositeLit compositeLit) {
		StringBuilder result = new StringBuilder();
		result.append(compositeLit.type.accept(this));
		result.append("{");

		for (KeyedElement kelem : compositeLit.elems) {
			result.append(kelem.accept(this));
		}
		result.append("}");

		return result.toString();
	}

	@Override
	public Object VisitIdExpression(IdExpression idExpression) {
		StringBuilder result = new StringBuilder();
		result.append(idExpression.name);
		return result;
	}

	@Override
	public Object VisitMethodExpression(MethodExpression methodExpression) {
		StringBuilder result = new StringBuilder();
		result.append(methodExpression.type.accept(this));
		result.append(".");
		result.append(methodExpression.name);
		return result;
	}

	@Override
	public Object VisitSelectorExpression(SelectorExpression selectorExpression) {
		StringBuilder result = new StringBuilder();
		result.append(selectorExpression.exp.accept(this));
		result.append(".");
		result.append(selectorExpression.name);
		return result;
	}

	@Override
	public Object VisitIndexExpression(IndexExpression indexExpression) {
		StringBuilder result = new StringBuilder();
		result.append(indexExpression.exp.accept(this));
		result.append("[" + indexExpression.indexExp.accept(this) + "]");
		return result;
	}

	@Override
	public Object VisitSliceExpression(SliceExpression sliceExpression) {
		StringBuilder result = new StringBuilder();
		result.append(sliceExpression.exp.accept(this));
		result.append("[" + sliceExpression.low.accept(this) + "]");
		result.append(":");
		result.append("[" + sliceExpression.high.accept(this) + "]");
		return result;
	}

	@Override
	public Object VisitFullSliceExpression(
			FullSliceExpression fullSliceExpression) {
		StringBuilder result = new StringBuilder();
		result.append(fullSliceExpression.exp.accept(this));
		result.append("[" + fullSliceExpression.low.accept(this) + "]");
		result.append(":");
		result.append("[" + fullSliceExpression.high.accept(this) + "]");
		result.append(":");
		result.append("[" + fullSliceExpression.max.accept(this) + "]");
		return result;
	}

	@Override
	public Object VisitCallExpression(CallExpression callExpression) {
		StringBuilder result = new StringBuilder();
		result.append(callExpression.exp.accept(this));
		result.append("(");

		for (Expression arg : callExpression.args) {
			result.append(arg.accept(this) + ", ");
		}

		result.replace(result.length() - 1, result.length(), "");
		result.append(")");

		result.append(callExpression.variadicArg.accept(this));
		return result;
	}

	@Override
	public Object VisitKeyedElement(KeyedElement keyedElement) {
		StringBuilder result = new StringBuilder();
		result.append(keyedElement.exp.accept(this) + ":");
		result.append(keyedElement.elem.accept(this));
		return result;
	}

	@Override
	public Object VisitExpressionElement(ExpressionElement expressionElement) {
		StringBuilder result = new StringBuilder();
		result.append(expressionElement.exp.accept(this));
		return result;
	}

	@Override
	public Object VisitLiteralElement(LiteralElement literalElement) {
		StringBuilder result = new StringBuilder();
		result.append("{");

		for (KeyedElement arg : literalElement.keyedElems) {
			result.append(arg.accept(this) + ", ");
		}

		result.replace(result.length() - 1, result.length(), "");
		result.append("}");

		return result;
	}

	@Override
	public Object VisitDeclarationStm(DeclarationStm declarationStm) {
		StringBuilder result = new StringBuilder();
		result.append(declarationStm.decl.accept(this));
		return result;
	}

	@Override
	public Object VisitEmptyStmt(EmptyStmt emptyStmt) {
		return new StringBuilder();
	}

	@Override
	public Object VisitReturnStmt(ReturnStmt returnStmt) {
		StringBuilder result = new StringBuilder();
		result.append("return ");

		if (returnStmt.exps != null) 
			for (Expression exp : returnStmt.exps) {
				result.append(exp.accept(this) + ", ");
			}

		return result;
	}

	@Override
	public Object VisitExpressionStmt(ExpressionStmt expressionStmt) {
		StringBuilder result = new StringBuilder();
		result.append(expressionStmt.exp.accept(this));
		return result;
	}

	@Override
	public Object VisitAssignment(Assignment assignment) {
		StringBuilder sb = new StringBuilder();
		int sizeLeft = assignment.leftExps.size() - 1;
		int sizeRight = assignment.rightExps.size() - 1;

		// Pega todas as expressões da esquerda
		for (Expression exp : assignment.leftExps.subList(0, sizeLeft))
			sb.append((String) exp.accept(this) + ',');
		sb.append(assignment.leftExps.get(sizeLeft));

		// adiciona sinal =
		sb.append(" = ");

		// Pega todas as expressões da direita
		for (Expression exp : assignment.rightExps.subList(0, sizeRight))
			sb.append((String) exp.accept(this) + ',');
		sb.append(assignment.rightExps.get(sizeRight));

		return sb.toString();
	}

	@Override
	public Object VisitShortVarDecl(ShortVarDecl shortVarDecl) {
		return null;
	}

	@Override
	public Object VisitBlock(Block block) {
		StringBuilder result = new StringBuilder();

		result.append("{\n");
		indent();
		
		for (Statement stm : block.stmts) {
			if (stm instanceof EmptyStmt)
				continue;
			result.append(print(stm.accept(this) + ";", true)); 
			result.append("\n");
		}
		
		unindent();
		result.append("}");

		return result.toString();
	}

	@Override
	public Object VisitIfStmt(IfStmt ifStmt) {
		StringBuilder result = new StringBuilder();

		result.append("if ");
		if (ifStmt.initStmt != null)
			result.append(ifStmt.initStmt.accept(this) + " ");
		result.append(ifStmt.exp.accept(this));
		result.append(ifStmt.block.accept(this));

		return result.toString();
	}

	@Override
	public Object VisitIfElseStmt(IfElseStmt ifElseStmt) {
		StringBuilder result = new StringBuilder();

		result.append("if ");
		if (ifElseStmt.initStmt != null)
			result.append(ifElseStmt.initStmt.accept(this) + " ");
		result.append(ifElseStmt.exp.accept(this));
		result.append(ifElseStmt.leftBlock.accept(this));
		result.append("else ");
		result.append(ifElseStmt.rightBlock.accept(this));

		return result.toString();
	}

	@Override
	public Object VisitForStmt(ForStmt forStmt) {
		StringBuilder result = new StringBuilder();

		result.append("for ");
		
		if (forStmt.initStmt != null){
			result.append(forStmt.initStmt.accept(this) + ";");
			if (forStmt.exp != null) {
				result.append(forStmt.exp.accept(this) + ";");
				if (forStmt.postStmt != null)
					result.append(forStmt.postStmt.accept(this));
			}
		}

		result.append(forStmt.body.accept(this));

		return result.toString();
	}

	@Override
	public Object VisitForRange(ForRange forRange) {
		StringBuilder result = new StringBuilder();

		result.append("for ");
		
		if (forRange.initStmt != null)
			result.append(forRange.initStmt.accept(this));
		result.append("range" + forRange.rangExp.accept(this));
		result.append(forRange.body.accept(this));

		return result.toString();
	}

	@Override
	public Object VisitBinaryOperator(BinaryOperator binaryOperator) {
		switch (binaryOperator.name()) {
		case "AND":
			return print(") AND (", false);
		case "OR":
			return print(") OR (", false);
		case "PLUS":
			return print(" + ", false);
		case "MINUS":
			return print(" - ", false);
		case "MULT":
			return print(" * ", false);
		case "DIV":
			return print(" / ", false);
		case "LT":
			return print(" < ", false);
		case "ASSIGN":
			return print(" = ", false);
		default:
			return null;
		}
	}

	@Override
	public Object VisitUnaryOperator(UnaryOperator unaryOperator) {
		switch (unaryOperator) {
		case PLUS:
			return print("+", false);
		case MINUS:
			return print("-", false);
		case NOT:
			return print("!", false);
		default:
			return null;
		}
	}
}
