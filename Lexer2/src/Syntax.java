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
        ArrayList<Token> identList;
        @Override
        void print(int d){
            super.print(d);
            System.out.println(identList);
        }
        public IdentList(ArrayList<Token> identList){
            this.identList=identList;
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
