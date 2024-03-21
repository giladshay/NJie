import java.util.function.BinaryOperator;
import java.util.function.UnaryOperator;

public class MyBoolean extends MyNumber {

    private final boolean value;

    public MyBoolean(boolean value) {
        super(value ? 1 : 0);
        this.value = value;
    }

    /**
     * Wrap the given number with this instance context.
     * @param number Given number.
     * @return The number wrapped in this context.
     */
    private MyBoolean contextWrap(MyBoolean bool) {
        bool.setContext(getContext()); 
        return bool;
    }

    /**
     * Helper method for logic binary operations.
     * @param b1 First number.
     * @param b2 Second number.
     * @param operator Binary operator to apply.
     * @return Number of value 1 if the result of the given operator is true. Otherwise, number of value 0.
     */
    private static MyBoolean logicBinaryOperation(MyBoolean b1, MyBoolean b2, BinaryOperator<Boolean> operator) {
        return operator.apply(b1.value, b2.value) ? new MyBoolean(true) : new MyBoolean(false);
    }

    /**
     * Get this & other where number is considered to be TRUE if its value is not 0.
     * @param other Other number.
     * @return Number of value 1 if both this and other values are not 0. Otherwise, number of value 0.
     */
    public MyBoolean and(MyBoolean other) {
        return contextWrap(logicBinaryOperation(this, other, (x, y) -> x.booleanValue() && y.booleanValue()));
    }

    /**
     * Get this | other where number is considered to be TRUE if its value is not 0.
     * @param other Other number.
     * @return Number of value 1 if this value is not 0 or other value is not 0. Otherwise, number of value 0.
     */
    public MyBoolean or(MyBoolean other) {
        return contextWrap(logicBinaryOperation(this, other, (x, y) -> x.booleanValue() || y.booleanValue()));
    }

    /**
     * Helper method for logic unary operations.
     * @param n Number.
     * @param operator Unary operator to apply.
     * @return Number of value 1 if the result of the given operator is true. Otherwise, number of value 0.
     */
    private static MyBoolean logicUnaryOperation(MyBoolean n, UnaryOperator<Boolean> operator) {
        return operator.apply(n.value) ? new MyBoolean(true) : new MyBoolean(false);
    }

    /**
     * Return the logical negation of this number.
     * @param other Other number.
     * @return Number of value 0 if this value is not 0. Otherwise, number of value 1.
     */
    public MyBoolean not() {
        return contextWrap(logicUnaryOperation(this, x -> !x));
    }

    public MyBoolean copy() {
        MyBoolean bool = new MyBoolean(value);
        bool.setContext(getContext());
        bool.setPosition(getStart(), getEnd());
        return bool;
    }

    @Override
    public String toString() {
        return value ? "TRUE" : "FALSE";
    }

}
