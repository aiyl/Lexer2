import java.util.ArrayList;

public  class Syntax {
    public static class  Node {
        void print(int d){
            System.out.print("  ".repeat(d));
        }
    }
    public static class NodeBinaryOP extends Node {
        String opname;
        Node left, right;
        @Override
        void print(int d){
            super.print(d);
            System.out.println(opname);
            left.print(d+1);
            right.print(d+1);
        }
        public NodeBinaryOP(String opname, Node left, Node right){
            this.opname=opname;
            this.left=left;
            this.right=right;
        }
    }
    public static class NodeUnaryOP extends Node {
        String opname;
        Node arg;
        @Override
        void print(int d){
            super.print(d);
            System.out.println(opname);
            arg.print(d+1);
        }
        public NodeUnaryOP(String opname, Node arg){
            this.opname=opname;
            this.arg=arg;
        }

    }
    public class NodeCall extends Node {
        String name;
        Node args;
        public NodeCall(String name, Node args) {
            this.name=name;
            this.args=args;
        }
    }
    public static class NodeVar extends Node {
        String name;
        @Override
        void print(int d){
            super.print(d);
            System.out.println(name);
        }
        public NodeVar(String name){
            this.name=name;
        }
    }

    public static class NodeFunctionalCall extends Node {
        String name;
        Node ident , params;
        @Override
        void print(int d){
            super.print(d);
            System.out.println(name);
            ident.print(d+1);
            params.print(d+1);
        }
        public NodeFunctionalCall(String name, Node ident, Node params){
            this.name=name;
            this.ident = ident;
            this.params = params;
        }
    }

    public static class IdentList extends Node {
        ArrayList<Syntax.Node> identList;
        @Override
        void print(int d){
            super.print(d);
            System.out.println("identList");
            for (int i=0; i<identList.size(); i++)
                identList.get(i).print(d+1);
        }
        public IdentList(ArrayList<Syntax.Node> identList){
            this.identList=identList;
        }
    }

    public static class ProgramParamsNode extends Node {
        IdentList identList;
        @Override
        void print(int d){
            super.print(d);
            System.out.println(identList);
        }
        public ProgramParamsNode(IdentList identList){
            this.identList=identList;
        }
    }

    public static class BlockNode extends Node {
        ArrayList<Syntax.Node> declarations;
        Node StatementSequence;
        @Override
        void print(int d){
            super.print(d);
            System.out.println(String.valueOf("BlockNode") );
            for (int i=0; i<declarations.size(); i++)
                declarations.get(i).print(d+1);
            StatementSequence.print(d+1);
        }

        public BlockNode(ArrayList<Syntax.Node> declarations,Node StatementSequence){
            this.declarations=declarations;
            this.StatementSequence=StatementSequence;

        }
    }

    public static class IfNode extends Node {
        String name;
        Node leftExpression;
        Node rightStatement;
        @Override
        void print(int d){
            super.print(d);
            System.out.println(String.valueOf(name) );
            leftExpression.print(d+1);
            rightStatement.print(d+1);
        }
        public IfNode(String name, Node leftExpression, Node rightStatement){
            this.name=name;
            this.leftExpression=leftExpression;
            this.rightStatement=rightStatement;

        }
    }

    public static class ElIfNode extends Node {
        String name;
        Node leftExpression;
        Node rightStatement;
        Node elseStatement;
        @Override
        void print(int d){
            super.print(d);
            System.out.println(String.valueOf(name) );
            leftExpression.print(d+1);
            rightStatement.print(d+1);
            elseStatement.print(d+1);
        }
        public ElIfNode(String name, Node leftExpression, Node rightStatement, Node elseStatement){
            this.name=name;
            this.leftExpression=leftExpression;
            this.rightStatement=rightStatement;
            this.elseStatement=elseStatement;

        }
    }

