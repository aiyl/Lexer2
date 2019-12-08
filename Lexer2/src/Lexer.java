import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;

public class Lexer {
//    boolean commentFlag;
    String str;
    int pos, linenum;
    HashSet<String> keyWords = new HashSet<>();

    public void getKeyWords(HashSet<String> keyWords) {
        this.keyWords = keyWords;
    }
    HashSet <Character> arithmeticOperator = new HashSet<>();
    public void getArithmeticOperator(HashSet<Character> arithmeticOperator) {
        this.arithmeticOperator = arithmeticOperator;
    }
    HashSet <Character> symbols = new HashSet<>();
    public void getSymbols(HashSet<Character> symbols) {
        this.symbols= symbols;
    }
    HashSet<Character> comparison = new HashSet<>();

    public void getComparison(HashSet<Character> comparison) {
        this.comparison = comparison;
    }

    HashSet<Character> separateOperator = new HashSet<>();

    public void getSeparateOperator(HashSet<Character> separateOperator) {
        this.separateOperator = separateOperator;
    }

    Lexer(String string, int linenum) {
        this.str = string;
        this.linenum=linenum;
    }
    public int getPos() {
        return pos;
    }
    Token next() {
        int length = str.length();
        if (pos >= length)
            return null;

        char ch = str.charAt(pos);
        while (pos < length && ch == ' ') {
            pos++;
            ch = str.charAt(pos);
        }
        if (pos >= length)
            return null;
        boolean chIsDouble=false;
        if (Character.isDigit(ch) ||(ch=='-')) {
            String t="";
            if(ch=='-'){
                pos++;
                t+="-";
            }
            while (pos < length && (Character.isDigit(str.charAt(pos) )|| (str.charAt(pos)=='.'))) {
                if (str.charAt(pos)=='.'){
                    chIsDouble=true;
                }
                t += str.charAt(pos);
                pos++;
            }
            if(chIsDouble==true &&(str.charAt(pos-1)!='-')){
                return new Token(linenum, pos-t.length(),"double", t );
            }
            if (str.charAt(pos-1)!='-') {
                return new Token(linenum, pos-t.length(), "int", t);
            }
        }
        if (Character.isLetter(ch)) {
            String t = "";
            while (pos < length && Character.isLetterOrDigit(str.charAt(pos))) {
                t += str.charAt(pos);
                pos++;
            }
            if(keyWords.contains(t)){
                return new Token(linenum,pos-t.length(),"Key word",t);
            }
            return new Token(linenum,pos-t.length(),"ident", t);
        }
        if ((str.charAt(pos)==':') &&(str.charAt(pos+1)=='=')){
            pos=pos+2;
            return new Token(linenum,pos-1,"assignment operator", ":=");
        }
        if ((str.charAt(pos)=='/') && (str.charAt(pos+1)=='/')){
            int i=pos;
            pos=pos+(str.length()-pos);
            return new Token(linenum,i,"comment", "/");
        }
        if((str.charAt(pos)=='/') &&(str.charAt(pos+1) =='*')){
            int i=pos;
            pos=pos+2;
            String closeComment = "";
            while (closeComment!="*/"){
                closeComment+=str.charAt(pos);
                if(pos+1>=length){
                    return null;}
                closeComment+=str.charAt(pos+1);
                if(closeComment.equals("*/")){
                   // commentFlag=true;
                    pos=pos+2;
                    return new Token(linenum,i,"comment", "/*");
                }
                closeComment="";
                pos++;
            }

        }
        if (arithmeticOperator.contains(ch)){
            if(ch=='-'){
                pos--;
            }
            pos++;
            return new Token(linenum,pos-1,"arithmetic operator", String.valueOf(str.charAt(pos-1)));
        }
        if (symbols.contains(ch)){
            pos++;
            return new Token(linenum,pos-1,"symbols", String.valueOf(str.charAt(pos-1)));
        }
        if (separateOperator.contains(ch)){
            pos++;
            return new Token(linenum,pos-1,"Separate Operator", String.valueOf(str.charAt(pos-1)));
        }
        if (comparison.contains(ch)){
            pos++;
            return new Token(linenum,pos-1,"Comparison Operator", String.valueOf(str.charAt(pos-1)));
        }

        pos++;
        return new Token(linenum, pos-1,"error", String.valueOf(str.charAt(pos-1)));
        //return null;
    }
}



