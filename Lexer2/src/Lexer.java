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

    private void checkComment(){

       /* pos=pos+2;
        while (pos<length-1 && str.charAt(pos)!='\n'){
            pos++;
        }
        if(pos==length-1){
            return null;
        }
        linenum++;
        str=str.substring(pos+1,length);
        length=str.length();
        pos=0;*/
    }

    Token next() {
        str=str.toLowerCase();
        int length = str.length();
        if (pos >= length)
            return null;
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
            if (pos>=length){
                return null;
            }
            ch = str.charAt(pos);
        }
        if (pos >= length)
            return null;
        tokenstart = pos - linestart + 1;
        boolean chIsDouble=false;
        boolean eFlag2 = false;
        int eFlag=0;
        int dotFlag=0;

        if (str.charAt(pos)=='{'){
            int start=pos;
            pos++;
            while (pos<length-1 && str.charAt(pos)!='}' ){
                pos++;
            }
            if(pos>=length){
                return new Token(linenum, tokenstart,TokenType.ERROR, "cant find close comment");
            }
            //String str1=str.substring(0,start);
            String str2=str.substring(pos+1,length);
            str= str2 ;
            length=str.length();
            pos=0;
            if (pos+2<=length && (str.charAt(pos)=='\r')){
              pos=pos+2;
              if (pos==length){
                  return null;
              }
            }

        }

        if ((str.charAt(pos)=='/') && (str.charAt(pos+1)=='/')){
            //checkComment();
            pos=pos+2;
            while (pos<length-1 && str.charAt(pos)!='\n' ){
                pos++;
            }
            if(pos==length-1){
                return null;
            }
            linenum++;
            str=str.substring(pos+1,length);
            length=str.length();
            pos=0;

        }

        /*if ((str.charAt(pos)=='/') &&(str.charAt(pos+1)=='/')){
            //checkComment();
            pos=pos+2;
            while (str.charAt(pos)!='\n' && pos<length-1){
                pos++;
            }
            linenum++;
            if(pos==length-1){
                return null;
            }
            str=str.substring(pos+1,length);
            pos=0;

        } */
        if (str.charAt(pos)=='"') {
            //checkComment();
            String t="";

            while (pos<length-1 && str.charAt(pos+1)!='"' ){
                pos++;
                t+=str.charAt(pos);
            }
            if(pos==length-1){
                pos++;
                return new Token(linenum, tokenstart,TokenType.ERROR, t);
            }
            pos=pos+2;
            return  new Token(linenum, tokenstart,TokenType._STRING, t);

        }

        if (Character.isDigit(str.charAt(pos))) {
            String t="";
            while (pos < length && (Character.isDigit(str.charAt(pos) )|| (str.charAt(pos)=='.') || (str.charAt(pos)=='e'))) {
                if (str.charAt(pos)=='.'){
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
                if ((dotFlag==1 && !eFlag2) || (dotFlag==1 && eFlag2 && (eFlag==1)))
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
        if (arithmeticOperator.contains(str.charAt(pos))) {
            if((str.charAt(pos)=='/')&&(str.charAt(pos+1)=='/')){
                next();
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



