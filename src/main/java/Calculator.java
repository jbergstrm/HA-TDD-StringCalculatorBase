import calculator.StringCalculator;
import calculator.impl.StringCalculatorImpl;
import logger.Logger;
import logger.impl.ConsoleLogger;
import utils.InfoUtils;

import java.util.Scanner;

/**
 * @author Joakim Bergström
 */
public class Calculator {

    private final Logger logger;
    private final StringCalculator stringCalculator;

    public Calculator(final Logger logger) {
        this(logger, new StringCalculatorImpl(logger));
    }

    public Calculator(final Logger logger, final StringCalculator stringCalculator) {
        this.logger = logger;
        this.stringCalculator = stringCalculator;
    }

    public void run() {
        logger.print(InfoUtils.getWelcomeMessage());
        calculate();
    }

    public void calculate() {
        String userInput = getUserInput();
        if (userInput.startsWith("scalc")) {
            userInput = userInput.substring("scalc".length() + 1);
            if (userInput.startsWith("'") && userInput.endsWith("'")) {
                userInput = userInput.substring(1, userInput.length() - 1);
                logger.println(String.format(InfoUtils.SCALC_RESULT_TEXT, stringCalculator.add(userInput)));
            }
        }
    }

    private String getUserInput() {
        try (final Scanner scanner = new Scanner(System.in)) {
            return scanner.nextLine();
        }
    }

    public static void main(String[] args) {
        final Calculator calculator = new Calculator(new ConsoleLogger());
        calculator.run();
    }
}
