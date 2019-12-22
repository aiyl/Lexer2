import java.util.HashSet;

public class Lexer {
//    boolean commentFlag;
    String str;
    int pos, linenum=1, tokenstart=0, linestart=0;
    int openComment=0, closeComment=0;

    HashSet<String> keyWords = new HashSet<>();

    public void getKeyWords(HashSet<String> keyWords) {
        this.keyWords = keyWords;
    }
    HashSet<String> doubleOperators = new HashSet<>();

    public void getDoubleOperators(HashSet<String> doubleOperators) {
        this.doubleOperators = doubleOperators;
    }

    HashSet <Character> arithmeticOperator = new HashSet<>();
    public void getArithmeticOperator(HashSet<Character> arithmeticOperator) {
        this.arithmeticOperator = arithmeticOperator;
    }
    HashSet <Character> symbols = new HashSet<>();
    public void getSymbols(HashSet<Character> symbols) {
        this.symbols= symbols;
    }

    HashSet<Character> separateOperator = new HashSet<>();

    public void getSeparateOperator(HashSet<Character> separateOperator) {
        this.separateOperator = separateOperator;
    }

    Lexer(String string) {
        this.str = string;
    }
    public int getPos() {
        return pos;
    }

    private boolean isPeek(char ch) {
        return pos < str.length() - 1 && str.charAt(pos + 1) == ch;
    }

    private void skipSpaces() {
        while (pos < str.length()) {
            char ch = str.charAt(pos);
            if (ch=='\r') {
                linestart = pos + 1;
            }
            else if (ch == ' ') {
            }
            else if (ch == '\t') {
            }
            else if (ch == '\n'){
                linenum++;
                linestart = pos + 1;
            }
            else
                break;
            pos++;
        }
    }

    private void checkLineComment() {
        if (!(pos < str.length() && (str.charAt(pos)=='/') && isPeek('/')))
            return;
        pos = pos + 2;
        while (pos < str.length() && str.charAt(pos) != '\n' ){
            pos++;
        }
        pos++;
        if(pos>=str.length()){
            return;
        }
        linenum++;
        linestart = pos ;
    }



    private Token checkMultilineComment() {
        if (!(pos<str.length() && str.charAt(pos)=='{'))
            return null;

        while (pos<str.length() && str.charAt(pos)!='}' ){
            if (str.charAt(pos)=='\r')
                linestart = pos + 1;
            else if (str.charAt(pos) == '\n'){
                linenum++;
                linestart = pos + 1;
            }
            pos++;
        }

        if(pos >= str.length())
            return new Token(linenum, tokenstart,TokenType.ERROR, "cant find close comment");
        pos++;
        return null;
    }

    Token next() {
        str=str.toLowerCase();
        int length = str.length();

        while (true) {
            int oldPos = pos;
            skipSpaces();
            Token t = checkMultilineComment();
            if (t != null)
                return t;
            checkLineComment();
            if (pos >= length)
                return null;
            if (pos == oldPos)
                break;
        }

        tokenstart = pos - linestart + 1;
        boolean chIsDouble=false;
        boolean eFlag2 = false;
        int eFlag=0;
        int dotFlag=0;
        if (str.charAt(pos)=='"') {
            String t="";

            while (!isPeek('"' )){
                pos++;
                if(pos >= str.length())
                    return new Token(linenum, tokenstart,TokenType.ERROR, "cant find close string");
                t+=str.charAt(pos);
            }
            /*if(pos==length-1){
                pos++;
                return new Token(linenum, tokenstart,TokenType.ERROR, t);
            }*/
            pos=pos+2;
            return  new Token(linenum, tokenstart,TokenType._STRING, t);

        }
        if ((str.charAt(pos)=='.')&&isPeek('.')) {
            pos=pos+2;
            return new Token(linenum,pos-1,TokenType.SEPARATE_OPERATOR, "..");
        }

        if (Character.isDigit(str.charAt(pos))) {
            String t="";
            while (pos < length && (Character.isDigit(str.charAt(pos) )|| (str.charAt(pos)=='.') || (str.charAt(pos)=='e'))) {
                if (str.charAt(pos)=='.'){
                    if (isPeek('.'))
                        break;
                    dotFlag++;
                }

                if ((str.charAt(pos)=='.')&& (Character.isDigit(str.charAt(pos-1))) ){
                    if (pos< length-1 && Character.isDigit(str.charAt(pos+1)))
                        chIsDouble=true;
                }
                if (((str.charAt(pos)=='e') || (str.charAt(pos)=='E'))  && (Character.isDigit(str.charAt(pos-1)))){
                    eFlag++;
                    if (pos<length-2)
                        if (((str.charAt(pos+1)=='+') || (str.charAt(pos+1)=='-')) && Character.isDigit(str.charAt(pos+2 ))) {
                            eFlag2 = true;
                            t += str.charAt(pos);
                            pos++;
                        }
                }

                t += str.charAt(pos);
                pos++;
            }

            if((chIsDouble)||(eFlag>0)){
                if ((dotFlag==1 && !eFlag2) || ((dotFlag==1||dotFlag==0) && eFlag2 && (eFlag==1)))
                    return new Token(linenum, tokenstart,TokenType.DOUBLE, t);
                else{
                    return new Token(linenum, tokenstart,TokenType.ERROR, t);
                    }
            }
            else {
                    if (dotFlag==0 && eFlag==0)
                        return new Token(linenum, tokenstart, TokenType.INTEGER, t);
                    else
                        return new Token(linenum, tokenstart, TokenType.ERROR, t);
            }

    }
        if (Character.isLetter(str.charAt(pos))) {
            String t = "";
            while (pos < length && Character.isLetterOrDigit(str.charAt(pos))) {
                t += str.charAt(pos);
                pos++;
            }
            if(keyWords.contains(t)){
                return new Token(linenum,tokenstart,TokenType.KEYWORD,t);
            }
            return new Token(linenum,tokenstart,TokenType.IDENTIFIER, t);
        }
        if ((str.charAt(pos)==':') && isPeek('=')){
            pos=pos+2;
            return new Token(linenum,pos-1,TokenType.ASSIGNMENT_OPERATOR, ":=");
        }


        if (arithmeticOperator.contains(str.charAt(pos))) {
            if( isPeek('/')&&(str.charAt(pos)=='/')){
//                next();
                pos++;
                return null;
            }
                String t = "";
                while (
                        pos < length && arithmeticOperator.contains(str.charAt(pos)) &&
                                (t.length() == 0 || doubleOperators.contains(t + str.charAt(pos)))
                ) {
                    t += str.charAt(pos);
                    pos++;
                }
                return new Token(linenum, tokenstart, TokenType.ARITHMETIC_OPERATOR, t);
            }
        if (symbols.contains(str.charAt(pos)) && pos<length){
            pos++;
            return new Token(linenum,tokenstart,TokenType.SYMBOLS, String.valueOf(str.charAt(pos-1)));
        }
        if (separateOperator.contains(str.charAt(pos)) && pos<length){
            pos++;
            return new Token(linenum,tokenstart,TokenType.SEPARATE_OPERATOR, String.valueOf(str.charAt(pos-1)));
        }
        pos++;

        return new Token(linenum, tokenstart,TokenType.ERROR, String.valueOf(str.charAt(pos-1)));
        //return null;
    }
}



