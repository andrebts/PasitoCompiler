package pasito.staticSemantics;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import pasito.ast.PasitoVisitor;
import pasito.ast.Program;
import pasito.ast.declaration.ConstDecl;
import pasito.ast.declaration.TypeDecl;
import pasito.ast.declaration.VarDecl;
import pasito.ast.element.ExpressionElement;
import pasito.ast.element.Identifier;
import pasito.ast.element.KeyedElement;
import pasito.ast.element.LiteralElement;
import pasito.ast.expression.BinaryExpression;
import pasito.ast.expression.BinaryOperator;
import pasito.ast.expression.BooleanLiteral;
import pasito.ast.expression.CallExpression;
import pasito.ast.expression.CompositeLit;
import pasito.ast.expression.Expression;
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
import pasito.ast.topLevelDecl.TopLevelDecl;
import pasito.ast.type.ArrayType;
import pasito.ast.type.FieldDecl;
import pasito.ast.type.InterfaceType;
import pasito.ast.type.PointerType;
import pasito.ast.type.SliceType;
import pasito.ast.type.StructType;
import pasito.ast.type.TypeName;
import pasito.staticEnvironment.AlreadyBoundException;
import pasito.staticEnvironment.InvalidLevelException;
import pasito.staticEnvironment.SymbolTable;
import pasito.staticSemantics.binding.Binding;
import pasito.staticSemantics.binding.Const;
import pasito.staticSemantics.binding.Fun;
import pasito.staticSemantics.binding.Ty;
import pasito.staticSemantics.binding.Var;
import pasito.staticSemantics.type.ArrayTp;
import pasito.staticSemantics.type.Kind;
import pasito.staticSemantics.type.PointerTp;
import pasito.staticSemantics.type.Primitive;
import pasito.staticSemantics.type.Type;
import pasito.util.ErrorRegister;

public class Analyser implements PasitoVisitor {

	SymbolTable<Binding> env;
	ConstantExpressionEavaluator constEvaluator;
    public ErrorRegister erros = new ErrorRegister();

