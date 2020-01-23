import java.util.ArrayList;

public class Parser {
    Lexer lexer;

    public Parser(Lexer lexer) {
        this.lexer=lexer;
    }

    ArrayList<Syntax.Node>  ParseIndentList() throws Exception{
       ArrayList <Syntax.Node> identList = new ArrayList<>();
       // Syntax.Node variabledecl = new Syntax.NodeUnaryOP("var", );
        Token t= lexer.next();
        Syntax.Node variable;
        while (!t.token.equals(";")) {
            if (t.type == TokenType.IDENTIFIER) {
                variable = new Syntax.NodeVar((String) t.token);
                identList.add(variable);
                variable.print(1);
                //System.out.println(variable);
                t=lexer.next();
            } else
                if (t.token.equals(","))
                    t = lexer.next();
                else
                    throw new Exception("this variable can't be ident ");
        }
       // System.out.println(identList);
        return identList;
    }

    Syntax.Node ParseVariableDecl() throws Exception{
        return null;
    }

    Syntax.Node ParseExpression() throws Exception {
        Syntax.Node left = ParseTerm();
        while (true){
            Token t= lexer.next();
            if(t.token.equals("or")||t.token.equals("-")||t.token.equals("+")) {
                Syntax.Node right = ParseTerm();
                left=new Syntax.NodeBinaryOP(String.valueOf(t.token), left,right);
            }
            else
            {
                lexer.putBack(t);
                break;
            }
        }
        return left;
    }
    Syntax.Node ParseTerm() throws Exception{
        Syntax.Node left = ParseFactor();
        while (true){
            Token t= lexer.next();
            if(t.token.equals("and")||t.token.equals("*")||t.token.equals("/")||t.token.equals("div")||t.token.equals("mod")) {
                Syntax.Node right = ParseFactor();
                left=new Syntax.NodeBinaryOP(String.valueOf(t.token), left,right);
            }
            else
            {
                lexer.putBack(t);
                break;
            }
        }
        return left;
    }

    Syntax.Node ParseFactor() throws Exception{
        Token t= lexer.next();
        if(t.type==TokenType.INTEGER){
            return new Syntax.NodeInteger((int)t.token);
        }
        if(t.type==TokenType.DOUBLE){
            return new Syntax.NodeDouble((double)t.token);
        }
        if(t.type==TokenType.IDENTIFIER){
            return new Syntax.NodeVar((String)t.token);
        }
        if(t.token.equals("-")){
            Token t2=lexer.next();
            lexer.putBack(t2);
            Syntax.Node factor =  ParseFactor();
            return new Syntax.NodeUnaryOP("-"+t2.token, factor);
        }
        if(t.token.equals("(")){
            Syntax.Node expression =  ParseExpression();
            if(!lexer.next().token.equals(")")){
                throw new Exception("missing closing )");
            }
            return expression;
        }
//        Syntax.Node left = ParseSetValue();

        throw new Exception("unknown token");
    }
    /*Syntax.Node ParseNodeCall(){
        Token t=lexer.next();

        }
    }*/


}
