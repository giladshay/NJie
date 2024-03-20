/**
 * Class for tracking position of everything in the program.
 * The reason we want to track everything, is to ease finding the position where an error occured.
 * @author Gil-Ad Shay.
 */
public class Position {
    private int idx, ln, col;
    private final String fn, ftxt;
    /**
     * 
     */
    public Position(String fn, String ftxt) {
        this.idx = 0;
        this.col = 0;
        this.ln = 0;
        this.fn = fn;
        this.ftxt = ftxt;
    }

    /**
     * Advance this idx by 1.
     * Adjust columns and line if needed.
     */
    public void advance() {
        idx ++;
        col ++;

        if (idx < ftxt.length() && ftxt.charAt(idx) == '\n') {
            col = 0;
            ln ++;
        }
    }

    public int getIdx() { return idx; }
    public int getLN() { return ln; }
    public String getFN() {return fn; }
    public String getText() {  return ftxt; }
    public int getCol() {
        return col;
    }

    public Position copy() {
        Position copy = new Position(fn, ftxt);
        copy.idx = idx;
        copy.col = col;
        copy.ln = ln;
        return copy;
    }
}
