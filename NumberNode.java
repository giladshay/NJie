/**
 * Class for node which represents a number.
 * @author Gil-Ad Shay.
 */
public class NumberNode extends Node {
    private final NumericToken number;

    /**
     * Initialize a new NumberNode with the given number.
     * @param number ValueableToken of type INT or FLOAT.
     */
    public NumberNode(NumericToken number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return number.toString();
    }

    @Override
    public MyNumber visit(Context context) {
        MyNumber result = new MyNumber(number.getValue());
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
