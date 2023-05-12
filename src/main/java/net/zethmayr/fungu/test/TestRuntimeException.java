package net.zethmayr.fungu.test;

/**
 * Base class for exceptions that
 * indicate that there is something wrong with the test case.
 */
public class TestRuntimeException extends RuntimeException {
    /**
     * Creates a new unchecked exception with the given message.
     *
     * @param message a message.
     */
    public TestRuntimeException(final String message) {
        super(message);
    }

    /**
     * Creates a new unchecked exception with the given cause.
     *
     * @param cause the cause.
     */
    public TestRuntimeException(final Throwable cause) {
        super(cause);
    }

    /**
     * Creates a new unchecked exception with the given message and cause.
     *
     * @param message a message.
     * @param cause   the cause.
     */
    public TestRuntimeException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
