package calculator.impl;

import calculator.StringCalculator;
import exceptions.NegativeNumberException;
import logger.Logger;

import java.util.Arrays;
import java.util.regex.Pattern;

public class StringCalculatorImpl implements StringCalculator {

    private final static String USER_SPECIFIED_DELIMITER_IDENTIFIER = "//";
    private String DELIMITER_REGEX = "[,\n]";

    private final Logger logger;

    public StringCalculatorImpl(final Logger logger) {
        this.logger = logger;
    }

    @Override
    public int add(String input) {
        if (input.isEmpty()) {
            return 0;
        }

        if (input.startsWith(USER_SPECIFIED_DELIMITER_IDENTIFIER)) {
            DELIMITER_REGEX = getQuotedDelimiters(input);
            input = extractNumbers(input);
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

        if (n > 1000) {
            logger.log(String.format("The given number %d is larger than 1000", n));
        }

        return true;
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
        return str.substring(2, str.indexOf("\n"));
    }

    private String extractNumbers(final String str) {
        return str.substring(str.indexOf('\n') + 1);
    }
}
