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
     * Wrap the given number with this instance context.
     * @param number Given number.
     * @return The number wrapped in this context.
     */
    private Number contextWrap(Number number) {
        number.context = this.context;
        return number;
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
