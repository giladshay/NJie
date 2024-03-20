import java.util.Objects;

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

        ASSIGN,

        EQUALS,
        NOT_EQUALS,
        LESS_THAN,
        GREATER_THAN,
        LESS_THAN_OR_EQUALS,
        GREATER_THAN_OR_EQUALS,

        EOF;
    }

    private final Type type;
    private final Position start;
    private final Position end; 

    /**
     * Initialize a new constant token.
     * @param type Type of constant.
     */
    public Token(Token.Type type) {
        this.type = type;
        this.start = null;
        this.end = null;
    }

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
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Token other = (Token) obj;
        return type == other.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(type);
    }
}