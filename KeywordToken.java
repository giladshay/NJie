import java.util.HashMap;
import java.util.Map;

/**
 * Class for tokens of type keywords.
 * @author Gil-Ad Shay.
 */
public class KeywordToken extends Token {
    
    enum Keyword {
        VAR
    }

    public static final Map<String, KeywordToken.Keyword> KEYWORDS = new HashMap<>() {{
        put("VAR", KeywordToken.Keyword.VAR);
    }};

    private final KeywordToken.Keyword keyword;

    /**
     * Initialize new keyword token.
     * @param keyword Keyword of this token.
     * @param start Starting position.
     */
    public KeywordToken(KeywordToken.Keyword keyword, Position start) {
        super(Token.Type.KEYWORD, start);
        this.keyword = keyword;
    }

    /**
     * Initialize new keyword token.
     * @param keyword Keyword of this token.
     * @param start Starting position.
     * @param end Ending position.
     */
    public KeywordToken(KeywordToken.Keyword keyword, Position start, Position end) {
        super(Token.Type.KEYWORD, start, end);
        this.keyword = keyword;
    }

    @Override
    public String toString() {
        return String.format("Keyword:%s", keyword);
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj) && obj instanceof KeywordToken && ((KeywordToken) obj).keyword == keyword;
    }
}
