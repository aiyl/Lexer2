import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;

public class Lexer {
    String str;
    int pos;
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
                return new Token("double", t);
            }
            if (str.charAt(pos-1)!='-') {
                return new Token("int", t);
            }
        }
        if (Character.isLetter(ch)) {
            String t = "";
            while (pos < length && Character.isLetterOrDigit(str.charAt(pos))) {
                t += str.charAt(pos);
                pos++;
            }
            if(keyWords.contains(t)){
                return new Token("Key word",t);
            }
            return new Token("ident", t);
        }

        if (arithmeticOperator.contains(ch)){
            pos++;
            return new Token("arithmetic operator", String.valueOf(str.charAt(pos-1)));

        }
        pos++;
        return new Token("error", String.valueOf(str.charAt(pos-1)));
        //return null;
    }
}



