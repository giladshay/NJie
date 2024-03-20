import java.util.HashMap;
import java.util.Map;

/**
 * Class for tracking symbols (i.e., variables) in the program.
 * @author Gil-Ad Shay.
 */
public class SymbolTable {
    private final Map<String, Number> symbols;
    private final SymbolTable parent;

    /**
     * Initialize an empty symbol table.
     */
    public SymbolTable() {
        symbols = new HashMap<>();
        parent = null;
    }

    /**
     * Get the value of the variable named name.
     * @param name Given name.
     * @return The value of the variable in the symbol table. null if not found.
     */
    public Number get(String name) {
        Number value = symbols.getOrDefault(name, null);
        if (value == null && parent != null)
            return parent.get(name);
        return value;
    }

    /**
     * Set the value of the variale named name to the given value.
     * @param name Name of variable.
     * @param value New value to assign.
     */
    public void set(String name, Number value) {
        symbols.put(name, value);
    }

    /**
     * Remove the variable named name from this symbol table.
     * @param name Name of variable.
     */
    public void remove(String name) {
        symbols.remove(name);
    }
}
