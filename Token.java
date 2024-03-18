/**
 * The basic unit of our language.
 * @author Gil-Ad Shay.
 */
class Token {
    enum Type {
        INT, 
        FLOAT,

        PLUS, 
        MIN, 
        MUL, 
        DIV, 
        LPAREN, 
        RPAREN
    }

    private final Type type;

    /**
     * Initialize new Token with given type.
     * @param type Given type.
     */
    public Token(Token.Type type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return type.toString();
    }
}