package cal;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Calculator CLI")
class CalculatorCliTest {

    @Test
    @DisplayName("interactive mode handles calculations and continues after invalid input")
    void interactiveModeContinuesAfterInvalidInput() throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        PrintStream output = new PrintStream(bytes, true, StandardCharsets.UTF_8);
        BufferedReader input = new BufferedReader(new StringReader("6 / 3\nbad input\n10 + 5\nexit\n"));

        CalculatorCli.runInteractive(input, output);

        String result = bytes.toString(StandardCharsets.UTF_8);
        assertTrue(result.contains("6.0 / 3.0 = 2.0"));
        assertTrue(result.contains("Error: use <number> <operator> <number>"));
        assertTrue(result.contains("10.0 + 5.0 = 15.0"));
    }
}
