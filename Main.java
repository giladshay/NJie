import java.util.List;
import java.util.Scanner;

/**
 * Class for running the program.
 * @author Gil-Ad Shay.
 */
public class Main {
    private static Scanner input = new Scanner(System.in);
    private static void run(String text) {
        Lexer lexer = new Lexer("<stdin>", text);
        try {
            List<Token> tokens = lexer.makeTokens();
            System.out.println(tokens);
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
