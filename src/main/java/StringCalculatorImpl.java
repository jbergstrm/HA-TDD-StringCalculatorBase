import java.util.Arrays;

public class StringCalculatorImpl implements StringCalculator {

    private final static String DELIMITER_REGEX = "[,\n]";

    @Override
    public int add(String input) {
        return input.isEmpty() ? 0 : Arrays
                .stream(input.split(DELIMITER_REGEX))
                .mapToInt(Integer::parseInt)
                .sum();
    }
}
