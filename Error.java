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

    public Position getStart() {
        return start;
    }

    public String getName() {
        return name;
    }

    public String getDetails() {
        return details;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%s: %s\n", name, details));
        sb.append(String.format(" > File %s, line %d", start.getFN(), start.getLN() + 1));
        sb.append("\n\n" + stringWithArrows());
        return sb.toString();
    }

    /**
     * Print arrows for the error.
     * @return
     */
    protected String stringWithArrows() {
        StringBuilder result = new StringBuilder();

        // Calculate indices
        int startIndex = Math.max(start.getText().indexOf('\n', start.getIdx()), 0);
        int endIndex = details.indexOf('\n', startIndex + 1);
        endIndex = endIndex < 0 ? start.getText().length() : endIndex;

        // Generate each line
        int lineCounter = end.getLN() - start.getLN() + 1;
        for (int i = 0; i < lineCounter; i++) {
            // Calculate line columns
            String line = start.getText().substring(startIndex, endIndex);
            int startCol = i == 0 ? start.getCol() : 0;
            int endCol = i == lineCounter - 1 ? end.getCol() : line.length() - 1;

            // Append to result
            result.append(line + '\n');
            for (int j = 0; j < startCol; j ++)
                result.append(' ');
            for (int j = 0; j < endCol - startCol; j++)
                result.append('^');

            // Recalculate indices
            startIndex = endIndex;
            endIndex = start.getText().indexOf('\n', startIndex + 1);
            endIndex = endIndex < 0 ? details.length() : endIndex;
        }
        return result.toString().replace('\t', '\0');
    }
}
