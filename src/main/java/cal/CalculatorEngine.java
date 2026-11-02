package cal;

/**
 * Pure arithmetic for the calculator, with no user interface.
 *
 * Keeping the maths in its own class means it can be unit tested on its own,
 * without having to open the Swing window.
 */
public class CalculatorEngine {

    public double add(double a, double b) {
        return a + b;
    }

    public double subtract(double a, double b) {
        return a - b;
    }

    public double multiply(double a, double b) {
        return a * b;
    }

    public double divide(double a, double b) {
        if (b == 0) {
            throw new ArithmeticException("Cannot divide by zero");
        }
        return a / b;
    }

    /**
     * Runs one operation chosen by its symbol (+, -, *, /).
     */
    public double apply(char operator, double a, double b) {
        switch (operator) {
            case '+':
                return add(a, b);
            case '-':
                return subtract(a, b);
            case '*':
                return multiply(a, b);
            case '/':
                return divide(a, b);
            default:
                throw new IllegalArgumentException("Unknown operator: " + operator);
        }
    }
}
