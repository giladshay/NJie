import java.util.function.BiPredicate;
import java.util.function.BinaryOperator;
import java.util.function.UnaryOperator;

/**
 * Class for numbers in NJie.
 * @author Gil-Ad Shay.
 */
public class MyNumber {
    private Position start;
    private Position end;
    private Context context;

    private final float value;
    private final boolean isInteger;

    public MyNumber(int value) {
        this.value = value;
        this.isInteger = true;
    }

    public MyNumber(float value) {
        this.value = value;
        this.isInteger = false;
    }

    public MyNumber(MyNumber number) { 
        this.value = number.value;
        this.isInteger = number.isInteger; 
    }

    public Position getStart() {
        return start;
    }

    public Position getEnd() {
        return end;
    }
    
    /**
     * Set the position of the number.
     * @param start Starting position.
     * @param end Ending position.
     */
    public void setPosition(Position start, Position end) {
        this.start = start;
        this.end = end;
    }

    public Context getContext() {
        return context;
    }

    /**
     * Set the context of the number.
     * @param context New context.
     */
    public void setContext(Context context) {
        this.context = context;
    }

    /**
     * Wrap the given number with this instance context.
     * @param number Given number.
     * @return The number wrapped in this context.
     */
    private MyNumber contextWrap(MyNumber number) {
        number.context = this.context;
        return number;
    }

    /**
     * Helper method for all unary operation between numbers.
     * @param n Operand.
     * @param operator Operator.
     * @return Result of the operation on the given operand.
     */
    private static MyNumber unaryOperation(MyNumber n, UnaryOperator<Float> operator) {
        float newValue = operator.apply(n.value);
        return new MyNumber(n.isInteger ? (int) newValue : newValue);
    }

     /**
     * Number negation.
     * @return New number with the value of -this.
     */
    public MyNumber neg() {
        return contextWrap(MyNumber.unaryOperation(this, x -> -x));
    }

    /**
     * Number identity.
     * @return New number with the value of this.
     */
    public MyNumber id() {
        return contextWrap(MyNumber.unaryOperation(this, x -> x));
    }

    /**
     * Helper method for all binary operations between numbers.
     * @param n1 First operand. 
     * @param n2 Second operand.
     * @param operator Operation to apply for float value.
     * @return Result of the operation on the given operands.
     */
    private static MyNumber binaryOpertion(MyNumber n1, MyNumber n2, BinaryOperator<Float> operator) {
        float newValue = operator.apply(n1.value, n2.value);
        int intValue = (int) newValue;
        return n1.isInteger && n2.isInteger ? new MyNumber(intValue) : new MyNumber(newValue);
    }

    /**
     * Number addition.
     * @param other Second operand. 
     * @return New number with the value of this + other.
     */
    public MyNumber add(MyNumber other) { 
        return contextWrap(MyNumber.binaryOpertion(this, other, (x, y) -> x + y));
    }

    /**
     * Number subtraction.
     * @param n1 First operand.
     * @param other Second operand. 
     * @return New number with the value of this - other.
     */
    public MyNumber sub(MyNumber other) { 
        return contextWrap(MyNumber.binaryOpertion(this, other, (x, y) -> x - y));
    }

    /**
     * Number multiplication.
     * @param other Second operand. 
     * @return New number with the value of this * other.
     */
    public MyNumber mul(MyNumber other) { 
        return contextWrap(MyNumber.binaryOpertion(this, other, (x, y) -> x * y)); 
    }

    /**
     * Number Division.
     * @param other Denominator.
     * @return New number with the value of this / denominator.
     * @throws RuntimeError In case of division by zero.
     */
    public MyNumber div(MyNumber other) throws RuntimeError { 
        if (other.value == 0) {
            throw new RuntimeError("Division by zero", other.start, other.end, context);
        }
        return contextWrap(MyNumber.binaryOpertion(this, other, (x, y) -> x / y));
    }

    /**
     * Numer exponentiation.
     * @param other Exponent.
     * @return New number with the value of this ^ other.
     */
    public MyNumber pow(MyNumber other) {
        return contextWrap(MyNumber.binaryOpertion(this, other, (x, y) -> (float) Math.pow(x, y)));
    }

    /**
     * Helper method for comparing numbers.
     * @param n1 first number.
     * @param n2 second number.
     * @param predicate The test function for comparison.
     * @return Number with value 0 if false. Otherwise, Number with value of 1.
     */
    private static MyBoolean compOperator(MyNumber n1, MyNumber n2, BiPredicate<Float, Float> predicate) {
        return predicate.test(n1.value, n2.value) ? new MyBoolean(true) : new MyBoolean(false);
    }

    /**
     * Check if this number equals to another.
     * @param other Other number.
     * @return Number with value of 1 if this == other. Otherwise, number with value of 0.
     */
    public MyNumber eq(MyNumber other) {
        return contextWrap(MyNumber.compOperator(this, other, (x, y) -> x.floatValue() == y.floatValue()));
    } 

    /**
     * Check if this number does not equal to another.
     * @param other Other number.
     * @return Number with value of 1 if this != other. Otherwise, number with value of 0.
     */
    public MyNumber neq(MyNumber other) {
        return contextWrap(MyNumber.compOperator(this, other, (x, y) -> x.floatValue() != y.floatValue()));
    }

    /**
     * Check if this number is less than another.
     * @param other Other number.
     * @return Numebr with value of 1 if this < other. Otherwise, number with value of 0.
     */
    public MyNumber lt(MyNumber other) {
        return contextWrap(MyNumber.compOperator(this, other, (x, y) -> x.floatValue() < y.floatValue()));
    }

    /**
     * Check if this number is less than or equals to another.
     * @param other Other number.
     * @return Number with value of 1 if this <= other. Otherwise, number with value of 0.
     */
    public MyNumber lte(MyNumber other) {
        return contextWrap(MyNumber.compOperator(this, other, (x, y) -> x.floatValue() <= y.floatValue()));
    }

    /**
     * Check if this number is greater than another.
     * @param other Other number.
     * @return Number with value of 1 if this > other. Otherwise, number with value of 0.
     */
    public MyNumber gt(MyNumber other) {
        return contextWrap(MyNumber.compOperator(this, other, (x, y) -> x.floatValue() > y.floatValue()));
    }

    /**
     * Check if this number is greater than or equals to another.
     * @param other Other number.
     * @return Number with value of 1 if this >= other. Otherwise, number with value of 0.
     */
    public MyNumber gte(MyNumber other) {
        return contextWrap(MyNumber.compOperator(this, other, (x, y) -> x.floatValue() >= y.floatValue()));
    }

    public MyNumber copy() {
        MyNumber number = new MyNumber(isInteger ? (int) value : value);
        number.setContext(context);
        number.setPosition(start, end);
        return number;
    }

    @Override
    public String toString() {
        return isInteger ? Integer.toString((int) value) : Float.toString(value);
    }
}
