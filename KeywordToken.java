import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Class for tokens of type keywords.
 * @author Gil-Ad Shay.
 */
public class KeywordToken extends Token {
    
    enum Keyword {
        VAR, 
        AND,
        OR,
        NOT
    }

    public static final Map<String, Keyword> KEYWORDS = new HashMap<>() {{
        put("VAR", Keyword.VAR);
        put("AND", Keyword.AND);
        put("OR", Keyword.OR);
        put("NOT", Keyword.NOT);
    }};

    private final Keyword keyword;

    /**
     * Initialize constant keyword token.
     * @param keyword Keyword.
     */
    public KeywordToken(Keyword keyword) {
        super(Token.Type.KEYWORD);
        this.keyword = keyword;
    }

    /**
     * Initialize new keyword token.
     * @param keyword Keyword of this token.
     * @param start Starting position.
     */
    public KeywordToken(Keyword keyword, Position start) {
        super(Token.Type.KEYWORD, start);
        this.keyword = keyword;
    }

    /**
     * Initialize new keyword token.
     * @param keyword Keyword of this token.
     * @param start Starting position.
     * @param end Ending position.
     */
    public KeywordToken(Keyword keyword, Position start, Position end) {
        super(Token.Type.KEYWORD, start, end);
        this.keyword = keyword;
    }

    public Keyword getKeyword() {
        return keyword;
    }
    
    @Override
    public String toString() {
        return String.format("Keyword:%s", keyword);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        KeywordToken keywordToken = (KeywordToken) obj;
        return super.equals(keywordToken) && keyword == keywordToken.keyword;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), keyword);
    }
}
