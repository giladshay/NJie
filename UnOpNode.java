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
    
}
