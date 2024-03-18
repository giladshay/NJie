/**
 * Class for throwing illegal character error.
 * @author Gil-Ad Shay.
 */
public class IllegalCharError extends Error {

    /**
     * Initialize new Illegal Character Error with the given details.
     * @param details Details.
     * @param start Starting position.
     * @param end Ending position.
     */
    public IllegalCharError(String details, Position start, Position end) {
        super("Illegal Character", details, start, end);
    }
    
}
