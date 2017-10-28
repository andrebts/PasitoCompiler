package pasito.staticSemantics;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import pasito.ast.PasitoVisitor;
import pasito.ast.Program;
import pasito.ast.declaration.ConstDecl;
import pasito.ast.declaration.Declaration;
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
import pasito.ast.statement.Statement;
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
import pasito.staticSemantics.type.Untyped;
import pasito.util.ErrorRegister;

public class Analyser implements PasitoVisitor {

	SymbolTable<Binding> env;
	ConstantExpressionEavaluator constEvaluator;
    public ErrorRegister erros = new ErrorRegister();

	public Analyser() {
		 env = new SymbolTable<>();
		 
			 try {
				env.put("int", new Ty(Primitive.INT32));
				env.put("float", new Ty(Primitive.FLOAT64));
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
			erros.report("error: Declaração de Constante NULA");
			return null; 
		} 
		
		Object value =  constDecl.exp.accept(constEvaluator);
		Type ty = (Type) constDecl.exp.accept(this);
		if (constDecl.type == null)
			try {
				env.put( constDecl.name, new Const(value, ty) );
			} catch (AlreadyBoundException e) {
				erros.report("bla bla bla");
			}
		else {
		   Type declaredType = (Type) constDecl.type.accept(this);
		   try {
			env.put( constDecl.name, new Const(value, declaredType) );
		} catch (AlreadyBoundException e) {
			erros.report("bla bla bla");
		}
		   if ( !ty.assignableTo(declaredType) )
			   erros.report("...");
		}
		return null;
	}

	@Override
	public Object VisitVarDecl(VarDecl varDecl) { 
		Iterator<String> idIt = varDecl.names.iterator();
		Iterator<Expression> expIt = varDecl.exps.iterator();
		
		while (idIt.hasNext() && expIt.hasNext()){
			try {
				if (varDecl.type != null) 
					env.put(idIt.next(), new Var((Type) varDecl.type.accept(this)));
				else
					env.put(idIt.next(), new Var((Type) expIt.next().accept(this)));
			} catch (Exception e) {
				 erros.report("A variável " + idIt.next() + " já foi declarada neste escopo");
			}
		}
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
			erros.report("Tamanho do Array invalido!");
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
		Object value = unaryExpression.exp.accept(this);
		if (value != null)
			switch (unaryExpression.op) {
			case PLUS: 
				if (value instanceof Integer || value instanceof Float)
					return value;
				else {
					erros.report("Not numeric operand");
					return null;
				}
			case MINUS:
				if (value instanceof Integer)
					return - (Integer) value;
				else if (value instanceof Float)
					return - (Float) value;
				else {
					erros.report("Not numeric operand");
					return null;
				}
			case NOT:
				if (value instanceof Boolean)
					return ! ((Boolean) value).booleanValue();
				else {
					erros.report("Not boolean operand");
					return null;
				}
			case MULT: 
			default:
				erros.report("Cannot point to a constant value");
				return null;
			}
		else
			return null;
	}

	@Override
	public Object VisitBinaryExpression(BinaryExpression binaryExpression) {
		Type tipoRetorno = null, tEsq, tDir;
        Object esq = binaryExpression.leftExp.accept(this);
        Object dir = binaryExpression.rightExp.accept(this);
        
        tEsq = (esq instanceof Var | esq instanceof Const)
                ? ((Var) esq).type
                : (Type) esq;
        
        tDir = (dir instanceof Var | dir instanceof Const)
                ? ((Var) dir).type
                : (Type) dir;
                
        switch (binaryExpression.op.name()) {
	        case "AND":
			case "OR":
			case "PLUS":
			case "MINUS":
			case "MULT":
			case "DIV":
			case "LT":
			case "ASSIGN":
				if (tEsq.equivalent(tDir)) {
	                tipoRetorno = new Primitive(Kind.BOOLEAN);
	            }
	            else if (tEsq.equivalent(Primitive.FLOAT64) && tDir.equivalent(Primitive.INT32)) {
	            	tDir = Primitive.FLOAT64;
	                tipoRetorno = Primitive.BOOLEAN;
	            }
	            else if (tEsq.equivalent(Primitive.INT32) && tDir.equivalent(Primitive.FLOAT64)) {
	            	tEsq = Primitive.INT32;
	            	tipoRetorno = Primitive.BOOLEAN;
	            }
	            else {
	                erros.report("Impossível realizar a operação ["
	                        + binaryExpression.op.name() +  "] entre os tipos " + tEsq
	                        + " e " + tDir); 
	                // Retornando bool apenas para prosseguir a checagem sem erros
	                tipoRetorno = Primitive.BOOLEAN;
	            }
	            break;
			case "EQ":
			default:
				return null;
		}
        return tipoRetorno;
	}

	@Override
	public Object VisitIntLiteral(IntLiteral intLiteral) {
		return Primitive.INT32;
	}

	@Override
	public Object VisitFloatLiteral(FloatLiteral floatLiteral) {
		return Primitive.FLOAT64;
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
		return Primitive.BOOLEAN;
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
		declarationStm.decl.accept(this);
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
		List<Expression> leftExps = assignment.leftExps;
		List<Expression> rightExps = assignment.rightExps;
		if(leftExps.size() != rightExps.size()) {
			erros.report("Declaração de atribuição inválida. É necessário que os termos a esquerda e direita sejam do mesmo tamanho.");
		}else {
			ListIterator<Expression> lIt = leftExps.listIterator();
			for(Expression rexp : rightExps) {
				Expression lexp = lIt.next();
				Type rTy = (Type) rexp.accept(this);
				Type lTy = (Type) lexp.accept(this);
				if(!rTy.assignableTo(lTy)) // testa se a atribuição é equivalentes
					erros.report("Não é possivel fazer esta atribuição!");
			}
		}
		return null;
	}

	@Override
	public Object VisitShortVarDecl(ShortVarDecl shortVarDecl) {
		Iterator<String> idIt = shortVarDecl.names.iterator();
		Iterator<Expression> expIt = shortVarDecl.exps.iterator();
		
		while (idIt.hasNext() && expIt.hasNext()){
			try {
				env.put(idIt.next(), new Var((Type) expIt.next().accept(this)));
			} catch (Exception e) {
				 erros.report("A variável " + idIt.next() + " já foi declarada neste escopo");
			}
		}

        return null;
	}

	@Override
	public Object VisitBlock(Block block) {
		for (Statement stm : block.stmts) {
			stm.accept(this);
		}
		return null;
		
		/*Type t = (Type) block.accept(this);
        if (! t.equals(Kind.BOOLEAN)) {
            erros.report("Esperada uma expressão do tipo boolean, recebido argumento tipo" + t + " inválido.");
        }
        ifStmt.initStmt.accept(this);
        ifStmt.block.accept(this);
        return null;
        
		erros.report("Block");
		return null;
		
		StringBuilder result = new StringBuilder();

		result.append("{\n");
		indent();
		
		for (Statement stm : block.stmts) {
			if (stm instanceof EmptyStmt)
				continue;
			if (stm != null) 
				result.append(print(stm.accept(this), true)); 
			result.append("\n");
		}
		
		unindent();
		result.append(print("}", true));

		return result.toString();*/
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
		return Primitive.BOOLEAN;
	}

	@Override
	public Object IncrStmt(pasito.ast.statement.IncrStmt incrStmt) {
		// TODO Auto-generated method stub
		return null;
	}
}
