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


        //Syntax.Node left = ParseSetValue();

        throw new Exception("unknown token");
    }

    Syntax.Node ParseNot() throws Exception {
        Syntax.Node left = ParseFactor();
        return left;
    }
    Syntax.Node ParseIfStatement() throws Exception {
        Token t = lexer.next();
        if(t.type==TokenType.IDENTIFIER)
            lexer.putBack(t);
        String op="if";
        while (true) {
            var left = ParseExpression();
            t=lexer.next();
            var right = ParseStatement();
            if (t.token.equals("then")) {
                 t=lexer.next();
                if (t.token.equals("else")) {
                    var right2 = ParseStatement();
                    var elIf = new Syntax.ElIfNode(op, left,right, right2) ;
                    return elIf;
                }
                return new Syntax.IfNode(op, left, right);
            }
        }
    }
    Syntax.Node ParseStatement() throws Exception {
        Token t=lexer.next();
        if(t.token.equals("if")){
            var statement = ParseIfStatement();
            return statement;
        }
        if(t.token.equals("for")){
            var statement = ParseForStatement();
            return statement;
        }
        if(t.type==TokenType.IDENTIFIER){
            var statement = ParseAssignment(t);
            return statement;
        }
        throw new Exception("can't find statement !!! ");
    }

    Syntax.Node ParseForStatement() throws Exception{
        Token t = lexer.next();
       /* if (t.token.equals("for"))
            lexer.putBack(t);*/
        String op="for";
        while (true){
            t=lexer.next();
            if(t.type==TokenType.IDENTIFIER){
                Syntax.NodeVar ident = new Syntax.NodeVar((String) t.token);
                t=lexer.next();
                if (t.type==TokenType.ASSIGNMENT_OPERATOR){
                   // t=lexer.next();
                    var exp = ParseExpression();
                    t=lexer.next();
                    var way = ParseWhichWay(t);
                    var exp2=ParseExpression();
                    t=lexer.next();
                    if(t.token.equals("do")){
                        Syntax.Node statement = ParseStatement();
                        return   new Syntax.NodeForStatement(op, ident, exp, (Syntax.NodeWhichWay) way, exp2, statement );
                    }
                    throw new Exception("You have to do something after do !!! ");
                }
                throw new Exception("You need to add assignment !!! ");
            }
            throw new Exception("for FOR you need ident ");
        }
    }

    Syntax.Node ParseWhichWay(Token t) throws Exception{
        if(t.token.equals("to")) {
            return new Syntax.NodeWhichWay("to");
        }
        return new Syntax.NodeWhichWay("downto");
    }

    Syntax.Node ParseAssignment(Token t) throws Exception{
       // Token t = lexer.next();
        while (true){
        if(t.type==TokenType.IDENTIFIER){
            var left = new Syntax.NodeVar(String.valueOf(t.token));
            t=lexer.next();
            if(t.type==TokenType.ASSIGNMENT_OPERATOR){
                var right = ParseExpression();
                return new Syntax.AssignmentNode(":=", left, right);
            }
            else {
                lexer.putBack(t);
                break;
                //throw new Exception("Error in ParseAssignment!!! can't assign it ");
            }
        }
        else
            lexer.putBack(t);
            break;
        }
        throw new Exception("Error in ParseAssignment!!!  ");

    }

    Syntax.Node ParseSetValue() throws Exception{
        ArrayList<Syntax.Node> valueList = new ArrayList<>();
        Token t = lexer.next();
        if (t.token.equals("[")){
            while (true){
                t=lexer.next();
                if(t.type==TokenType.EOF)
                    break;
                if(t.token.equals("]"))
                    break;
                if (t.token.equals(",")){
                    t=lexer.next();
                }
                Syntax.Node element =  ParseElement(t);
                valueList.add(element);

            }
            return new Syntax.NodeSetValue(valueList);
        }
        throw new Exception(" Missing close !!!]");
    }

    Syntax.Node ParseElement(Token t) throws Exception{
        Token t2 = t;
        Syntax.Node element = ParseConstExpression(t);
        while (!t.token.equals("]")) {
            t = lexer.next();
            if (t.token.equals("..") && !(t.token.equals("]"))) {
                t=lexer.next();
                ParseConstExpression(t);
                Syntax.NodeElement element3 =  new Syntax.NodeElement(String.valueOf(t2.token)+".."+String.valueOf(t.token));
                return element3;
            }
            else
                lexer.putBack(t);
                break;
        }
    return element;
    }
    Syntax.Node ParseConstExpression(Token t) throws Exception{
        Syntax.Node constFactor = ParseConstFactor(t);
        if(t.token.equals("nil")){
            Syntax.Node left = new Syntax.NodeUnaryOP("nil",constFactor);
            return left;
        }
        if(t.token.equals("'")){
            Syntax.Node constFactor2 = ParseConstFactor(t);
            if(!lexer.next().token.equals("'")){
                throw new Exception("missing closing ' ");
            }
            return constFactor2;
        }
       // Syntax.Node left = new Syntax.NodeUnaryOP("what?", constFactor);
        return constFactor; // SO WARNING!!!
    }

    Syntax.Node ParseConstFactor(Token t) throws Exception{
        //Token t = lexer.next();
        if(t.type==TokenType.INTEGER){
            return new Syntax.NodeInteger((int)t.token);
        }
        if(t.type==TokenType.DOUBLE){
            return new Syntax.NodeDouble((double)t.token);
        }
        if(t.type==TokenType.IDENTIFIER){
            return new Syntax.NodeVar((String)t.token);
        }
        if(t.token.equals("nil")){
            //lexer.next();
            return new Syntax.NodeNil("nil");
        }
        throw new Exception("Error in ParseConstFactor !!! ");
    }
    Syntax.Node ParseNodeCall(){
        Token t=lexer.next();
    return null;
    }



}
