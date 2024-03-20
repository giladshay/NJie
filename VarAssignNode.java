/**
 * Class for assigning variables.
 * @author Gil-Ad Shay.
 */
public class VarAssignNode extends Node {

    private final IdentifierToken varName;
    private final Node value;

    /**
     * Initialize a new var assigning node assigning given value to given variable.
     * @param varName Name of the variable we are assigning.
     * @param value The value of the assigned variable.
     */
    public VarAssignNode(IdentifierToken varName, Node value) {
        this.varName = varName;
        this.value = value;
    }

    @Override
    public String toString() {
        return String.format("(%s: %s)", varName.getName(), value.toString());
    }

    @Override
    public Number visit(Context context) throws RuntimeError {
        String name = varName.getName();
        Number numberValue = value.visit(context);
        context.getSymbolTable().set(name, numberValue);
        return numberValue;
    }

    @Override
    public Position getStart() {
        return varName.getStart();
    }

    @Override
    public Position getEnd() {
        return value.getEnd();
    }

    
}
