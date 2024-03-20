import java.util.function.BiPredicate;
import java.util.function.BinaryOperator;
import java.util.function.UnaryOperator;

/**
 * Wrapper class for value to be able to use mathemtical operation on it.
 * Wrap the numeric value with position and context to track errors.
 * @author Gil-Ad Shay.
 */
public class Number extends ValueableToken.Value {

    private Position start;
    private Position end;
    private Context context;

    public static final int FALSE = 0;
    public static final int TRUE = 1;

    public Number(int value) {
        super(value);
    }

    public Number(float value) {
        super(value);
    }

    public Number(ValueableToken.Value value) {
        super(value); 
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
    private Number contextWrap(Number number) {
        number.context = this.context;
        return number;
    }

    /**
     * Helper method for all unary operation between numbers.
     * @param n Operand.
     * @param operator Operator.
     * @return Result of the operation on the given operand.
     */
    private static Number unaryOperation(Number n, UnaryOperator<Float> operator) {
        float newValue = operator.apply(n.getValue());
        if (n.isInteger())
            return new Number(new ValueableToken.Value((int) newValue));
        return new Number(new ValueableToken.Value(newValue));
    }

     /**
     * Number negation.
     * @return New number with the value of -this.
     */
    public Number neg() {
        return contextWrap(Number.unaryOperation(this, x -> -x));
    }

    /**
     * Number identity.
     * @return New number with the value of this.
     */
    public Number id() {
        return contextWrap(Number.unaryOperation(this, x -> x));
    }

    /**
     * Helper method for all binary operations between numbers.
     * @param n1 First operand. 
     * @param n2 Second operand.
     * @param operator Operation to apply for float value.
     * @return Result of the operation on the given operands.
     */
    private static Number binaryOpertion(Number n1, Number n2, BinaryOperator<Float> operator) {
        float newFloatValue = operator.apply(n1.getValue(), n2.getValue());
        ValueableToken.Value newValue = n1.isInteger() && n2.isInteger() ?
                new ValueableToken.Value((int) newFloatValue) :
                new ValueableToken.Value(newFloatValue);
        return new Number(newValue);
    }

    /**
     * Number addition.
     * @param other Second operand. 
     * @return New number with the value of this + other.
     */
    public Number add(Number other) { 
        return contextWrap(Number.binaryOpertion(this, other, (x, y) -> x + y));
    }

    /**
     * Number subtraction.
     * @param n1 First operand.
     * @param other Second operand. 
     * @return New number with the value of this - other.
     */
    public Number sub(Number other) { 
        return contextWrap(Number.binaryOpertion(this, other, (x, y) -> x - y));
    }

    /**
     * Number multiplication.
     * @param other Second operand. 
     * @return New number with the value of this * other.
     */
    public Number mul(Number other) { 
        return contextWrap(Number.binaryOpertion(this, other, (x, y) -> x * y)); 
    }

    /**
     * Number Division.
     * @param other Denominator.
     * @return New number with the value of this / denominator.
     * @throws RuntimeError In case of division by zero.
     */
    public Number div(Number other) throws RuntimeError { 
        if (other.getValue() == 0) {
            throw new RuntimeError("Division by zero", other.start, other.end, context);
        }
        return contextWrap(Number.binaryOpertion(this, other, (x, y) -> x / y));
    }

    /**
     * Numer exponentiation.
     * @param other Exponent.
     * @return New number with the value of this ^ other.
     */
    public Number pow(Number other) {
        return contextWrap(Number.binaryOpertion(this, other, (x, y) -> (float) Math.pow(x, y)));
    }

    /**
     * Helper method for comparing numbers.
     * @param n1 first number.
     * @param n2 second number.
     * @param predicate The test function for comparison.
     * @return Number with value 0 if false. Otherwise, Number with value of 1.
     */
    private static Number compOperator(Number n1, Number n2, BiPredicate<Float, Float> predicate) {
        return predicate.test(n1.getValue(), n2.getValue()) ? new Number(TRUE) : new Number(FALSE);
    }

    /**
     * Check if this number equals to another.
     * @param other Other number.
     * @return Number with value of 1 if this == other. Otherwise, number with value of 0.
     */
    public Number eq(Number other) {
        return contextWrap(Number.compOperator(this, other, (x, y) -> x.floatValue() == y.floatValue()));
    } 

    /**
     * Check if this number does not equal to another.
     * @param other Other number.
     * @return Number with value of 1 if this != other. Otherwise, number with value of 0.
     */
    public Number neq(Number other) {
        return contextWrap(Number.compOperator(this, other, (x, y) -> x.floatValue() != y.floatValue()));
    }

    /**
     * Check if this number is less than another.
     * @param other Other number.
     * @return Numebr with value of 1 if this < other. Otherwise, number with value of 0.
     */
    public Number lt(Number other) {
        return contextWrap(Number.compOperator(this, other, (x, y) -> x.floatValue() < y.floatValue()));
    }

    /**
     * Check if this number is less than or equals to another.
     * @param other Other number.
     * @return Number with value of 1 if this <= other. Otherwise, number with value of 0.
     */
    public Number lte(Number other) {
        return contextWrap(Number.compOperator(this, other, (x, y) -> x.floatValue() <= y.floatValue()));
    }

    /**
     * Check if this number is greater than another.
     * @param other Other number.
     * @return Number with value of 1 if this > other. Otherwise, number with value of 0.
     */
    public Number gt(Number other) {
        return contextWrap(Number.compOperator(this, other, (x, y) -> x.floatValue() > y.floatValue()));
    }

    /**
     * Check if this number is greater than or equals to another.
     * @param other Other number.
     * @return Number with value of 1 if this >= other. Otherwise, number with value of 0.
     */
    public Number gte(Number other) {
        return contextWrap(Number.compOperator(this, other, (x, y) -> x.floatValue() >= y.floatValue()));
    }

    /**
     * Helper method for logic binary operations.
     * @param n1 First number.
     * @param n2 Second number.
     * @param operator Binary operator to apply.
     * @return Number of value 1 if the result of the given operator is true. Otherwise, number of value 0.
     */
    private static Number logicBinaryOperation(Number n1, Number n2, BinaryOperator<Boolean> operator) {
        boolean isTrue = operator.apply(n1.getValue() != FALSE, n2.getValue() != FALSE);
        return isTrue ? new Number(TRUE) : new Number(FALSE);
    }
    
    /**
     * Get this & other where number is considered to be TRUE if its value is not 0.
     * @param other Other number.
     * @return Number of value 1 if both this and other values are not 0. Otherwise, number of value 0.
     */
    public Number and(Number other) {
        return contextWrap(logicBinaryOperation(this, other, (x, y) -> x.booleanValue() && y.booleanValue()));
    }

    /**
     * Get this | other where number is considered to be TRUE if its value is not 0.
     * @param other Other number.
     * @return Number of value 1 if this value is not 0 or other value is not 0. Otherwise, number of value 0.
     */
    public Number or(Number other) {
        return contextWrap(logicBinaryOperation(this, other, (x, y) -> x.booleanValue() || y.booleanValue()));
    }

    /**
     * Helper method for logic unary operations.
     * @param n Number.
     * @param operator Unary operator to apply.
     * @return Number of value 1 if the result of the given operator is true. Otherwise, number of value 0.
     */
    private static Number logicUnaryOperation(Number n, UnaryOperator<Boolean> operator) {
        boolean isTrue = operator.apply(n.getValue() != FALSE);
        return isTrue ? new Number(TRUE) : new Number(FALSE);
    }

    /**
     * Return the logical negation of this number.
     * @param other Other number.
     * @return Number of value 0 if this value is not 0. Otherwise, number of value 1.
     */
    public Number not() {
        return contextWrap(logicUnaryOperation(this, x -> !x));
    }

    public Number copy() {
        Number number = new Number(this);
        number.setContext(context);
        number.setPosition(start, end);
        return number;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
