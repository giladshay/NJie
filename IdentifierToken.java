/**
 * Class for identifier token.
 * @author Gil-Ad Shay.
 */
public class IdentifierToken extends Token {

    private final String name;

    /**
     * Initialize new identifier token.
     * @param name Name of the identifier.
     * @param start Starting position.
     */
    public IdentifierToken(String name, Position start) {
        super(Token.Type.IDENTIFIER, start);
        this.name = name;
    }

    /**
     * Initialize new identifier token.
     * @param name Name of the identifier.
     * @param start Starting position.
     * @param end Ending position.
     */
    public IdentifierToken(String name, Position start, Position end) {
        super(Token.Type.IDENTIFIER, start, end);
        this.name = name;
    }

    public String getName() {
        return name;
    }
    
    @Override
    public String toString() {
        return String.format("Identifier:%s", name);
    }
}