	public Analyser() {
		 env = new SymbolTable<>();
		 
			 try {
				env.put("int64", new Ty(Primitive.INT64));
				env.put("float64", new Ty(Primitive.FLOAT64));
				env.put("boolean", new Ty(Primitive.BOOLEAN));
				// .. Todos os primitivos
			} catch (AlreadyBoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		 constEvaluator = new ConstantExpressionEavaluator(env);
		 /* inicializar env com bindings pr�definidos para int64, float64, boolean, ....
		  * 
 		 */
	}

	@Override
	public Object VisitProgram(Program program) {
		for (TopLevelDecl tpDec :  program.declarations )
			tpDec.accept(this);
		return null;
	}

	@Override
	public Object VisitDec(Dec dec) {
		dec.decl.accept(this);
		return null;
	}

	@Override
	public Object VisitFunctionDecl(FunctionDecl functionDecl) {
		try {
			env.beginScope();
			Binding fun = (Binding) functionDecl.sig.accept(this);
			env.put(functionDecl.name, fun);
			functionDecl.body.accept(this);
			env.endScope();
		} catch (AlreadyBoundException | InvalidLevelException e) {
			e.printStackTrace();
		}
		return null;
		
	}

	@Override
	public Object VisitMethodDecl(MethodDecl methodDecl) {
		methodDecl.sig.accept(this); // visita a assinatura
		methodDecl.receiver.accept(this); // visita o receiver
		methodDecl.body.accept(this); // visita o corpo 
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object VisitSignature(Signature signature) { 
		
		List<Type> pin = new LinkedList<>();
		List<Type> pout = new LinkedList<>();
		Type variadic = null;
		
		if (signature.inPars != null) 
			for(FormalParameter fp : signature.inPars) {
				Type ty = (Type) fp.type.accept(this);
				pin.add(ty);
			}
		
		if (signature.outPars != null) 
		for(FormalParameter fp : signature.outPars) {
			Type ty = (Type) fp.type.accept(this);
			pout.add(ty);
		}
		
		if (signature.variadicPar != null) 
			variadic = (Type) signature.variadicPar.accept(this);
		
		Binding fun = new Fun(pout, pin, variadic);		
		return fun;
	}

	@Override
	public Object VisitConstDecl(ConstDecl constDecl) { 
		if (constDecl.exp == null) {
			ErrorRegister.report("error: Declaração de Constante NULA");
			return null; 
		} 
		
		Object value =  constDecl.exp.accept(constEvaluator);
		Type ty = (Type) constDecl.exp.accept(this);
		if (constDecl.type == null)
			try {
				env.put( constDecl.name, new Const(value, ty) );
			} catch (AlreadyBoundException e) {
				ErrorRegister.report("bla bla bla");
			}
		else {
		   Type declaredType = (Type) constDecl.type.accept(this);
		   try {
			env.put( constDecl.name, new Const(value, declaredType) );
		} catch (AlreadyBoundException e) {
			ErrorRegister.report("bla bla bla");
		}
		   if ( !ty.assignableTo(declaredType) )
			   ErrorRegister.report("...");
		}
		return null;
	}

	
	@Override
	public Object VisitVarDecl(VarDecl varDecl) { 
		/*Type ty = (Type) varDecl.exp.accept(this);
		try {
			env.put(varDecl.name, new Var(ty));
		} catch (AlreadyBoundException e) {
			ErrorRegister.report(varDecl.name + " Variável ja foi declarada!");
		}*/
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
		Binding b = env.get(typeName.name);
		if (b != null) {
			return ((Ty) b).type;
		}
		return null;
	}

	@Override
	public Object VisitArrayType(ArrayType arrayType) {
		Type tEle = (Type) arrayType.elemType.accept(this);
		Object value = arrayType.length.accept(this);
		int v;
		if(value instanceof IntLiteral) {			
			v = ((IntLiteral)value).value;			
			return new ArrayTp(v,tEle);
		} // Verificar casos do tipo a[i] onde i é uma variavel
		else {
			ErrorRegister.report("Tamanho do Array invalido!");
			return null; // Obs: retornar um tipo padrao OBjeto
		}		
	}

	@Override
	public Object VisitBaseType(PointerType pointerType) {
		Type ty = (Type) pointerType.baseType.accept(this);		
		return new PointerTp(ty);
	}

	@Override
	public Object VisitStructType(StructType structType) {
		// TODO Auto-generated method stub	
		Type ty = (Type) structType.accept(this);		
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
		List<Expression> leftExps = assignment.leftExps;
		List<Expression> rightExps = assignment.rightExps;
		if(leftExps.size() != rightExps.size()) {
			ErrorRegister.report("...");
		}else {
			ListIterator<Expression> lIt = leftExps.listIterator();
			for(Expression rexp : rightExps) {
				Expression lexp = lIt.next();
				Type rTy = (Type) rexp.accept(this);
				Type lTy = (Type) lexp.accept(this);
				if(!rTy.assignableTo(lTy)) // testa se a atribuição é equivalentes
					ErrorRegister.report("...");
			}
		}
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
		Type t = (Type) ifStmt.exp.accept(this);
        if (! t.equals(Kind.BOOLEAN)) {
            erros.report("Esperada uma expressão do tipo boolean, recebido argumento tipo" + t + " inválido.");
        }
        ifStmt.initStmt.accept(this);
        ifStmt.block.accept(this);
        return null;
	}

	@Override
	public Object VisitIfElseStmt(IfElseStmt ifElseStmt) {
		Type t = (Type) ifElseStmt.exp.accept(this);
        if (! t.equals(Kind.BOOLEAN)) {
            erros.report("Esperada uma expressão do tipo boolean, recebido argumento tipo" + t + " inválido.");
        }
        
        ifElseStmt.initStmt.accept(this);
        ifElseStmt.leftBlock.accept(this);
        ifElseStmt.rightBlock.accept(this);
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
