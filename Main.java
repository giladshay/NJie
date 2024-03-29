import java.util.List;
import java.util.Scanner;

/**
 * Class for running the program.
 * @author Gil-Ad Shay.
 */
public class Main {
    private static final SymbolTable globalSymbolTable = new SymbolTable();
    private static Scanner input = new Scanner(System.in);
    private static void run(String text) {
        try {
            // Generate tokens
            Lexer lexer = new Lexer("<stdin>", text);
            List<Token> tokens = lexer.makeTokens();
           
            // Generate AST
            Parser parser = new Parser(tokens);
            Node ast = parser.parse();

            // Initialize context
            Context context = new Context("<program>", globalSymbolTable);
            
            // Run program 
            MyNumber result = Interpreter.visit(ast, context);

            // Show result
            System.out.println(result);
        } catch (Error e) {
            System.out.println(e.toString());
        }
    }

    public static void main(String[] args) {
        // Initialize global symbol table
        globalSymbolTable.set("NULL", new MyNumber(0));
        globalSymbolTable.set("TRUE", new MyBoolean(true));
        globalSymbolTable.set("FALSE", new MyBoolean(false));

        String cmd;
        while (true) {
            System.out.print("basic > ");
            cmd = input.nextLine();
            run(cmd);
        }
    }
}
