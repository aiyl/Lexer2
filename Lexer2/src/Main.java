import java.io.*;

public class Main {
    public static void main(String[] args) throws Exception {
        int ch;
        String line1 = "";
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
                Token t = lexer.next();
                if (t == null)
                    break;
                t.print();
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
        /*String str = "8959fhhe54 222    lslkzcv";
        Lexer lexer = new Lexer(str);
        while (true){
            Token t=lexer.next();
            if(t==null)
                break;
            t.print();
        }*/
    }

}
