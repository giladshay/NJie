/**
 * Hold the current context of the program.
 * @author Gil-Ad Shay.
 */
public class Context {

    private final String displayName;
    private final Context parent;
    private final Position parentEntry;

    private final SymbolTable symbolTable;
    /**
     * Initialize new Context.
     * @param displayName Name for displaying the context.
     * @param parent Parent of context for recursively showing the context.
     * @param parentEntry The position where the context has changed.
     */
    public Context(String displayName, Context parent, Position parentEntry, SymbolTable symbolTable) {
        this.displayName = displayName;
        this.parent = parent;
        this.parentEntry = parentEntry;
        this.symbolTable = symbolTable;
    }   

    /**
     * Initialize a new Context with default (null) parent and parentEntry.
     * @param displayName Name for displaying the context.
     */
    public Context(String displayName, SymbolTable symbolTable) {
        this(displayName, null, null, symbolTable);
    }
    
    public String getDisplayName() {
        return displayName;
    }

    public Context getParent() {
        return parent;
    }

    public Position getParentEntry() {
        return parentEntry;
    }

    public SymbolTable getSymbolTable() {
        return symbolTable;
    }
}
