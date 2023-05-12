package logger.impl;

import logger.Logger;

/**
 * @author Joakim Bergström
 */
public class ConsoleLogger implements Logger {

    @Override
    public void log(String message) {
        // Temporary empty
    }

    @Override
    public void print(String message) {
        System.out.print(message);
    }

    @Override
    public void println(String message) {
        System.out.println(message);
    }
}
