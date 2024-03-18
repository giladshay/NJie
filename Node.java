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
}

