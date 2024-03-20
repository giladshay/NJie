import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Lexer {
    private static final char none = '\0';
    private static final Map<Character, Token.Type> charOperators = new HashMap<>() {{
        put('+', Token.Type.PLUS);
        put('-', Token.Type.MIN);
        put('*', Token.Type.MUL);
        put('/', Token.Type.DIV);
        put('^', Token.Type.POW);
        put('(', Token.Type.LPAREN);
        put(')', Token.Type.RPAREN);
    }};
    private final String fn, text;
    private final Position pos;

    /**
     * Initialize a new Lexer with the given text.
     * @param text Given text.
     */
    public Lexer(String fn, String text) {
        this.text = text;
        this.fn = fn; 
        this.pos = new Position(fn, text);
    }

    /**
     * Advance this position.
     */
    private void advance() {
        pos.advance();;
    }

    /**
     * Get the current character in the text of the lexer.
     * If out of bounds, return none.
     * @return Current character in the text.
     */
    private char getCurrentChar() {
        return pos.getIdx() < text.length() ? text.charAt(pos.getIdx()) : none;
    }

    /**
     * Tokenize the text into list of tokens.
     * @return List of tokens representing the text of the lexer.
     * @throws IllegalCharError when encountering an undefined character.
     */
    public List<Token> makeTokens() throws IllegalCharError {
        List<Token> tokens = new ArrayList<>();

        while (getCurrentChar() != none) {
            // ignore ' ' and '\t'
            if (getCurrentChar() == ' ' || getCurrentChar() == '\t') {
                advance();
            // +, -, *, /, (, or )
            } else if (charOperators.containsKey(getCurrentChar())) {
                tokens.add(new Token(charOperators.get(getCurrentChar()), pos));
                advance();
            // Numbers
            } else if (Character.isDigit(getCurrentChar())) {
                tokens.add(makeNumber());
            // Error
            } else {
                Position start = pos.copy();
                char c = getCurrentChar();
                advance();
                throw new IllegalCharError("" + "'" + c + "'", start, pos);
            }
        }
        tokens.add(new Token(Token.Type.EOF, pos));
        return tokens;
    }

    /**
     * Create a valueble (numeric) token from a sequence of digits.
     * Check if it is a float or an integer and return matching token.
     * @return ValueableToken representing sequence of digits and '.'. If the number is float, return ValueableToken of type FLOAT, and otherwise of type INT.
     */
    private ValueableToken makeNumber() {
        StringBuilder number = new StringBuilder();
        boolean isThereDot = false;

        Position start = pos.copy();
        while (getCurrentChar() != none && (Character.isDigit(getCurrentChar()) || getCurrentChar() == '.')) {
            if (getCurrentChar() == '.') {
                if (isThereDot)
                    break;
                isThereDot = true;
            }
            number.append(getCurrentChar());
            advance();
        }

        Token.Type type = isThereDot ? Token.Type.FLOAT : Token.Type.INT;
        ValueableToken.Value value = isThereDot ? 
            new ValueableToken.Value(Float.valueOf(number.toString())) :
            new ValueableToken.Value(Integer.valueOf(number.toString()));
        return new ValueableToken(type, value, start, pos);
    }
}
