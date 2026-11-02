package cal;

/**
 * A text-only (headless) way to run the calculator.
 *
 * This lets the same jar run inside a Docker container, where there is no
 * screen to draw the Swing window on. Give it three arguments, for example
 * "6 / 3", and it prints the answer. With no arguments it prints a short
 * demo so a container run still shows something useful.
 */
public class CalculatorCli {

    public static void main(String[] args) {
        run(args);
    }

    static void run(String[] args) {
        CalculatorEngine engine = new CalculatorEngine();

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
        System.out.println("Demo:");
        System.out.println("  2 + 3 = " + engine.apply('+', 2, 3));
        System.out.println("  10 - 4 = " + engine.apply('-', 10, 4));
        System.out.println("  6 * 7 = " + engine.apply('*', 6, 7));
        System.out.println("  20 / 5 = " + engine.apply('/', 20, 5));
    }
}
