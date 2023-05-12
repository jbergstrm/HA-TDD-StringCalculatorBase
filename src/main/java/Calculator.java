import logger.Logger;
import utils.InfoUtils;

/**
 * @author Joakim Bergström
 */
public class Calculator {

    private final Logger logger;

    public Calculator(final Logger logger) {
        this.logger = logger;
    }

    public void run() {
        logger.print(String.format("%s\n%s\n", InfoUtils.WELCOME_TEXT, InfoUtils.HELP_TEXT));
    }

    public static void main(String[] args) {

    }
}
