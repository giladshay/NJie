import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
     * Helper method for similar grammar rules of binary operation.
     * @param leftFunc Function to get the left child (operand).
     * @param ops Set of operators that can be a binary operator for the current grammar rule that called this function.
     * @param rightFunc Function to get the right child (operand).
     * @return Node with the result of the operator.
     * @throws InvalidSyntaxError Where encountering invalid syntax.
     */
    private Node binaryOperations(ThrowableSupplier<Node, InvalidSyntaxError> leftFunc, Set<Token.Type> ops, ThrowableSupplier<Node, InvalidSyntaxError> rightFunc) throws InvalidSyntaxError {
        Node left = leftFunc.get();

        while (ops.contains(getCurrentToken().getType())) {
            Token operator = getCurrentToken();
            advance();
            Node right = rightFunc.get();
            left = new BinOpNode(left, operator, right);    
        }
        return left;
    }

    /**
     * Helper method for using binary operation where left function and right function are the same.
     * @param func Function to use for left and right children.
     * @param ops Set of operators that can be a binary operator for the current grammar ruke.
     * @return Node with the result of the operator.
     * @throws InvalidSyntaxError Where encountering invalid syntax.
     */
    private Node binaryOperation(ThrowableSupplier<Node, InvalidSyntaxError> func, Set<Token.Type> ops) throws InvalidSyntaxError {
        return binaryOperations(func, ops, func);
    }
    
    /**
     * Create the tree in the case of factor according to grammar rules.
     * Recall the atom case is either:
     * INT | FLOAT
     * IDENTIFIER
     * LPAREN expr RPAREN
     * @return
     * @throws InvalidSyntaxError
     */
    private Node atom() throws InvalidSyntaxError {
        Token currentToken = getCurrentToken();
        // INT | FLOAT
        if (currentToken.getType() == Token.Type.INT || currentToken.getType() == Token.Type.FLOAT) {
            advance();
            return new NumberNode((ValueableToken) currentToken); 
        // IDENTIFIER
        } else if (currentToken.getType() == Token.Type.IDENTIFIER) {
            advance();
            return new VarAccessNode((IdentifierToken) currentToken);
        // LPAREN expr RPAREN   
        } else if (currentToken.getType() == Token.Type.LPAREN) {
            advance();
            Node expr = expr();
            if (getCurrentToken().getType() == Token.Type.RPAREN) {
                advance();
                return expr;
            }
            throw new InvalidSyntaxError("Expected ')'", getCurrentToken().getStart(), getCurrentToken().getEnd());
        } else {
            throw new InvalidSyntaxError("Expected INT, FLOAT, IDENTIFIER, '+', '-', or ')'", currentToken.getStart(), currentToken.getEnd());
        }
    }

    /**
     * Create the tree in the case of power according to grammar rules.
     * Recall the power case is:
     * atom (^ factor)*
     * @return
     * @throws InvalidSyntaxError
     */
    private Node power() throws InvalidSyntaxError {
        // atom (^ factor)*
        return binaryOperations(this::atom, new HashSet<>() {{
            add(Token.Type.POW);
        }}, this::factor);
    }

    /**
     * Create the tree in the case of factor according to grammar rules.
     * Recall the factor case is either:
     * (+ | -) factor
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
        return power();
    }

    /**
     * Create the tree in the case of term according to grammar rules. 
     * Recall the term case is:
     * factor ((* | /) factor)*
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
     * VAR varname = expr
     * @return Node with the tree of the expression.
     * @throws InvalidSyntaxError Where encountering invalid syntax.
     */
    private Node expr() throws InvalidSyntaxError {
        int currentIndex = idx;
        // VAR varname = expr
        if (getCurrentToken().equals(new KeywordToken(KeywordToken.Keyword.VAR, null))) {
            advance();
            if (getCurrentToken().getType() != Token.Type.IDENTIFIER) {
                throw new InvalidSyntaxError("Expected identifier", getCurrentToken().getStart(), getCurrentToken().getEnd());
            }
            IdentifierToken varName = (IdentifierToken) getCurrentToken();
            advance();
            if (getCurrentToken().getType() != Token.Type.EQ) {
                throw new InvalidSyntaxError("Expected '='", getCurrentToken().getStart(), getCurrentToken().getEnd());
            }
            advance();
            Node expr = expr();
            return new VarAssignNode(varName, expr);
        }
        try {
            // term ((+ | -) term)*
            return binaryOperation(this::term, new HashSet<>() {{
                add(Token.Type.PLUS);
                add(Token.Type.MIN);
            }});
        } catch (InvalidSyntaxError ise) {
            if (currentIndex != idx) // if we have advanced, do not override.
                throw ise;
            else
                throw new InvalidSyntaxError("Expected INT, FLOAT, IDENTIFIER, 'VAR', '+', '-', or '('", getCurrentToken().getStart(), getCurrentToken().getEnd());
        }
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
