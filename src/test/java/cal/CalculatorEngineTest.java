package cal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("CalculatorEngine arithmetic")
class CalculatorEngineTest {

    private final CalculatorEngine engine = new CalculatorEngine();

    @Test
    @DisplayName("adds two numbers")
    void addsTwoNumbers() {
        assertEquals(5.0, engine.add(2, 3));
    }

    @Test
    @DisplayName("subtracts two numbers")
    void subtractsTwoNumbers() {
        assertEquals(6.0, engine.subtract(10, 4));
    }

    @Test
    @DisplayName("multiplies two numbers")
    void multipliesTwoNumbers() {
        assertEquals(42.0, engine.multiply(6, 7));
    }

    @Test
    @DisplayName("divides two numbers")
    void dividesTwoNumbers() {
        assertEquals(4.0, engine.divide(20, 5));
    }

    @Test
    @DisplayName("dividing by zero throws an error")
    void divideByZeroThrows() {
        ArithmeticException ex =
                assertThrows(ArithmeticException.class, () -> engine.divide(5, 0));
        assertTrue(ex.getMessage().toLowerCase().contains("zero"));
    }

    @Test
    @DisplayName("apply() routes to the right operation")
    void applyRoutesByOperator() {
        assertEquals(8.0, engine.apply('+', 5, 3));
        assertEquals(2.0, engine.apply('-', 5, 3));
        assertEquals(15.0, engine.apply('*', 5, 3));
        assertEquals(5.0, engine.apply('/', 10, 2));
    }

    @Test
    @DisplayName("apply() rejects an unknown operator")
    void applyRejectsUnknownOperator() {
        assertThrows(IllegalArgumentException.class, () -> engine.apply('%', 5, 3));
    }
}
