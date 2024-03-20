/**
 * Class for errors occur during runtime of the program.
 * @author Gil-Ad Shay.
 */
public class RuntimeError extends Error {

    private final Context context;
    /**
     * Initialize a new Runtime Error with the given details at the given position.
     * @param details Details.
     * @param start Starting position.
     * @param end Ending position.
     * @param context Context of the runtime error.
     */
    public RuntimeError(String details, Position start, Position end, Context context) {
        super("Runtime Error", details, start, end);
        this.context = context;
    }
    
    /**
     * Generate the traceback of the error.
     * @return String for the traceback of the error.
     */
    private String generateTraceback() {
        StringBuilder traceback = new StringBuilder();
        Position position = getStart();
        Context context = this.context;

        while (context != null) {
            traceback.insert(0, String.format(" > File %s, line %d, in %s\n", position.getFN(), position.getLN() + 1, context.getDisplayName()));
            position = context.getParentEntry();
            context = context.getParent();
        }

        traceback.insert(0, "Traceback (most recent call last):\n");
        return traceback.toString();
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(generateTraceback());
        sb.append(String.format("%s: %s\n", getName(), getDetails()));
        sb.append("\n\n" + stringWithArrows());
        return sb.toString();
    }
}
