import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;

public class Lexer {
    String str;
    int pos;
    HashSet<String> keyWords = new HashSet<>();

    public void setKeyWords(HashSet<String> keyWords) {
        this.keyWords = keyWords;
        keyWords.add("begin");      keyWords.add("const");      keyWords.add("file");
        keyWords.add("end");        keyWords.add("div");        keyWords.add("for");
        keyWords.add("array");      keyWords.add("do");         keyWords.add("function");
        keyWords.add("and");        keyWords.add("downto");     keyWords.add("goto");
        keyWords.add("case");       keyWords.add("else");       keyWords.add("to");
        keyWords.add("if");         keyWords.add("mod");        keyWords.add("of");
        keyWords.add("in");         keyWords.add("nil");        keyWords.add("packed");
        keyWords.add("label");      keyWords.add("not");        keyWords.add("procedure");
        keyWords.add("record");     keyWords.add("then");       keyWords.add("var");
        keyWords.add("repeat");     keyWords.add("type");       keyWords.add("while");
        keyWords.add("set");        keyWords.add("until");      keyWords.add("with");
    }
    HashSet <Character> symbols = new HashSet<>();
    public void setSymbols(HashSet<Character> symbols) {
        this.symbols = symbols;
        symbols.add('>');   symbols.add('#');   symbols.add('$');
        symbols.add('<');   symbols.add('=');   symbols.add('&');   symbols.add('@');
        symbols.add('^');   symbols.add('_');   symbols.add('~');   symbols.add('%');
    }
    HashSet <Character> arithmeticOperator = new HashSet<Character>();

    public HashSet<Character> setArithmeticOperator() {
        arithmeticOperator.add('*');    arithmeticOperator.add('/');
        arithmeticOperator.add('-');    arithmeticOperator.add('+');
        return arithmeticOperator;
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
            String t="-";
            if(ch=='-'){
                pos++;
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


        pos++;
        return new Token("error", String.valueOf(str.charAt(pos-1)));
        //return null;
    }
}