    public static class NodeForStatement extends Node{
        String name;
        NodeVar ident;
        NodeWhichWay way;
        Node exp1,  exp2, statement;
        @Override
        void print(int d){
            super.print(d);
           // System.out.println(String.valueOf(name) + " " + ident.name + " "+ exp1 + " " + way.name + " "+exp2);
            System.out.println(String.valueOf(name) );
            ident.print(d+1);
            exp1.print(d+1);
            way.print(d+1);
            exp2.print(d+1);
            statement.print(d+1);
        }
    public NodeForStatement(String name, NodeVar ident, Node exp1, NodeWhichWay whichWay, Node exp2, Node statement) {
        this.name = name;
        this.ident = ident;
        this.exp1 = exp1;
        this.exp2 = exp2;
        this.way = whichWay;
        this.statement = statement;
        }

    }

    public static class NodeWhileStatement extends Node{
        String name;
        Node expr, statement;
        @Override
        void print(int d){
            super.print(d);
            System.out.println(String.valueOf(name) );
            expr.print(d+1);
            statement.print(d+1);
        }
        public NodeWhileStatement(String name, Node expr, Node statement) {
            this.name = name;
            this.expr = expr;
            this.statement = statement;
        }

    }

    public static class NodeWhichWay extends Node{
        String name;
        @Override
        void print(int d){
            super.print(d);
            System.out.println(String.valueOf(name) );
        }
        public NodeWhichWay(String name){
            this.name = name;
        }

    }


    public static class AssignmentNode extends Node {
        String name;
        NodeVar ident;
        Node expr;
        @Override
        void print(int d){
            super.print(d);
            System.out.println(String.valueOf(name));
            ident.print(d+ 1);
            expr.print(d+1);
        }
        public AssignmentNode(String name, NodeVar ident,  Node expr){
            this.name=name;
            this.ident=ident;
            this.expr=expr;

        }
    }


    public static class ProgramModuleNode extends Node {
        String name;
        ProgramParamsNode params;
        BlockNode body;
        @Override
        void print(int d){
            super.print(d);
            System.out.println(String.valueOf(name) );
        }
        public ProgramModuleNode(String name, ProgramParamsNode params, BlockNode body){
            this.name=name;
            this.params=params;
            this.body=body;

        }
    }

    public static class NodeElement extends Node {
        String name;
        // Node element;
        @Override
        void print(int d){
            super.print(d);
            System.out.println(name);
        }
        public NodeElement(String name){
            this.name=name;
        }
    }

    public static class DeclarationsNode extends Node {
        ArrayList<Syntax.Node> declarations;
        @Override
        void print(int d){
            super.print(d);
            System.out.println(String.valueOf("var") );
            for (int i =0 ; i<declarations.size(); i++)
                declarations.get(i).print(d+1);
        }
        public DeclarationsNode(ArrayList declarations){
            this.declarations=declarations;
        }
    }

    public static class ConstDefBlockNode extends Node {
        ArrayList constans;
        @Override
        void print(int d){
            super.print(d);
            System.out.println(String.valueOf(constans) );
        }
        public ConstDefBlockNode(ArrayList constans){
            this.constans=constans;
        }
    }


    /*public static class IdentListBlock extends Node {
        ArrayList<Token> identList;
        @Override
        void print(int d){
            super.print(d);
            System.out.println(identList);
        }
        public IdentList(ArrayList<Token> identList){
            this.identList=identList;
        }
    }*/

    public static class NodeInteger extends Node {
        int value;
        @Override
        void print(int d){
            super.print(d);
            System.out.println(value);
        }
        public NodeInteger(int value){
            this.value=value;
        }
    }

    public static class NodeDouble extends Node {
        double value;
        @Override
        void print(int d){
            super.print(d);
            System.out.println(value);
        }
        public NodeDouble(double value){
            this.value=value;
        }
    }
    public static class NodeString extends Node {
        String value;
        @Override
        void print(int d){
            super.print(d);
            System.out.println(value);
        }
        public NodeString(String value){
            this.value=value;
        }
    }

    public static class NodeNil extends Node {
        String value;
        @Override
        void print(int d){
            super.print(d);
            System.out.println(value);
        }
        public NodeNil(String value){
            this.value=value;
        }
    }

    public static class Nodeboolean extends Node {
        String value;
        @Override
        void print(int d){
            super.print(d);
            System.out.println(value);
        }
        public Nodeboolean(String value){
            this.value=value;
        }
    }

