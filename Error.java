/**
 * Superclass for all errors.
 * @see Throwable
 * @author Gil-Ad Shay 
 */
public class Error extends Throwable {
    private final String name;
    private final String details;
    private final Position start;
    private final Position end;

    /**
     * Initialize a new Error with the given name and given details.
     * @param name Error name.
     * @param details Details.
     * @param start Starting position.
     * @param end Ending position.
     */
    public Error(String name, String details, Position start, Position end) {
        this.name = name;
        this.details = details;
        this.start = start;
        this.end = end;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%s: %s\n", name, details));
        sb.append(String.format(">> File %s, line %d", start.getFN(), start.getLN() + 1));
        return sb.toString();
    }
}
