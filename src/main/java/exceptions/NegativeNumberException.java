package exceptions;

/**
 *
 * @author Joakim Bergström
 */
public class NegativeNumberException extends RuntimeException {

    public NegativeNumberException(final String msg) {
        super(msg);
    }

}
