import java.util.Arrays;

public class StringCalculatorImpl implements StringCalculator {

    private final static String USER_SPECIFIED_DELIMITER_IDENTIFIER = "//";
    private String DELIMITER_REGEX = "[,\n]";

    @Override
    public int add(String input) {
        if (input.isEmpty()) {
            return 0;
        }

        if (input.startsWith(USER_SPECIFIED_DELIMITER_IDENTIFIER)) {
            DELIMITER_REGEX = "[" + input.charAt(2) + "]";
            input = input.substring(input.indexOf('\n') + 1);
        }

        return Arrays
                .stream(input.split(DELIMITER_REGEX))
                .mapToInt(Integer::parseInt)
                .filter(this::isValidNumber)
                .sum();
    }

    private boolean isValidNumber(final int n) {
        if (n < 0) {
            throw new NegativeNumberException(String.format("Negatives not allowed: %d", n));
        }
        return true;
    }
}
