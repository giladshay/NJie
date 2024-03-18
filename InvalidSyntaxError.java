/**
 * Class for invalid syntax error.
 * @author Gil-Ad Shay.
 */
public class InvalidSyntaxError extends Error {
    public InvalidSyntaxError(String details, Position start, Position end) {
        super("Invalid Syntax", details, start, end);
    }
    
}
