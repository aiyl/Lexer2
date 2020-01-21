import java.io.*;
public class Main {
    private static void runLexer(String inPath, String outPath, String expectedPath) {
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
            Lexer lexer = new Lexer(str);
            /*while (true) {
                Token t = lexer.next();
                arrayTokens.add(t);
              //  Parser parser = new Parser(t);
              //  parser.getToken();
                if (t.type==TokenType.EOF)
                    break;
                fw.write(String.format("%s\t%s\t%s\t%s\r\n", String.valueOf(t.getLinepos()), String.valueOf(t.getColumn()), t.getToken(), t.getType()));
               //t.print();
            }
            System.out.print("file number "+inPath + " ");
             */
            fr.close();
            fw.close();
            Parser parser = new Parser(lexer);
            parser.ParseExpression().print(1);
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
        /*while (i <= 51) {
            inPath=args[0]+"in"+String.valueOf(i)+".txt";
            outPath=args[0]+"out"+String.valueOf(i)+".txt";
            expectedPath=args[0]+"expected"+String.valueOf(i)+".txt";
            runLexer(inPath,outPath, expectedPath);
            if(compare(expectedPath,outPath))
                System.out.println(" success ");
            else
                System.out.println(" failed ");
            i++;

        }*/
        runLexer("D:\\\\tests\\\\in53.txt" ,"D:\\\\tests\\\\out53.txt", "D:\\\\tests\\\\expected53.txt");
    }


}
