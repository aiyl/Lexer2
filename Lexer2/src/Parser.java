import java.util.ArrayList;

public class Parser {
    Lexer lexer;

    public Parser(Lexer lexer) {
        this.lexer=lexer;
    }

    Syntax.Node  ParseIndentList() throws Exception{
       //ArrayList <Syntax.Node> identList = new ArrayList<>();
        ArrayList <String> identList = new ArrayList<>();
       // Syntax.Node variabledecl = new Syntax.NodeUnaryOP("var", );
        Token t= lexer.next();
        //Syntax.Node variable;
        String variable;
        while (!t.token.equals(";")) {
            if (t.type == TokenType.IDENTIFIER && (t.type != TokenType.KEYWORD)) {
                variable = new Syntax.NodeVar((String) t.token).name;
                identList.add(variable);
                t=lexer.next();
            } else
                if (t.token.equals(","))
                    t = lexer.next();
                else
                    throw new Exception("this variable can't be ident or can't find ; ");
        }
       // System.out.println(identList);
        return new Syntax.IdentList(identList);
    }

    /*Syntax.Node ParseBlock() throws Exception{
        ArrayList<Syntax.Node> declList = new ArrayList<>();
        Token t= lexer.next();
        Syntax.Node decl=null; //Change it in future
        if (t.token.equals("const") || t.token.equals("var") || t.token.equals("function") || t.token.equals("procedure") && t.type==TokenType.KEYWORD){
            decl = ParseDeclarations();
            declList.add(decl);
            while (t.token.equals("const") || t.token.equals("var") || t.token.equals("function") || t.token.equals("procedure") && t.type==TokenType.KEYWORD){
                decl =  ParseDeclarations();
                declList.add(decl);
            }
        }
        if (t.token.equals("begin")){
            Syntax.Node states= ParseStatementSequence();
        }
        return new Syntax.BlockNode(declList, decl);
    }


    Syntax.Node ParseDeclarations() throws Exception{
        Token t= lexer.next();
        Syntax.Node constant;
        ArrayList<Syntax.DeclarationsNode> declList = new ArrayList<>();
        if((t.token.equals("const")){
            constant = new ParseConstantDefBlock();
            declList.add(constant);
        }
        if((t.token.equals("var")){
            constant = new ParseVariableDeclBlock();
            declList.add(constant);
        }
        if((t.token.equals("function") || t.token.equals("procedure")){
            constant = new ParseSubprogDeclList();
            declList.add(constant);
        }
        return new Syntax.DeclarationsNode(declList);
    }

*/

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
        if(t.type==TokenType._STRING){
            return new Syntax.NodeString((String)t.token);
        }
        if(t.token.equals("nil")){
            lexer.next();
            return new Syntax.NodeNil("nil");
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
        if(t.token.equals("not")){
            lexer.next();
            Syntax.Node p = ParseNot();
            return new Syntax.NodeNot("not", p);
        }

        Syntax.Node left = ParseSetValue();

        throw new Exception("unknown token");
    }

    Syntax.Node ParseNot() throws Exception{
        Syntax.Node left = ParseFactor();
        return left;
    }

    Syntax.Node ParseSetValue() throws Exception{
        ArrayList<Syntax.Node> valueList = new ArrayList<>();
        Token t = lexer.next();
        if (t.token.equals("[")){
            while (true){
            Syntax.Node element =  ParseElement();
            valueList.add(element);
            if (t.token.equals(",")){
                t=lexer.next();
            }
            if(lexer.next().token.equals("]"))
                break;
            }
            return new Syntax.NodeSetValue(valueList);
        }
        throw new Exception(" Missing close !!!]");
    }

    Syntax.Node ParseElement() throws Exception{
        Syntax.Node constExpression = ParseConstExpression();
        Token t=lexer.next();
        while (true) {
            if (t.token.equals("..")) {
                t = lexer.next();
                constExpression = ParseConstExpression();
            }
            else
                //lexer.putBack(t);
                break;
        }
    return constExpression;
    }
    Syntax.Node ParseConstExpression() throws Exception{
        Syntax.Node constFactor = ParseFactor();
        Token t = lexer.next();
        return constFactor; // SO WARNING!!!
    }

    Syntax.Node ParseNodeCall(){
        Token t=lexer.next();
    return null;
    }



}
