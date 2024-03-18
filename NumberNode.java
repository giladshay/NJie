/**
 * Class for node which represents a number.
 * @author Gil-Ad Shay.
 */
public class NumberNode extends Node {
    private final Token number;

    /**
     * Initialize a new NumberNode with the given number.
     * @param number ValueableToken of type INT or FLOAT.
     */
    public NumberNode(ValueableToken number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return number.toString();
    }
}
