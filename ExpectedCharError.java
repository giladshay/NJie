/**
 * Class for expected characters.
 * @author Gil-Ad Shay.
 */
public class ExpectedCharError extends Error {

    public ExpectedCharError(String details, Position start, Position end) {
        super("Expected Character", details, start, end);
    }
    
}
