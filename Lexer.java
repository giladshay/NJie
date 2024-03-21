import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Lexer {
    private static final char NONE = '\0';
    private static final Map<Character, Token.Type> CHARACTER_OPERATORS = new HashMap<>() {{
        put('+', Token.Type.PLUS);
        put('-', Token.Type.MIN);
        put('*', Token.Type.MUL);
        put('/', Token.Type.DIV);
        put('^', Token.Type.POW);
        put('(', Token.Type.LPAREN);
        put(')', Token.Type.RPAREN);
    }};

    private final Map<Character, ThrowableSupplier<Token, ExpectedCharError>> CHARACTER_LOGICAL_COMPARISON = new HashMap<>() {{
        put('!', Lexer.this::makeNotEquals);
        put('=', Lexer.this::makeEquals);
        put('<', Lexer.this::makeLessThan);
        put('>', Lexer.this::makeGreaterThan);
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
        return pos.getIdx() < text.length() ? text.charAt(pos.getIdx()) : NONE;
    }

    /**
     * Tokenize the text into list of tokens.
     * @return List of tokens representing the text of the lexer.
     * @throws IllegalCharError when encountering an undefined character.
     * @throws ExpectedCharError when encountering '!' not followed by '='.
     */
    public List<Token> makeTokens() throws IllegalCharError, ExpectedCharError {
        List<Token> tokens = new ArrayList<>();

        while (getCurrentChar() != NONE) {
            // ignore ' ' and '\t'
            if (getCurrentChar() == ' ' || getCurrentChar() == '\t') {
                advance();
            // +, -, *, /, (, or )
            } else if (CHARACTER_OPERATORS.containsKey(getCurrentChar())) {
                tokens.add(new Token(CHARACTER_OPERATORS.get(getCurrentChar()), pos));
                advance();
            // Numbers
            } else if (Character.isDigit(getCurrentChar())) {
                tokens.add(makeNumber());
            // Error
            } else if (Character.isAlphabetic(getCurrentChar())) {
                tokens.add(makeIdentifierOrKeyword());
            } else if (CHARACTER_LOGICAL_COMPARISON.containsKey(getCurrentChar())) {
                tokens.add(CHARACTER_LOGICAL_COMPARISON.get(getCurrentChar()).get());
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
    private NumericToken makeNumber() {
        StringBuilder number = new StringBuilder();
        boolean isThereDot = false;

        Position start = pos.copy();
        while (getCurrentChar() != NONE && (Character.isDigit(getCurrentChar()) || getCurrentChar() == '.')) {
            if (getCurrentChar() == '.') {
                if (isThereDot)
                    break;
                isThereDot = true;
            }
            number.append(getCurrentChar());
            advance();
        }

        Token.Type type = isThereDot ? Token.Type.FLOAT : Token.Type.INT;
        MyNumber value = isThereDot ? 
            new MyNumber(Float.valueOf(number.toString())):
            new MyNumber(Integer.valueOf(number.toString()));
        return new NumericToken(type, value, start, pos);
    }

    /**
     * Check for alphabetical token and return a keyword token or an identifier token based on whether the input is a keyword.
     * @return Keyword token if it is a keyword. Otherwise, identifier token.
     */
    private Token makeIdentifierOrKeyword() {
        StringBuilder id = new StringBuilder();
        Position start = pos.copy();

        while (getCurrentChar() != NONE && (Character.isAlphabetic(getCurrentChar()) || Character.isDigit(getCurrentChar()))) {
            id.append(getCurrentChar());
            advance();
        }

        String name = id.toString();
        return KeywordToken.KEYWORDS.containsKey(name) ? 
            new KeywordToken(KeywordToken.KEYWORDS.get(name), start, pos) :
            new IdentifierToken(name, start, pos);
    }

    /**
     * Make NOT_EQUALS token.
     * Check if the second character (first character is '!' because function was called) is '='. 
     * @return NOT_EQUALS token if the second character is '='.
     * @throws ExpectedCharError If the second character is not '='.
     */
    private Token makeNotEquals() throws ExpectedCharError {
        Position start = pos.copy();
        advance();

        if (getCurrentChar() == '=') {
            advance();
            return new Token(Token.Type.NOT_EQUALS, start, pos);
        }
        advance();
        throw new ExpectedCharError("'=' (after '!')", start, pos);
    }

    /**
     * Helper method for changing the token type if needed.
     * Used in makeEquals, makeGreaterThan, and makeLessThan.
     * Check if current character equals to given character. If it is, return a token of a changed type. Otherwise, return a token of default character.
     * @param defaultType Type if not changed.
     * @param changed Type if changed.
     * @param isChange Character to make a change.
     * @return Token of type default type or changed type.
     */
    private Token changeTokenTypeIfNeeded(Token.Type defaultType, Token.Type changed, char isChange) {
        Position start = pos.copy();
        advance();
        
        Token.Type type = defaultType;
        if (getCurrentChar() == isChange) {
            advance();
            type = changed;
        }

        return new Token(type, start, pos);
    }

    /**
     * Make either ASSIGN token or EQUALS token.
     * Check if second character (first is '=' because function was called) is also '='.
     * @return ASSIGN token if second character equals '='. Otherwise, EQUALS token.
     */
    private Token makeEquals() {
        return changeTokenTypeIfNeeded(Token.Type.ASSIGN, Token.Type.EQUALS, '=');
    }

    /**
     * Make either LESS_THAN or LESS_THAN_OR_EQUALS token.
     * Check if second character (first is '<' because function was called) is '='.
     * @return LESS_THAN_OR_EQUALS token if second character equals '='. Otherwise, LESS_THAN token.
     */
    private Token makeLessThan() {
        return changeTokenTypeIfNeeded(Token.Type.LESS_THAN, Token.Type.LESS_THAN_OR_EQUALS, '=');
    }

    /**
     * Make either GREATER_THAN or GREATER_THAN_OR_EQUALS token.
     * Check if second character (first is '>' because function was called) is '='.
     * @return GREATER_THAN_OR_EQUALS token if second character equals '='. Otherwise, GREATER_THAN token.
     */
    private Token makeGreaterThan() {
        return changeTokenTypeIfNeeded(Token.Type.GREATER_THAN, Token.Type.GREATER_THAN_OR_EQUALS, '=');
    }
}
