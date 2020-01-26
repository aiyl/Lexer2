import java.beans.Expression;
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
  /*  Syntax.Node ParseExpr() throws Exception {
        Syntax.Node left = ParseExpression();
        Token t = lexer.next();
        if(t.token=="="||t.token=="<>"||t.token=="<"||t.token==">"||t.token=="<="||t.token==">="||t.token=="in" ){
            var right = ParseExpression();
            return new Syntax.NodeBinaryOP((String) t.token, left, right);
        }
    }*/

    Syntax.Node ParseExpression() throws Exception {
        Syntax.Node left = ParseTerm();
        while (true){
            Token t= lexer.next();
            if(t.token.equals("=")||t.token.equals("<>")||t.token.equals("<")||t.token.equals(">")||t.token.equals("<=")||t.token.equals(">=")||t.token.equals("in") ){
                var right = ParseExpression();
                return new Syntax.NodeBinaryOP((String) t.token, left, right);
            }

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
            if(t.token.equals(")")){
               // lexer.putBack(t);
                var funcCall = ParseFunctionalCall(t);
                return funcCall;
            }
            return new Syntax.NodeVar((String)t.token);
        }
        if(t.type==TokenType._STRING){
            return new Syntax.NodeString((String)t.token);
        }

        if(t.token.equals("nil")){
            return new Syntax.NodeNil("nil");
        }
        if(t.type==TokenType.BOOLEAN){
            return new Syntax.Nodeboolean((String) t.token);
        }


        if(t.token.equals("-")){
            Token t2=lexer.next();
            lexer.putBack(t2);
            Syntax.Node factor =  ParseFactor();
            return new Syntax.NodeUnaryOP("-", factor);
        }
        if(t.token.equals("(")){
            Syntax.Node expression =  ParseExpression();
            if(!lexer.next().token.equals(")")){
                throw new Exception("missing closing )");
            }
            return expression;
        }
        if(t.token.equals("not")){
            Token t2=lexer.next();
            lexer.putBack(t2);
            Syntax.Node factor =  ParseExpression();
            return new Syntax.NodeNot("not", factor);
        }


        //Syntax.Node left = ParseSetValue();

        throw new Exception("unknown token");
    }

    Syntax.Node ParseFunctionalCall(Token t ) throws Exception{
        if (t.type == TokenType.IDENTIFIER){
            var ident = new  Syntax.NodeVar((String) t.token);
            var actParams = ParseActualParameters(t);
            return new Syntax.NodeFunctionalCall("functionalCall", ident, actParams);
        }
    throw new Exception("Error in ParseFunctionalCall !!! ");
    }
    Syntax.Node ParseNot() throws Exception {
        var left = ParseExpression();
        return left;
    }
    Syntax.Node ParseIfStatement() throws Exception {
        Token t = lexer.next();
        if(t.type==TokenType.IDENTIFIER || (t.token.equals("(")) || (t.token.equals("not")))
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
            throw new Exception("unexpected THEN !!!");
        }
    }
    Syntax.Node ParseStatement() throws Exception {
        Token t=lexer.next();
        if(t.token.equals("if")) {
            var statement = ParseIfStatement();
            return statement;
        }
        if(t.token.equals("for")) {
            lexer.putBack(t);
            var statement = ParseForStatement();
            return statement;
        }
        if(t.token.equals("read") || t.token.equals("readln") || t.token.equals("write")|| t.token.equals("writeln")){
            var statement = ParseIOStatement(t);
            return statement;
        }

        if(t.type==TokenType.IDENTIFIER) {
            var ident = new Syntax.NodeVar((String) t.token);
            t = lexer.next();
            if(t.token.equals("(")){
                var statement = ParseProcedureCall(ident, t);
                return statement;
            }
            //lexer.putBack(t);
            if(t.token.equals(":=")){
                var statement = ParseAssignment(t, ident);
                return statement;
            }
        }
        if(t.token.equals("while")) {
            lexer.putBack(t);
            var statement = ParseWhileStatement();
            return statement;
        }
        if(t.token.equals("repeat")) {
            lexer.putBack(t); // ?????
            var statement = ParseRepeatStatement();
            return statement;
        }

        throw new Exception("can't find statement !!! ");
    }



    Syntax.Node ParseRepeatStatement() throws Exception{
        Token t = lexer.next();
        String op="repeat";
        while (true){
           // t=lexer.next();
            if (t.token.equals("repeat")){
                var statementSequence = ParseStatementSequence();
                t = lexer.next();
                if(t.token.equals("until")){
                    var exp = ParseExpression();
                    return new Syntax.NodeRepeatStatement(op, statementSequence, exp);
                }
             throw new Exception("need repeat!!!");
            }
        }
    }

    Syntax.Node ParseStatementSequence() throws Exception{
        ArrayList<Syntax.Node> statements = new ArrayList<>();
        Token t =lexer.next();
        while (!t.token.equals("end")){
            if(t.type==TokenType.IDENTIFIER || t.token.equals("while") || t.token.equals("if") || t.token.equals("for") || t.token.equals("repeat") )
                lexer.putBack(t);
            //if (t.token.equals("begin")){
                var statement = ParseStatement();
                t = lexer.next();
                if (t.token.equals(";")){
                    t = lexer.next();}
                    //statement = ParseStatement();
                statements.add(statement);
                if (t.token.equals("end")){
                    return new Syntax.NodeStatements(statements);
                    //break;
                }
            //}
            //throw new Exception("need begin !!! ");
        }
        throw new Exception("error in ParseStatementSequence !!! ");
    }

    Syntax.Node ParseProcedureCall(Syntax.NodeVar ident, Token t) throws Exception {
        String op = "procedure";
        //Token t =lexer.next();
       // lexer.putBack(t);
        if (t.token.equals("(")){
            //var ident = new Syntax.NodeVar((String) t.token);
            // lexer.next();
            var actualParameters = ParseActualParameters(t);
            return new Syntax.NodeProcedureCall(op, ident, actualParameters);
        }
     throw new Exception("Error in ParseProcedureCall !!! ");
    }

    Syntax.Node ParseActualParameters(Token t) throws Exception{
        //Token t = lexer.next();
       // String op = "expList";
        if (t.token.equals("(")){
            var expList =  ParseExpList();
            t = lexer.next();
            if (t.token.equals(")")){
                return new Syntax.NodeActualParameters( expList);
            }
        }
    throw new Exception("Error in ParseActualParameters !!! ");
    }
    Syntax.Node ParseExpList() throws Exception{
        ArrayList exprs = new ArrayList();
        String op ="expList";
        while (true){
            var exp = ParseExpression();
            Token t = lexer.next();
            if (t.token.equals(","))
                t = lexer.next();
            exprs.add(exp);
            if (t.type == TokenType.IDENTIFIER)
                lexer.putBack(t);
            if (t.token.equals(")")){
                lexer.putBack(t);
                return new Syntax.NodeExpList( exprs);
                //break;
            }
        }
    //throw new Exception("Error in ParseExpList");
    }

    Syntax.Node ParseForStatement() throws Exception {
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

    Syntax.Node ParseWhileStatement() throws Exception{
        String op ="while";
        Token t=lexer.next();
        if(t.token.equals("while")){
            var expr = ParseExpression();
            t=lexer.next();
          //  t=lexer.next();
            if (t.token.equals("do")){
                var statement = ParseStatement();
                return new Syntax.NodeWhileStatement(op, expr, statement);
            }
            throw new Exception("you have to do something after do !!! ");
        }
        throw new Exception("error in ParseWhileStatement ");
    }

    Syntax.Node ParseAssignment(Token t, Syntax.NodeVar left) throws Exception{
       // Token t = lexer.next();
        while (true){
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
        throw new Exception("Error in ParseAssignment!!!  ");

    }

    Syntax.Node ParseIOStatement(Token t) throws Exception{
        String op="";
        if(t.token.equals("read") || t.token.equals("readln")){
            if (t.token.equals("read"))
                op="read";
            else
                op = "readln";
            t = lexer.next();
            if(t.token.equals("(")){
                var designatorList = ParseDesignatorList();
                t = lexer.next();
                if(t.token.equals(")")){
                    return new Syntax.NodeInStatement(op, designatorList);
                }
            }
        }
        if(t.token.equals("write") || t.token.equals("writeln")){
            if (t.token.equals("write"))
                op="write";
            else
                op = "writeln";
            t = lexer.next();
            if(t.token.equals("(")){
                var expList = ParseExpList();
                t = lexer.next();
                if(t.token.equals(")")){
                    return new Syntax.NodeOutStatement(op, expList);
                }
            }
        }
        throw new Exception("Error in ParseIOStatement !!! ");
    }

    Syntax.Node ParseDesignatorList() throws Exception{
        Token t = lexer.next();
        ArrayList designatorList = new ArrayList();
        while (true) {
            var designator = ParseDesignator(t);
            t = lexer.next();
            if (t.token.equals(",")) {
                t = lexer.next();
            }
            designatorList.add(designator);
            if (t.token.equals(")")) {
                lexer.putBack(t);
                return new Syntax.NodeDesignatorList("designatorList", designatorList);
            }
        }
    }

    Syntax.Node ParseDesignator(Token t) throws Exception{
        if(t.type == TokenType.IDENTIFIER){
            var ident = new Syntax.NodeVar((String) t.token);
            var designatorStuff = ParseDesignatorStuff();
            if(designatorStuff!=null)
                return new Syntax.NodeDesignator(ident, designatorStuff);
            else
                return ident;
        }
    throw new Exception("Error in ParseDesignator !!! ");
    }

    Syntax.Node ParseDesignatorStuff() throws Exception{
        Token t = lexer.next();
        if (t.token.equals(".")){
            t = lexer.next();
            if (t.type == TokenType.IDENTIFIER){
                var ident = new Syntax.NodeVar((String)t.token);
                return new Syntax.NodeDesignatorStuff(ident);
            }
        }
        if(t.token.equals(")") || t.token.equals(",")){
            lexer.putBack(t);
            return null;
        }

        if(t.token.equals("[")){
            //t = lexer.next();
            var expList = ParseExpList();
            if (t.token.equals("]")){
                return new Syntax.NodeDesignatorStuff(expList);
            }
        }
        throw new Exception("Error in ParseDesignatorStuff !!!");
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
        if (t.type==TokenType.BOOLEAN){
            return new Syntax.Nodeboolean((String) t.token);
        }

        throw new Exception("Error in ParseConstFactor !!! ");
    }

}
