/**
 * As the grammar rules can be understood as a tree, we create node classes.
 * This class is an abstract superclass for all other nodes.
 * @see BinOpNode
 * @see NumberNode
 * @author Gil-Ad Shay.
 */
public abstract class Node {
    @Override
    public abstract String toString();

    /**
     * Visit current node and calculate its value.
     * @param context Context of the visit for tracking errors.
     * @return Number with the result of the visit.
     * @throws RuntimeError If there is a runtime error during the run of the program.
     */
    public abstract Number visit(Context context) throws RuntimeError;

    /**
     * Get the starting position for this node.
     * @return Starting position of this node.
     */
    public abstract Position getStart();

    /**
     * Get the ending position for this node.
     * @return Ending position for this node.
     */
    public abstract Position getEnd();
}

