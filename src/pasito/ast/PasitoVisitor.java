package pasito.ast;


import pasito.ast.*;
import pasito.ast.declaration.ConstDecl;
import pasito.ast.declaration.TypeDecl;
import pasito.ast.declaration.VarDecl;
import pasito.ast.element.ExpressionElement;
import pasito.ast.element.Identifier;
import pasito.ast.element.KeyedElement;
import pasito.ast.element.LiteralElement;
import pasito.ast.expression.*;
import pasito.ast.methodSpecOrInterfaceName.InterfaceName;
import pasito.ast.methodSpecOrInterfaceName.MethodSpec;
import pasito.ast.signature.FormalParameter;
import pasito.ast.signature.Signature;
import pasito.ast.statement.*;
import pasito.ast.topLevelDecl.Dec;
import pasito.ast.topLevelDecl.FunctionDecl;
import pasito.ast.topLevelDecl.MethodDecl;
import pasito.ast.type.*;

public interface PasitoVisitor {

    Object VisitProgram(Program program);

    /* TopLevelDecl */
    Object VisitDec(Dec dec);
    Object VisitFunctionDecl(FunctionDecl functionDecl);
    Object VisitMethodDecl(MethodDecl methodDecl);

    Object VisitSignature(Signature signature);

    /* Declaration */
    Object VisitConstDecl(ConstDecl constDecl);
    Object VisitVarDecl(VarDecl varDecl);
    Object VisitTypeDecl(TypeDecl typeDecl);

    Object VisitFormalParameter(FormalParameter formalParameter);

    /* Type */
    Object VisitTypeName(TypeName typeName);
    Object VisitArrayType(ArrayType arrayType);
    Object VisitBaseType(PointerType pointerType);
    Object VisitStructType(StructType structType);
    Object VisitInterfaceType(InterfaceType interfaceType);
    Object VisitSliceType(SliceType sliceType);

    Object VisitFieldDecl(FieldDecl fieldDecl);

    /* MethodSpecOrInterfaceName */
    Object VisitMethodSpec(MethodSpec methodSpec);
    Object VisitInterfaceName(InterfaceName interfaceName);

    /* Expression */
    Object VisitUnaryExpression(UnaryExpression unaryExpression);
    Object VisitBinaryExpression(BinaryExpression binaryExpression);
    Object VisitBoolLiteral(BooleanLiteral boolLiteral);
    Object VisitIntLiteral(IntLiteral intLiteral);
    Object VisitFloatLiteral(FloatLiteral floatLiteral);
    Object VisitFunctionLiteral(FunctionLiteral functionLiteral);
    Object VisitCompositLit(CompositeLit compositeLit);
    Object VisitIdExpression(IdExpression idExpression);
    Object VisitMethodExpression(MethodExpression methodExpression);
    Object VisitSelectorExpression(SelectorExpression selectorExpression);
    Object VisitIndexExpression(IndexExpression indexExpression);
    Object VisitSliceExpression(SliceExpression sliceExpression);
    Object VisitFullSliceExpression(FullSliceExpression fullSliceExpression);
    Object VisitCallExpression(CallExpression callExpression);

    Object VisitKeyedElement(KeyedElement keyedElement);

    /* Element */
    Object VisitExpressionElement(ExpressionElement expressionElement);
    Object VisitLiteralElement(LiteralElement literalElement);

    /* Statement */
    Object VisitDeclarationStm(DeclarationStm declarationStm);
    Object VisitEmptyStmt(EmptyStmt emptyStmt);
    Object VisitReturnStmt(ReturnStmt returnStmt);
    Object VisitExpressionStmt(ExpressionStmt expressionStmt);
    Object VisitAssignment(Assignment assignment);
    Object VisitShortVarDecl(ShortVarDecl shortVarDecl);
    Object VisitBlock(Block block);
    Object VisitIfStmt(IfStmt ifStmt);
    Object VisitIfElseStmt(IfElseStmt ifElseStmt);
    Object VisitForStmt(ForStmt forStmt);
    Object VisitForRange(ForRange forRange);

    /* Operators */
    Object VisitBinaryOperator(BinaryOperator binaryOperator);
    Object VisitUnaryOperator(UnaryOperator unaryOperator);
}
