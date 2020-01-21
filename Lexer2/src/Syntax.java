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
    public class NodeUnaryOP extends Node {
        String opname;
        Node arg;
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
