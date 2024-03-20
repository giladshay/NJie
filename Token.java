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
        POW, 
        LPAREN, 
        RPAREN, 

        IDENTIFIER, 
        KEYWORD,

        EQ,

        EOF;
    }

    private final Type type;
    private final Position start;
    private final Position end; 

    /**
     * Initialize new Token with given type.
     * @param type Given type.
     * @param start Starting position.
     * @param end Ending position.
     */
    public Token(Token.Type type, Position start, Position end) {
        this.type = type;
        this.start = start.copy();
        this.end = end;
    }

    /**
     * Initialize a new Token with given type.
     * Ending position is starting position + 1.
     * @param type Given type.
     * @param start Starting position.
     */
    public Token(Token.Type type, Position start) {
        this.type = type;
        if (start != null) {
            this.start = start.copy();
            Position temp = start.copy();
            temp.advance();
            this.end = temp.copy();
        } else {
            this.start = null;
            this.end = null;
        }
    }

    public Type getType() {
        return type;
    }
    public Position getStart() {
        return start;
    }
    public Position getEnd() {
        return end;
    }

    @Override
    public String toString() {
        return type.toString();
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Token && ((Token) obj).type == type;
    }
}