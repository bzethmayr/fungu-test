package net.zethmayr.fungu.test;

/**
 * Represents arbitrary checked exceptions thrown by application code.
 */
public class ExampleCheckedException extends Exception {
    /**
     * Creates a new checked exception with the given message.
     *
     * @param message a message.
     */
    public ExampleCheckedException(final String message) {
        super(message);
    }

    /**
     * Creates a new checked exception with the given cause.
     *
     * @param cause the cause.
     */
    public ExampleCheckedException(final Throwable cause) {
        super(cause);
    }

    /**
     * Creates a new checked exception with the given message and cause.
     *
     * @param message a message
     * @param cause   the cause
     */
    public ExampleCheckedException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
