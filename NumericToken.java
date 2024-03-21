import java.util.Objects;

/**
 * INT and FLOAT Token.
 * @see Token
 * @see Number
 * @author Gil-Ad Shay. 
 */
public class NumericToken extends Token {

    private final MyNumber number;

    /**
     * Initialize a new ValueableToken with the given type and given value.
     * @param type Given type.
     * @param value Given value. 
     * @param start Starting position.
     * @param end Ending position.
     */
    public NumericToken(Token.Type type, MyNumber value, Position start, Position end) {
        super(type, start, end);
        this.number = value;
    }

    /**
     * Initialize a new ValueableToken with the given type and value.
     * Ending position is starting position + 1.
     * @param type Given type.
     * @param value Given value.
     * @param start Starting position.
     */
    public NumericToken(Token.Type type, MyNumber value, Position start) {
        super(type, start);
        this.number = value;
    }

    public MyNumber getValue() { return number; }
    
    @Override
    public String toString() {
        return String.format("%s:%s", super.toString(), this.number.toString());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        NumericToken token = (NumericToken) obj;
        return super.equals(token) && number == token.number;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), number.hashCode());
    }
}
