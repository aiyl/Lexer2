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

    public static class IdentList extends Node {
        ArrayList<String> identList;
        @Override
        void print(int d){
            super.print(d);
            System.out.println(identList);
        }
        public IdentList(ArrayList<String> identList){
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
        ArrayList declarations;
        Node StatementSequence;
        @Override
        void print(int d){
            super.print(d);
            System.out.println(String.valueOf(declarations) );
        }
        public BlockNode(ArrayList declarations,Node StatementSequence){
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
        ArrayList declarations;
        @Override
        void print(int d){
            super.print(d);
            System.out.println(String.valueOf(declarations) );
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
