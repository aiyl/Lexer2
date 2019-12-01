import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Lexer {
    String str;
    int pos;
    FileReader fileReader;

    Lexer(String string) {
        this.str = string;

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

        if (Character.isDigit(ch)) {
            String t = "";
            while (pos < length && Character.isDigit(str.charAt(pos))) {
                t += str.charAt(pos);
                pos++;
            }
            return new Token("int", t);
        }
        if (Character.isLetter(ch)) {
            String t = "";
            while (pos < length && Character.isLetterOrDigit(str.charAt(pos))) {
                t += str.charAt(pos);
                pos++;
            }
            return new Token("ident", t);
        }

        return null;









    }
}



