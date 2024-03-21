import java.util.HashMap;
import java.util.Map;
import java.util.function.BinaryOperator;

/**
 * Class for node which represents a binary opreation.
 * @author Gil-Ad Shay.
 */
public class BinOpNode extends Node {
    private final Node leftChild;
    private final Node rightChild;
    private final Token operator;

    private static final Map<Token, ThrowableBinaryOperator<MyNumber, RuntimeError>> OPERATIONS = new HashMap<>() {{
        put(new Token(Token.Type.PLUS), (x, y) -> x.add(y));
        put(new Token(Token.Type.MIN), (x, y) -> x.sub(y));
        put(new Token(Token.Type.MUL), (x, y) -> x.mul(y));
        put(new Token(Token.Type.DIV), (x, y) -> x.div(y));
        put(new Token(Token.Type.POW), (x, y) -> x.pow(y));
        put(new Token(Token.Type.EQUALS), (x, y) -> x.eq(y));
        put(new Token(Token.Type.NOT_EQUALS), (x, y) -> x.neq(y));
        put(new Token(Token.Type.LESS_THAN), (x, y) -> x.lt(y));
        put(new Token(Token.Type.LESS_THAN_OR_EQUALS), (x, y) -> x.lte(y));
        put(new Token(Token.Type.GREATER_THAN), (x, y) -> x.gt(y));
        put(new Token(Token.Type.GREATER_THAN_OR_EQUALS), (x, y) -> x.gte(y));
        put(new KeywordToken(KeywordToken.Keyword.AND), (x, y) -> ((MyBoolean) x).and((MyBoolean) y));
        put(new KeywordToken(KeywordToken.Keyword.OR), (x, y) -> ((MyBoolean) x).or((MyBoolean) y));
    }};

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
    public MyNumber visit(Context context) throws RuntimeError {
        MyNumber leftNumber = leftChild.visit(context);
        MyNumber rightNumber = rightChild.visit(context);
        MyNumber result = OPERATIONS.get(operator).apply(leftNumber, rightNumber);
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
