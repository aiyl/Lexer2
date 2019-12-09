import java.io.*;
import java.util.HashSet;

public class Main {
    String string;
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
        arithmeticOperator.add('>');    arithmeticOperator.add('<');    arithmeticOperator.add('=');
        HashSet <Character> symbols = new HashSet<>();
        symbols.add('#');   symbols.add('$');
        symbols.add('&');   symbols.add('@');   symbols.add(':');
        symbols.add('_');   symbols.add('~');   symbols.add('%');




        HashSet <Character> separateOperator = new HashSet<>();
        separateOperator.add('{');  separateOperator.add(')');  separateOperator.add('.');
        separateOperator.add('}');  separateOperator.add(']');  separateOperator.add(';');
        separateOperator.add('(');  separateOperator.add('[');  separateOperator.add(',');

        HashSet <String> doubleOperators = new HashSet<>();
        doubleOperators.add("\"\"");    doubleOperators.add("<=");
        doubleOperators.add(">=");      doubleOperators.add("<>");

        try {
            File file = new File("D:\\tests\\in1.txt");
            //создаем объект FileReader для объекта File
            FileReader fr = new FileReader(file);
            FileWriter nFile = new FileWriter("D:\\tests\\out1.txt");
            nFile.write("line"+"\t");
            nFile.write("column"+"\t");
            nFile.write("token"+"\t");
            nFile.write("type"+"\n");
            BufferedReader reader = new BufferedReader(fr);
            String str="";
            int line;
            //str=this.string;
            while ((line = reader.read()) !=-1) {
                str +=(String.valueOf((char)line));

            }
            //System.out.println(linenum);
            Lexer lexer = new Lexer(str);
            lexer.getKeyWords(keyWords);
            lexer.getSeparateOperator(separateOperator);
            lexer.getArithmeticOperator(arithmeticOperator);
            lexer.getSymbols(symbols);
            lexer.getDoubleOperators(doubleOperators);
            while (true){
                Token t = lexer.next();
                if (t == null)
                    break;
                nFile.write(String.format("%s\t%s\t%s\t%s\n", String.valueOf(t.getLinepos()),String.valueOf(t.getColumn()), t.getToken(), t.getType()));
                t.print();
            }
                //line = reader.readLine();


        fr.close();
        nFile.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
