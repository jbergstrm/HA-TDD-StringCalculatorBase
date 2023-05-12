import calculator.impl.StringCalculatorImpl;
import exceptions.NegativeNumberException;
import logger.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import utils.InfoUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

public class StringCalculatorTest {

    private static final String[] FAKE_ARGS = new String[0];

    @Mock
    private Logger logger;

    @InjectMocks
    private StringCalculatorImpl calculator;

    @BeforeEach
    public void beforeEach() {
        MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    public void afterEach() {
        // Set the default system in.
        System.setIn(System.in);

        // Set the default system out.
        System.setOut(System.out);
    }

    @Test
    public void testEmptyStringReturnsZero() {
        Assertions.assertEquals(0, calculator.add(""));
    }

    @Test
    public void testWithSingleNumber() {
        Assertions.assertEquals(1, calculator.add("1"));
        Assertions.assertEquals(7, calculator.add("7"));
        Assertions.assertEquals(11, calculator.add("11"));
    }

    @Test
    public void testWithTwoNumbersSeparatedByComma() {
        Assertions.assertEquals(3, calculator.add("1,2"));
        Assertions.assertEquals(13, calculator.add("4,9"));
        Assertions.assertEquals(16, calculator.add("11,5"));
    }

    @Test
    public void testWithUnspecifiedAmountOfNumbersSeparatedByComma() {
        Assertions.assertEquals(21, calculator.add("1,2,3,4,5,6"));
        Assertions.assertEquals(54, calculator.add("6,8,3,9,11,15,2"));
    }

    @Test
    public void testWithTwoNumbersSeparatedByNewline() {
        Assertions.assertEquals(3, calculator.add("1\n2"));
        Assertions.assertEquals(13, calculator.add("4\n9"));
        Assertions.assertEquals(16, calculator.add("11\n5"));
    }

    @Test
    public void testWithUnspecifiedAmountOfNumbersSeparatedByCommaOrNewline() {
        Assertions.assertEquals(21, calculator.add("1\n2\n3,4\n5,6"));
        Assertions.assertEquals(54, calculator.add("6,8\n3,9,11\n15\n2"));
    }

    @Test
    public void testWithUserSpecifiedDelimiter() {
        Assertions.assertEquals(3, calculator.add("//;\n1;2"));
        Assertions.assertEquals(16, calculator.add("//#\n11#5"));
        Assertions.assertEquals(21, calculator.add("//+\n1+2+3+4+5+6"));
    }

    @Test
    public void testWithNegativeNumberThrowsNegativeNumberException() {
        final NegativeNumberException negativeNumberException =
                Assertions.assertThrowsExactly(NegativeNumberException.class, () -> calculator.add("1,-2"));
        Assertions.assertEquals(String.format("Negatives not allowed: %d", -2), negativeNumberException.getMessage());

    }

    @Test
    public void testWithNegativeNumberThrowsNegativeNumberExceptionWithUserSpecifiedDelimiter() {
        final NegativeNumberException negativeNumberException =
                Assertions.assertThrowsExactly(NegativeNumberException.class, () -> calculator.add("//;\n-11;2;4"));
        Assertions.assertEquals(String.format("Negatives not allowed: %d", -11), negativeNumberException.getMessage());
    }

    @Test
    public void testWithNumberLargerThanThousand() {
        final int result = calculator.add("1001,1");
        Mockito.verify(logger, Mockito.times(1))
                .log(String.format("The given number %d is larger than 1000", 1001));
        Assertions.assertEquals(1002, result);
    }

    @Test
    public void testWithMultipleNumberLargerThanThousand() {
        final int result = calculator.add("1001,1002,1000");
        Mockito.verify(logger, Mockito.times(2))
                .log(Mockito.contains("is larger than 1000"));
        Assertions.assertEquals(3003, result);
    }

    @Test
    public void testWelcomeMessageAndHelpText() {
        logger.print(InfoUtils.getWelcomeMessage());
        Mockito.verify(logger, Mockito.times(1))
                        .print(String.format("%s\n%s\n", InfoUtils.WELCOME_TEXT, InfoUtils.HELP_TEXT));
    }

    @Test
    public void testWithUserConsoleInputUnspecifiedAmountOfNumbersSeparatedByComma() {
        final InputStream inputStream = new ByteArrayInputStream("scalc '1,2,3'".getBytes(StandardCharsets.UTF_8));
        final OutputStream outputStream = new ByteArrayOutputStream();

        System.setIn(inputStream);
        System.setOut(new PrintStream(outputStream));

        Calculator.main(FAKE_ARGS);
        Assertions.assertEquals(String.format("%s\n%s\n", InfoUtils.WELCOME_TEXT, InfoUtils.HELP_TEXT) +
                        String.format(InfoUtils.SCALC_RESULT_TEXT + System.lineSeparator(), 6), outputStream.toString());
    }
}