    public static class NodeNot extends Node {
        String op;
        Node  left;
        @Override
        void print(int d){
            super.print(d);
            System.out.println(op);
            left.print(d+1);
        }
        public NodeNot(String op,Node left){
            this.op=op;
            this.left=left;
        }
    }

    public static class NodeStatements extends Node{
        ArrayList<Syntax.Node> statements;
        @Override
        void print(int d){
            super.print(d);
            System.out.println("statements");
            for (int i=0; i<statements.size(); i++){
                statements.get(i).print(d+1);
            }
        }
        public NodeStatements(ArrayList statements){
            this.statements = statements;
        }
    }

    public static class NodeRepeatStatement extends Node{
        String op;
        Node statementSequence, exp;
        @Override
        void print(int d){
            super.print(d);
            System.out.println(op);
            statementSequence.print(d+1);
            exp.print(d+1);
        }
        public NodeRepeatStatement(String op, Node statementSequence, Node exp){
            this.op = op;
            this.statementSequence = statementSequence;
            this.exp = exp;
        }
    }

    public static class NodeExpList extends Node {
        ArrayList<Syntax.Node> expList;
       // String op;
        @Override
        void print(int d) {
            super.print(d);
            System.out.println("expressions");
            for (int i=0; i<expList.size(); i++){
                expList.get(i).print(d+1);
            }
        }

        public  NodeExpList( /*String op,*/ ArrayList<Syntax.Node> expList) {
            this.expList = expList;
            //this.op = op;
        }
    }

    public static class NodeActualParameters extends Node {
        Node expList;
       // String op;
        @Override
        void print(int d) {
            super.print(d);
            System.out.println("params");
            expList.print(d+1);
        }
        public  NodeActualParameters(/*String op, */ Node expList) {
          //  this.op = op;
            this.expList = expList;
        }
    }


    public static class NodeProcedureCall extends Node {
        String op;
        Node ident, params;
        @Override
        void print(int d) {
            super.print(d);
            System.out.println(op);
            ident.print(d+1);
            params.print(d+1);
        }
        public  NodeProcedureCall(String op, Node ident, Node params) {
            this.op=op;
            this.ident = ident;
            this.params = params;
        }
    }

    public static class NodeInStatement extends Node {
        String op;
        Node designatorlist;
        @Override
        void print(int d) {
            super.print(d);
            System.out.println(op);
            designatorlist.print(d+1);
        }
        public  NodeInStatement(String op, Node designatorlist) {
            this.op=op;
            this.designatorlist = designatorlist;
        }
    }

    public static class NodeOutStatement extends Node {
        String op;
        Node expList;
        @Override
        void print(int d) {
            super.print(d);
            System.out.println(op);
            expList.print(d+1);
        }
        public  NodeOutStatement(String op, Node expList) {
            this.op=op;
            this.expList = expList;
        }
    }

    public static class NodeDesignatorList extends Node {
        String op;
        ArrayList<Syntax.Node> desList;
        @Override
        void print(int d) {
            super.print(d);
            System.out.println(op);
            for(int i=0; i<desList.size(); i++){
                desList.get(i).print(d+1);
            }
        }
        public  NodeDesignatorList(String op, ArrayList<Syntax.Node> desList) {
            this.op=op;
            this.desList = desList;
        }
    }

    public static class NodeDesignator extends Node {
        Node arg1, arg2;
        @Override
        void print(int d) {
            super.print(d);
            System.out.println("designator");
            arg1.print(d+1);
            arg2.print(d+1);
        }
        public  NodeDesignator(Node arg1, Node arg2) {
            this.arg1 = arg1;
            this.arg2 = arg2;
        }
    }

    public static class NodeDesignatorStuff extends Node {
        Node arg;
        @Override
        void print(int d) {
            super.print(d);
            System.out.println("designator stuff");
            arg.print(d+1);
        }
        public  NodeDesignatorStuff(Node arg) {
            this.arg = arg;
        }
    }

    public static class NodeConstDef extends Node {
        Node ident, constExp;
        @Override
        void print(int d) {
            super.print(d);
            System.out.println("ConstDef");
            ident.print(d+1);
            constExp.print(d+1);
        }
        public   NodeConstDef(Node ident, Node constExp) {
            this.ident = ident;
            this.constExp = constExp;
        }
    }

