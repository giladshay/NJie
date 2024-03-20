/**
 * Class for node which represents a number.
 * @author Gil-Ad Shay.
 */
public class NumberNode extends Node {
    private final ValueableToken number;

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

    @Override
    public Number visit(Context context) {
        Number result = new Number(number.getValue());
        result.setPosition(getStart(), getEnd());
        result.setContext(context);
        return result;
    }

    @Override
    public Position getStart() {
        return number.getStart();
    }

    @Override
    public Position getEnd() {
        return number.getEnd();
    }
}
