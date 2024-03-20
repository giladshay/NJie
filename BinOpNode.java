/**
 * Class for node which represents a binary opreation.
 * @author Gil-Ad Shay.
 */
public class BinOpNode extends Node {
    private final Node leftChild;
    private final Node rightChild;
    private final Token operator;

    public BinOpNode(Node left, Token operator, Node right) {
        this.leftChild = left;
        this.rightChild = right;
        this.operator = operator;
    }

    @Override
    public String toString() {
        return String.format("(%s, %s, %s)", leftChild.toString(), operator.toString(), rightChild.toString());
    }

    @Override
    public Number visit(Context context) throws RuntimeError {
        Number leftNumber = leftChild.visit(context);
        Number rightNumber = rightChild.visit(context);
        Number result;
        switch (operator.getType()) {
            case PLUS:
                result = leftNumber.add(rightNumber);
                break;
            case MIN:
                result = leftNumber.sub(rightNumber);
                break;
            case MUL:
                result = leftNumber.mul(rightNumber);
                break;
            case DIV:
                result = leftNumber.div(rightNumber);
                break;
            case POW:
                result = leftNumber.pow(rightNumber);
                break;
            default:
                result = null;
        }
        result.setPosition(getStart(), getEnd());
        result.setContext(context);
        return result;
    }

    @Override
    public Position getStart() {
        return leftChild.getStart();
    }

    @Override
    public Position getEnd() {
        return rightChild.getEnd();
    }
}
