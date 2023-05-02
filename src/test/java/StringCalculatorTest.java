import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StringCalculatorTest {

    private StringCalculator calculator;

    @BeforeEach
    public void beforeEach() {
        calculator = new StringCalculatorImpl();
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
}
