import java.io.*;
import java.util.HashSet;

public class Main {
    public static void main(String[] args) throws Exception {
        HashSet<String> keyWords = new HashSet<>();
        keyWords.add("begin");      keyWords.add("const");      keyWords.add("file");       keyWords.add("break");
        keyWords.add("end");        keyWords.add("div");        keyWords.add("for");        keyWords.add("continue");
        keyWords.add("array");      keyWords.add("do");         keyWords.add("function");   keyWords.add("writeln");
        keyWords.add("and");        keyWords.add("downto");     keyWords.add("goto");       keyWords.add("write");
        keyWords.add("case");       keyWords.add("else");       keyWords.add("to");         keyWords.add("readln");
        keyWords.add("if");         keyWords.add("mod");        keyWords.add("of");         keyWords.add("read");
        keyWords.add("in");         keyWords.add("nil");        keyWords.add("packed");     keyWords.add("char");
        keyWords.add("label");      keyWords.add("not");        keyWords.add("procedure");  keyWords.add("exit");
        keyWords.add("record");     keyWords.add("then");       keyWords.add("var");        keyWords.add("xor");
        keyWords.add("repeat");     keyWords.add("type");       keyWords.add("while");      keyWords.add("integer");
        keyWords.add("set");        keyWords.add("until");      keyWords.add("with");       keyWords.add("double");

        HashSet <Character> arithmeticOperator = new HashSet<>();
        arithmeticOperator.add('*');    arithmeticOperator.add('/');    arithmeticOperator.add('^');
        arithmeticOperator.add('-');    arithmeticOperator.add('+');
        HashSet <Character> symbols = new HashSet<>();
        symbols.add('#');   symbols.add('$');
        symbols.add('&');   symbols.add('@');   symbols.add(':');
        symbols.add('_');   symbols.add('~');   symbols.add('%');

        HashSet<Character> comparisonOperator = new HashSet<>();
        comparisonOperator.add('>');    comparisonOperator.add('<'); comparisonOperator.add('=');

        HashSet <Character> separateOperator = new HashSet<>();
        separateOperator.add('{');  separateOperator.add(')');  separateOperator.add('.');
        separateOperator.add('}');  separateOperator.add(']');  separateOperator.add(';');
        separateOperator.add('(');  separateOperator.add('[');  separateOperator.add(',');

        try {
            File file = new File("D:\\tests\\file1.txt");
            //создаем объект FileReader для объекта File
            FileReader fr = new FileReader(file);
            FileWriter nFile = new FileWriter("D:\\tests\\out.txt");
            nFile.write("line"+"\t");
            nFile.write("column"+"\t");
            nFile.write("token"+"\t");
            nFile.write("type"+"\n");
            BufferedReader reader = new BufferedReader(fr);
            int linenum=-1;
            String line = reader.readLine();
            while (line != null) {
                 linenum++;
                 //System.out.println(linenum);
                 Lexer lexer = new Lexer(line, linenum );
                 lexer.getKeyWords(keyWords);
                 lexer.getComparison(comparisonOperator);
                 lexer.getSeparateOperator(separateOperator);
                 lexer.getArithmeticOperator(arithmeticOperator);
                 lexer.getSymbols(symbols);
                 while (true){
                    Token t = lexer.next();
                    if (t == null)
                        break;
                     String token="";
                     String column="";
                     String type="";
                     String  linepos="";
                     token=t.getToken();
                     column=String.valueOf(t.getColumn());
                     linepos=String.valueOf(t.getLinepos());
                     type=t.getType();
                     nFile.write(linepos+"\t");
                     nFile.write(column+"\t");
                     nFile.write(token+"\t");
                     nFile.write(type+"\n");
                     //nFile.append("\n");
                     t.print();
                 }
                    //System.out.println(line);
                line = reader.readLine();

                    // считываем остальные строки в цикле
                //line = reader.readLine();
            }

        fr.close();
        nFile.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
