import java.util.List;
import java.util.Scanner;

/**
 * Class for running the program.
 * @author Gil-Ad Shay.
 */
public class Main {
    private static Scanner input = new Scanner(System.in);
    private static void run(String text) {
        try {
            // Generate tokens
            Lexer lexer = new Lexer("<stdin>", text);
            List<Token> tokens = lexer.makeTokens();
           
            // Generate AST
            Parser parser = new Parser(tokens);
            Node ast = parser.parse();

            System.out.println(ast.toString());
        } catch (Error e) {
            System.out.println(e.toString());
        }
    }

    public static void main(String[] args) {
        String cmd;
        while (true) {
            System.out.print("basic > ");
            cmd = input.nextLine();
            run(cmd);
        }
    }
}
