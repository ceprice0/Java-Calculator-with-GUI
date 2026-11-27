package cal;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;

/**
 * A text-only (headless) way to run the calculator.
 *
 * This lets the same jar run inside a Docker container, where there is no
 * screen to draw the Swing window on. Give it three arguments, for example
 * "6 / 3", and it prints the answer. Use --interactive to keep the calculator
 * open for repeated calculations.
 */
public class CalculatorCli {

    public static void main(String[] args) {
        run(args);
    }

    static void run(String[] args) {
        CalculatorEngine engine = new CalculatorEngine();

        if (args.length == 1 && "--interactive".equals(args[0])) {
            try {
                runInteractive(new BufferedReader(new InputStreamReader(System.in)), System.out);
            } catch (IOException ex) {
                System.err.println("Error reading calculator input: " + ex.getMessage());
            }
            return;
        }

        if (args.length == 3) {
            try {
                double a = Double.parseDouble(args[0]);
                char operator = args[1].charAt(0);
                double b = Double.parseDouble(args[2]);
                System.out.println(a + " " + operator + " " + b + " = " + engine.apply(operator, a, b));
            } catch (NumberFormatException ex) {
                System.out.println("Error: please give two numbers, e.g. 6 / 3");
            } catch (ArithmeticException | IllegalArgumentException ex) {
                System.out.println("Error: " + ex.getMessage());
            }
            return;
        }

        System.out.println("Calculator (headless mode)");
        System.out.println("Usage: java -jar calculator.jar <a> <operator> <b>   e.g. 6 / 3");
        System.out.println("       java -jar calculator.jar --interactive");
        System.out.println("Demo:");
        System.out.println("  2 + 3 = " + engine.apply('+', 2, 3));
        System.out.println("  10 - 4 = " + engine.apply('-', 10, 4));
        System.out.println("  6 * 7 = " + engine.apply('*', 6, 7));
        System.out.println("  20 / 5 = " + engine.apply('/', 20, 5));
    }

    static void runInteractive(BufferedReader input, PrintStream output) throws IOException {
        CalculatorEngine engine = new CalculatorEngine();
        output.println("Interactive calculator. Enter: <number> <operator> <number> (or 'exit' to quit).");

        String line;
        while (true) {
            output.print("> ");
            output.flush();
            line = input.readLine();
            if (line == null || "exit".equalsIgnoreCase(line.trim())) {
                return;
            }
            if (line.isBlank()) {
                continue;
            }

            String[] parts = line.trim().split("\\s+");
            if (parts.length != 3 || parts[1].length() != 1) {
                output.println("Error: use <number> <operator> <number>, e.g. 6 / 3");
                continue;
            }

            try {
                double a = Double.parseDouble(parts[0]);
                char operator = parts[1].charAt(0);
                double b = Double.parseDouble(parts[2]);
                output.println(a + " " + operator + " " + b + " = " + engine.apply(operator, a, b));
            } catch (NumberFormatException ex) {
                output.println("Error: please enter two numbers, e.g. 6 / 3");
            } catch (ArithmeticException | IllegalArgumentException ex) {
                output.println("Error: " + ex.getMessage());
            }
        }
    }
}
