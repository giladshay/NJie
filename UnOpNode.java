import java.util.HashMap;
import java.util.Map;
import java.util.function.UnaryOperator;

/**
 * Enables unary operation as -5.
 * @author Gil-Ad Shay.
 */
public class UnOpNode extends Node {
    private final Token operator;
    private final Node operand;

    /**
     * Initialize new unary operation node with the given operator (- or +) and operand.
     * @param operator Unary operator (- or +).
     * @param operand Node that comes after the operator.
     */
    public UnOpNode(Token operator, Node operand) {
        this.operator = operator;
        this.operand = operand;
    }

    @Override
    public String toString() {
        return String.format("(%s, %s)", operator, operand);
    }

    @Override
    public Number visit(Context context) throws RuntimeError {
        Number result = operand.visit(context);
        if (operator.getType() == Token.Type.MIN)
            result = result.neg();
        result.setPosition(getStart(), getEnd());
        result.setContext(context);
        return result;
    }

    @Override
    public Position getStart() {
        return operator.getStart();
    }

    @Override
    public Position getEnd() {
        return operand.getEnd();
    }
    
}
