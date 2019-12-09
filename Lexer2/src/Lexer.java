import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;

public class Lexer {
//    boolean commentFlag;
    String str;
    int pos, linenum=1, tokenstart=0, linestart=0;

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
    Token next() {
        int length = str.length();
        if (pos >= length)
            return null;
        int k;
        char ch = str.charAt(pos);
        while (pos < length) {
            if (ch=='\r') {
                linestart = pos + 1;
            }
            else if(ch == ' ') {
            }
            else if (ch == '\n'){
                linenum++;
                linestart = pos + 1;
            }
            else
                break;
            pos++;
            ch = str.charAt(pos);
        }
        if (pos >= length)
            return null;
        tokenstart = pos - linestart + 1;
        if ((str.charAt(pos)=='/') && (str.charAt(pos+1)=='/')){
            pos=pos+2;
        }
        boolean chIsDouble=false;
        if (Character.isDigit(str.charAt(pos))) {
            String t="";
            while (pos < length && (Character.isDigit(str.charAt(pos) )|| (str.charAt(pos)=='.'))) {
                if (str.charAt(pos)=='.'){
                    chIsDouble=true;
                }
                t += str.charAt(pos);
                pos++;
            }
            if(chIsDouble){
                return new Token(linenum, tokenstart,TokenType.DOUBLE, t);
            }
            else {
                return new Token(linenum, tokenstart, TokenType.INTEGER, t);
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
        if ((str.charAt(pos)==':') &&(str.charAt(pos+1)=='=')){
            pos=pos+2;
            return new Token(linenum,pos-1,TokenType.ASSIGNMENT_OPERATOR, ":=");
        }

        /*if((str.charAt(pos)=='/') &&(str.charAt(pos+1) =='*')){
            int i=pos;
            pos=pos+2;
            String closeComment = "";
            while (closeComment!=""){
                closeComment+=str.charAt(pos);
                if(pos+1>=length){
                    return null;}
                closeComment+=str.charAt(pos+1);
                if(closeComment.equals("")){
                   // commentFlag=true;
                    pos=pos+2;
                    //return new Token(linenum,i,"comment", "");
                }
                closeComment="";
                pos++;
            }

        } */
        // a:= >= =  =++++b
        if (arithmeticOperator.contains(str.charAt(pos))){
            String t="";
            while (
                    pos < length && arithmeticOperator.contains(str.charAt(pos)) &&
                    (t.length() == 0 || doubleOperators.contains(t + str.charAt(pos)))
            ) {
                t += str.charAt(pos);
                pos++;
            }
            return new Token(linenum,tokenstart,TokenType.ARITHMETIC_OPERATOR, t);
        }
        if (symbols.contains(ch)){
            pos++;
            return new Token(linenum,tokenstart,TokenType.SYMBOLS, String.valueOf(str.charAt(pos-1)));
        }
        if (separateOperator.contains(ch)){
            pos++;
            return new Token(linenum,tokenstart,TokenType.SEPARATE_OPERATOR, String.valueOf(str.charAt(pos-1)));
        }

        pos++;
        return new Token(linenum, tokenstart,TokenType.ERROR, String.valueOf(str.charAt(pos-1)));
        //return null;
    }
}



