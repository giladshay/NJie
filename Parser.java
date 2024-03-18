import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

/**
 * Class for parsing tokens into tree.
 * @author Gil-Ad Shay.
 */
public class Parser {
    private final List<Token> tokens;
    private int idx;
    /**
     * Initialize new Parser with given tokens.
     * @param tokens List of tokens.
     */
    public Parser(List<Token> tokens) {
        this.tokens = tokens;
        this.idx = 0;
    }

    /**
     * Advances this index by 1.
     */
    private void advance() {
        idx ++;
    }

    /**
     * Get the current token.
     * @return Current token if this index is in the range. Otherwise, return the last token.
     */
    private Token getCurrentToken() {
        return idx < tokens.size() ? tokens.get(idx) : tokens.get(tokens.size() - 1);
    }

    /**
     * Create the tree in the case of factor according to grammar rules.
     * Recall the factor case is either:
     * INT | FLOAT
     * (+ | -) factor
     * LPAREN expr RPAREN
     * @return Node 
     * @throws InvalidSyntaxError Where encountering invalid syntax.
     */
    private Node factor() throws InvalidSyntaxError {
        Token currentToken = getCurrentToken();

        // (+ | -) factor
        if (currentToken.getType() == Token.Type.PLUS || currentToken.getType() == Token.Type.MIN) {
            advance();
            Node factor = factor();
            return new UnOpNode(currentToken, factor);
        }
    
        // INT | FLOAT
        else if (currentToken.getType() == Token.Type.INT || currentToken.getType() == Token.Type.FLOAT) {
            advance();
            return new NumberNode((ValueableToken) currentToken); 
        }

        // LPAREN expr RPAREN
        else if (currentToken.getType() == Token.Type.LPAREN) {
            advance();
            Node expr = expr();
            if (getCurrentToken().getType() == Token.Type.RPAREN) {
                advance();
                return expr;
            }
            throw new InvalidSyntaxError("Expectd ')'", getCurrentToken().getStart(), getCurrentToken().getEnd());
        }
        throw new InvalidSyntaxError("Expected INT or FLOAT", currentToken.getStart(), currentToken.getEnd());
    }

    /**
     * Helper method for similar grammar rules of binary operation.
     * @param func Function to get the left and right children (operands).
     * @param ops Set of operators that can be a binary operator for the current grammar rule that called this function.
     * @return Node with the result of the factor.
     * @throws InvalidSyntaxError Where encountering invalid syntax.
     */
    private Node binaryOperation(ThrowableSupplier<Node, InvalidSyntaxError> func, Set<Token.Type> ops) throws InvalidSyntaxError {
        Node left = func.get();

        while (ops.contains(getCurrentToken().getType())) {
            Token operator = getCurrentToken();
            advance();
            Node right = func.get();
            left = new BinOpNode(left, operator, right);    
        }
        return left;
    }

    /**
     * Create the tree in the case of term according to grammar rules. 
     * Recall the term case is:
     * factor ((* | /) factor)
     * @return Node with the result of the term.
     * @throws InvalidSyntaxError Where encountering invalid syntax.
     */
    private Node term() throws InvalidSyntaxError {
        return binaryOperation(this::factor, new HashSet<>() {{
            add(Token.Type.MUL);
            add(Token.Type.DIV);
        }});
    }

    /**
     * Create the tree in the case of expr according to grammar rules.
     * Recall the expr case is:
     * term ((+ | -) term)*
     * @return Node with the tree of the expression.
     * @throws InvalidSyntaxError Where encountering invalid syntax.
     */
    private Node expr() throws InvalidSyntaxError {
        return binaryOperation(this::term, new HashSet<>() {{
            add(Token.Type.PLUS);
            add(Token.Type.MIN);
        }});
    }

    /**
     * Parse the complete sentence into the complete tree.
     * @return Node of the entire tree.
     * @throws InvalidSyntaxError Where encounter invalid syntax.
     */
    public Node parse() throws InvalidSyntaxError {
        Node res = expr();
        if (getCurrentToken().getType() != Token.Type.EOF) {
            throw new InvalidSyntaxError("Expected '+', '-', '*', or '/'", getCurrentToken().getStart(), getCurrentToken().getEnd());
        }
        return res;
    }
}
