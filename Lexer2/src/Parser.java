import java.util.ArrayList;

public class Parser {
    Lexer lexer;

    public Parser(Lexer lexer) {
        this.lexer=lexer;
    }
    Syntax.Node ParseExpression() throws Exception {
        Syntax.Node left = ParseTerm();
        while (true){
            Token t= lexer.next();
            if(t.token.equals("-")||t.token.equals("+")) {
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
            if(t.token.equals("*")||t.token.equals("/")) {
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
        if(t.type==TokenType.IDENTIFIER){
            return new Syntax.NodeVar((String)t.token);
        }
        if(t.token.equals("(")){
            Syntax.Node expression =  ParseExpression();
            if(!lexer.next().token.equals(")")){
                throw new Exception("missing closing )");
            }
            return expression;
        }
        throw new Exception("unknown token");
    }


}