    public static class NodeConstDefBlock extends Node {
        Node ident;
        ArrayList<Syntax.Node> constList;
        @Override
        void print(int d) {
            super.print(d);
            System.out.println("ConstDefBlock");
            ident.print(d+1);
            for (int i=0; i<constList.size(); i++){
                constList.get(i).print(d+1);
            }
        }
        public   NodeConstDefBlock(Node ident, ArrayList<Syntax.Node> constList) {
            this.ident = ident;
            this.constList = constList;
        }
    }

    public static class NodeSetValue extends Node {
        ArrayList<Syntax.Node> list;

        @Override
        void print(int d) {
            super.print(d);
            System.out.println(list);
        }

        public NodeSetValue(ArrayList<Syntax.Node> list) {
            this.list = list;

        }
    }
    public static class NodeType extends Node {
        String type;
        @Override
        void print(int d) {
            super.print(d);
            System.out.println(type);
        }

        public NodeType(String type) {
            this.type = type;
        }
    }

    public static class NodeTypeDef extends Node {
        Node ident, type;
        @Override
        void print(int d) {
            super.print(d);
            System.out.println("NodeTypeDef");
            ident.print(d+1);
            type.print(d+1);
        }

        public NodeTypeDef(Node ident, Node type) {
            this.ident = ident;
            this.type = type;
        }
    }

    public static class NodeTypeDefBlock extends Node {
        Node  type;
        ArrayList<Syntax.Node> typedefList;
        @Override
        void print(int d) {
            super.print(d);
            System.out.println("NodeTypeDefBlock");
            type.print(d+1);
            for (int i=0; i<typedefList.size(); i++)
                typedefList.get(i).print(d+1);
        }

        public NodeTypeDefBlock(Node type, ArrayList<Syntax.Node> typedefList) {
            this.type = type;
            this.typedefList = typedefList;
        }
    }

    public static class NodeSubrange extends Node {
        Node  constFactor1, constFactor2;
        @Override
        void print(int d) {
            super.print(d);
            System.out.println("Subrange");
            constFactor1.print(d+1);
            constFactor2.print(d+1);
        }

        public NodeSubrange(Node constFactor1, Node constFactor2) {
            this.constFactor1 = constFactor1;
            this.constFactor2 = constFactor2;
        }
    }
    public static class NodeVariableDecl extends Node {
        Node  identList, type;
        @Override
        void print(int d) {
            super.print(d);
            System.out.println(":");
            type.print(d+1);
            identList.print(d+1);
        }

        public NodeVariableDecl(Node identList, Node type) {
            this.identList = identList;
            this.type = type;
        }
    }

    public static class NodeVariableDeclBlock extends Node {
        ArrayList<Syntax.Node> list;
        @Override
        void print(int d) {
            super.print(d);
            System.out.println("block");
            for (int i=0; i<list.size(); i++)
                list.get(i).print(d+1);
        }

        public NodeVariableDeclBlock(ArrayList<Syntax.Node> list) {
            this.list = list;
        }
    }

    public static class NodeSubprogList extends Node {
        Node  decl;
        @Override
        void print(int d) {
            super.print(d);
            System.out.println("SubprogList");
            decl.print(d+1);
        }

        public NodeSubprogList(Node decl) {
            this.decl = decl;
        }
    }

    public static class NodeProcedureDecl extends Node {
        Node  procHead, block;
        @Override
        void print(int d) {
            super.print(d);
            System.out.println("ProcedureDecl");
            procHead.print(d+1);
            block.print(d+1);
        }

        public NodeProcedureDecl(Node procHead, Node block) {
            this.procHead = procHead;
            this.block = block;
        }
    }

        public class NodeRecordAccess extends NodeUnaryOP {
            public NodeRecordAccess(String opname, Node arg) {
                super(opname, arg);
            }
        }


    public class NodeAssignOp extends NodeBinaryOP{
        public NodeAssignOp(String opname, Node left, Node right) {
            super(opname, left, right);
        }
    }




}
