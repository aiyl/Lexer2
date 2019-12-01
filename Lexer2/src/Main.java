import java.io.*;
import java.util.HashSet;

public class Main {
    public static void main(String[] args) throws Exception {
        /*HashSet<String> keyWords = new HashSet<>();
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
        HashSet <Character> arithmeticOperator = new HashSet<Character>();
        arithmeticOperator.add('*');    arithmeticOperator.add('/');
        arithmeticOperator.add('-');    arithmeticOperator.add('+');
        HashSet <Character> symbols = new HashSet<>();
        symbols.add('>');   symbols.add('#');   symbols.add('$');
        symbols.add('<');   symbols.add('=');   symbols.add('&');   symbols.add('@');
        symbols.add('^');   symbols.add('_');   symbols.add('~');   symbols.add('%');
        */
        try {
            File file = new File("D:\\file.txt");
            //создаем объект FileReader для объекта File
            FileReader fr = new FileReader(file);
            //создаем BufferedReader с существующего FileReader для построчного считывания
            BufferedReader reader = new BufferedReader(fr);
            // считаем сначала первую строку
            String line = reader.readLine();
            while (line != null) {
                 Lexer lexer = new Lexer(line);
                 while (true){
                    Token t = lexer.next();
                    if (t == null)
                        break;
                    t.print();
                }
                    //System.out.println(line);
                line = reader.readLine();
                    // считываем остальные строки в цикле
                //line = reader.readLine();
            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    /*    String str = "g 22   ";
        Lexer lexer = new Lexer(str);
        while (true){
            Token t=lexer.next();
            if(t==null)
                break;
            t.print();
        }*/
    }

}
