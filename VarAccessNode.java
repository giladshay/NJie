/**
 * Class for node of accessing variables and using their values.
 * @author Gil-Ad Shay.
 */
public class VarAccessNode extends Node {

    private final IdentifierToken varName;
    /**
     * Initialize new var access node.
     * @param varName name of the accessed variable.
     */
    public VarAccessNode(IdentifierToken varName) {
        this.varName = varName;
    }

    @Override
    public String toString() {
        return varName.toString();
    }

    @Override
    public MyNumber visit(Context context) throws RuntimeError {
        String name = varName.getName(); 
        MyNumber value = context.getSymbolTable().get(name);

        if (value == null)
            throw new RuntimeError(String.format("%s is not defined", name), getStart(), getEnd(), context);
        value = value.copy();
        value.setPosition(getStart(), getEnd());
        return value;
    }

    @Override
    public Position getStart() {
        return varName.getStart();
    }

    @Override
    public Position getEnd() {
        return varName.getEnd();
    }
    
}
