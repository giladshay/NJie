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
}
