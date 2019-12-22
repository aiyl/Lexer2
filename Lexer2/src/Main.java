import java.io.*;
import java.util.HashSet;

public class Main {

    private static void runLexer(String inPath, String outPath, String expectedPath) {
        HashSet<String> keyWords = new HashSet<>();
        keyWords.add("begin");
        keyWords.add("const");
        keyWords.add("file");
        keyWords.add("break");
        keyWords.add("end");
        keyWords.add("div");
        keyWords.add("for");
        keyWords.add("continue");
        keyWords.add("array");
        keyWords.add("do");
        keyWords.add("function");
        keyWords.add("writeln");
        keyWords.add("and");
        keyWords.add("downto");
        keyWords.add("goto");
        keyWords.add("write");
        keyWords.add("case");
        keyWords.add("else");
        keyWords.add("to");
        keyWords.add("readln");
        keyWords.add("if");
        keyWords.add("mod");
        keyWords.add("of");
        keyWords.add("read");
        keyWords.add("in");
        keyWords.add("nil");
        keyWords.add("packed");
        keyWords.add("char");
        keyWords.add("label");
        keyWords.add("not");
        keyWords.add("procedure");
        keyWords.add("exit");
        keyWords.add("record");
        keyWords.add("then");
        keyWords.add("var");
        keyWords.add("xor");
        keyWords.add("repeat");
        keyWords.add("type");
        keyWords.add("while");
        keyWords.add("integer");
        keyWords.add("set");
        keyWords.add("until");
        keyWords.add("with");
        keyWords.add("double");

        HashSet<Character> arithmeticOperator = new HashSet<>();
        arithmeticOperator.add('*');
        arithmeticOperator.add('/');
        arithmeticOperator.add('^');
        arithmeticOperator.add('-');
        arithmeticOperator.add('+');
        arithmeticOperator.add('>');
        arithmeticOperator.add('<');
        arithmeticOperator.add('=');
        HashSet<Character> symbols = new HashSet<>();
        symbols.add('#');
        symbols.add('$');
        symbols.add('&');
        symbols.add('@');
        //symbols.add(':');
        symbols.add('_');
        symbols.add('~');
        symbols.add('%');


        HashSet<Character> separateOperator = new HashSet<>();
        separateOperator.add(')');
        separateOperator.add('.');
        separateOperator.add(']');
        separateOperator.add(';');
        separateOperator.add('(');
        separateOperator.add('[');
        separateOperator.add(',');
        separateOperator.add(':');

        HashSet<String> doubleOperators = new HashSet<>();
        doubleOperators.add("\"\"");
        doubleOperators.add("<=");
        doubleOperators.add(">=");
        doubleOperators.add("<>");

        try {
            File file = new File(inPath);
            FileReader fr = new FileReader(file);
            FileWriter fw = new FileWriter(outPath);
            fw.write("line" + "\t");
            fw.write("column" + "\t");
            fw.write("token" + "\t");
            fw.write("type" + "\r"+"\n");
            BufferedReader reader = new BufferedReader(fr);
            String str = "";
            int line;
            while ((line = reader.read()) != -1) {
                str += (String.valueOf((char) line));

            }
            //System.out.println(linenum);
            Lexer lexer = new Lexer(str);
            lexer.getKeyWords(keyWords);
            lexer.getSeparateOperator(separateOperator);
            lexer.getArithmeticOperator(arithmeticOperator);
            lexer.getSymbols(symbols);
            lexer.getDoubleOperators(doubleOperators);
            while (true) {
                Token t = lexer.next();
                if (t == null)
                    break;
                fw.write(String.format("%s\t%s\t%s\t%s\r\n", String.valueOf(t.getLinepos()), String.valueOf(t.getColumn()), t.getToken(), t.getType()));
               //t.print();
            }
            System.out.print("file number "+inPath + " ");
            fr.close();
            fw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static boolean compare(String a, String b) throws Exception {
        String a1=a;
        String b2=b;
        FileReader readerA = new FileReader(a1);
        FileReader readerB = new FileReader(b2);
        int byteA;
        int byteB;
        String str1="";
        String str2="";
        while ((byteA = readerA.read()) != -1) {
            str1 += (String.valueOf((char) byteA));

        }
        readerA.close();
        while ((byteB = readerB.read()) != -1) {
            str2 += (String.valueOf((char) byteB));

        }
        readerB.close();
        if (str1.equals(str2))
                return true;
        else
            return false;

    }

    public static void main(String[] args) throws Exception {
        String inPath="";
        String outPath="";
        String expectedPath="";
        int i = 1;
        while (i <= 51) {
            inPath=args[0]+"in"+String.valueOf(i)+".txt";
            outPath=args[0]+"out"+String.valueOf(i)+".txt";
            expectedPath=args[0]+"expected"+String.valueOf(i)+".txt";
            runLexer(inPath,outPath, expectedPath);
            if(compare(expectedPath,outPath))
                System.out.println(" success ");
            else
                System.out.println(" failed ");
            i++;

        }
        //runLexer("D:\\\\tests\\\\in49.txt" ,"D:\\\\tests\\\\out49.txt", "D:\\\\tests\\\\expected49.txt");
    }


}
