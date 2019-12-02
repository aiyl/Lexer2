public class MyHashSet {
    java.util.HashSet<String> keyWords = new java.util.HashSet<>();
    public void setKeyWords(java.util.HashSet<String> keyWords) {
        this.keyWords = keyWords;
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
    }
    java.util.HashSet<Character> symbols = new java.util.HashSet<>();
    public void setSymbols(java.util.HashSet<Character> symbols) {
        this.symbols = symbols;
        symbols.add('>');   symbols.add('#');   symbols.add('$');
        symbols.add('<');   symbols.add('=');   symbols.add('&');   symbols.add('@');
        symbols.add('^');   symbols.add('_');   symbols.add('~');   symbols.add('%');
    }
    java.util.HashSet<Character> arithmeticOperator = new java.util.HashSet<Character>();

    public java.util.HashSet<Character> setArithmeticOperator() {
        arithmeticOperator.add('*');    arithmeticOperator.add('/');
        arithmeticOperator.add('-');    arithmeticOperator.add('+');
        return arithmeticOperator;
    }

    public java.util.HashSet<Character> getArithmeticOperator() {
        return arithmeticOperator;
    }

    public java.util.HashSet<Character> getSymbols() {
        return symbols;
    }

    public java.util.HashSet<String> getKeyWords() {
        return keyWords;
    }
}
