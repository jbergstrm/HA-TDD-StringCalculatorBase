import java.util.Arrays;
import java.util.regex.Pattern;

public class StringCalculatorImpl implements StringCalculator {

    private final static String USER_SPECIFIED_DELIMITER = "//";
    private String DELIMITER_REGEX = "[,\n]";

    @Override
    public int add(String input) {
        if (input.isEmpty()) {
            return 0;
        }

        if (input.startsWith(USER_SPECIFIED_DELIMITER)) {
            DELIMITER_REGEX = getQuotedDelimiters(input);
            input = extractNumbers(input);
        }

        return Arrays
                .stream(input.split(DELIMITER_REGEX))
                .mapToInt(Integer::parseInt)
                .filter(this::isValidNumber)
                .sum();
    }

    private boolean isValidNumber(final int n) throws NegativeNumberException {
        if (n < 0) {
            throw new NegativeNumberException(String.format("Negatives not allowed: %d", n));
        }
        return n <= 1000;
    }

    private String getQuotedDelimiters(final String str) {
        String delimiter = extractDelimiter(str);

        if (delimiter.startsWith("[") && delimiter.endsWith("]")) {
            delimiter = delimiter.substring(1, delimiter.length() - 1);
            return handleMultipleDelimiters(delimiter);
        }

        return Pattern.quote(delimiter);
    }

    private String handleMultipleDelimiters(final String str) {
        String[] delimiters = str.split("]\\[");
        delimiters = Arrays.stream(delimiters).map(Pattern::quote).toArray(String[]::new);
        return String.join("|", delimiters);
    }

    private String extractDelimiter(final String str) {
        return str.substring(2, str.indexOf('\n'));
    }

    private String extractNumbers(final String str) {
        return str.substring(str.indexOf('\n') + 1);
    }
}
