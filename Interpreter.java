/**
 * Interpreter class for getting the result out of parsing.
 * @author Gil-Ad Shay.
 */
public class Interpreter {
    /**
     * Visit the given node and return its result.
     * @param node Given node to visit all its node and calculate its value.
     * @param context Context of the program for tracking errors.
     * @throws RuntimeError In case of a runtime error.
     */
    public static Number visit(Node node, Context context) throws RuntimeError {
       return node.visit(context);
    }
}
