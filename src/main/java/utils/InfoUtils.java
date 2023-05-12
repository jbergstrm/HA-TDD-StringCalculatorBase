package utils;

/**
 * @author Joakim Bergström
 */
public class InfoUtils {

    public static final String WELCOME_TEXT =
            "----- Welcome to the amazing StringCalculator -----";

    public static final String HELP_TEXT =
            "Temporary help text could not be bothered to write.";

    public static final String SCALC_RESULT_TEXT =
            "The result is %d";

    public static String getWelcomeMessage() {
        return String.format("%s\n%s\n", WELCOME_TEXT, HELP_TEXT);
    }
}
