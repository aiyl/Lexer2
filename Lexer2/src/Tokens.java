import java.util.ArrayList;

public class Tokens {
    Token token;
    ArrayList<Object> tokenList = new ArrayList<>();
    public Tokens(Token token){
        this.token=token;
        tokenList.add(token);
    }

    public ArrayList<Object> getTokenList() {
        return tokenList;
    }
}
