import java.io.*;
public class Main {
    private static void runLexer(String inPath, String outPath, String expectedPath, boolean testType) {
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
            if (!testType){
                while (true) {
                    Token t = lexer.next();
                    if (t.type==TokenType.EOF)
                        break;
                    fw.write(String.format("%s\t%s\t%s\t%s\r\n", String.valueOf(t.getLinepos()), String.valueOf(t.getColumn()), t.getToken(), t.getType()));
                   //t.print();
                }
                System.out.print("file number "+inPath + " ");

                fr.close();
                fw.close();
            }
            if(testType){

            Parser parser = new Parser(lexer);
               // parser.ParseIndentList().print(1);
            //parser.ParseExpression().print(1);
               // parser.ParseSetValue().print(1);
           // parser.ParseAssignment().print(1); //Временно!!!!
             //   parser.ParseIfStatement().print(1);
               // parser.ParseForStatement().print(1);
               // parser.ParseWhileStatement().print(1);
             //   parser.ParseRepeatStatement().print(1);
                parser.ParseStatement().print(1);
                //parser.ParseProcedureCall().print(1);
            }
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
        boolean testType=false;
        int i = 1;
        String path="";
        if (args[1].contains("L"))
            path=args[0]+"\\\\LexerTests\\\\";
        if (args[1].contains("P")){
            path=args[0]+"\\\\ParserTests\\\\";
            testType=true;
        }
        if(args[1].contains("L")){
        while (i <= 51) {
            inPath=path+"in"+String.valueOf(i)+".txt";
            outPath=path+"out"+String.valueOf(i)+".txt";
            expectedPath=path+"expected"+String.valueOf(i)+".txt";
            runLexer(inPath,outPath, expectedPath, testType);
            if(compare(expectedPath,outPath))
                System.out.println(" success ");
            else
                System.out.println(" failed ");
            i++;

        }
        }

       if(testType){
           i=16;
           while (i <= 32) {
               inPath=path+"in"+String.valueOf(i)+".txt";
               outPath=path+"out"+String.valueOf(i)+".txt";
               expectedPath=path+"expected"+String.valueOf(i)+".txt";
               System.out.println(inPath+"_____________________________");
               runLexer(inPath,outPath, expectedPath, testType);
               i++;
           }
       }
        runLexer(path+"in32.txt" ,"D:\\\\tests\\\\ParserTests\\\\out27.txt", "D:\\\\tests\\\\ParserTests\\\\expected18.txt", testType);
    }
}
