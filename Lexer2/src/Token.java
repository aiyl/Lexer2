import java.io.FileWriter;
import java.io.IOException;

public class Token {
    int linepos, column;
    String type,token;
public Token(int linepos, int column, String type, String token){
    this.linepos=linepos;
    this.column = column;
    this.type=type;
    this.token=token;
    }

    public int getLinepos() {
        return linepos;
    }

    public String getToken() {
        return token;
    }

    public int getColumn() {
        return column;
    }

    public String getType() {
        return type;
    }

    void print(){
    System.out.print(linepos+" ");
    System.out.print(column+" ");
    System.out.print(token+" ");
    System.out.println(type+" ");

}

}
