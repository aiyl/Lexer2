import java.util.ArrayList;

public class Parser {
    int i=0;
    Token token;
    String curToken;
    TokenType curTokenType;
    ArrayList<Token> arrayList;
    public Parser(ArrayList tokensArray){
        this.arrayList=tokensArray;
        System.out.println("PlusMinus" + Parse(String.valueOf(arrayList.get(0).token)));
        //this.token=token;
    }
    String getToken(){
        token=arrayList.get(i);
        curToken=String.valueOf(token.token);
        curTokenType=token.type;
        return curToken;

    }
    public double Parse(String curToken){
        Result result = PlusMinus(curToken);
        if(arrayList==null)
            System.out.println("Error"+curToken);
        return result.acc;
    }
    private Result PlusMinus(String curToken){
        Result current=Num(curToken);
        double acc = current.acc;
        while (true){
            if((curToken!="+")||(curToken!="-"))
                break;
            String sign = curToken;
            i++;
          //  getToken();
            String nextToken=getToken();
            acc = current.acc;
            current=Num(nextToken);
            if(sign=="+"){
                acc+=current.acc;
            }
            else{
                acc-=acc;
            }
            current.acc=acc;
        }
        return new Result(current.acc,curToken);
    }
    private Result Num(String curToken){
        if ((curTokenType!=TokenType.DOUBLE)||(curTokenType!=TokenType.INTEGER)) {
            System.out.println("Can't find digit");
            //return null;
        }
        Double value = Double.valueOf(curToken);
        String restPart = curToken;
        return new Result(value, curToken);
    }


    //Tokens tokens= new Tokens(t);

}
