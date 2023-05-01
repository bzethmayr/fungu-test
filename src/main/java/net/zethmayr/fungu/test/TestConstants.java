package net.zethmayr.fungu.test;

import java.security.SecureRandom;
import java.util.Random;
import java.util.UUID;

import static net.zethmayr.fungu.test.TestExceptionFactory.becauseConstantsOnly;

/**
 * Declares constants of frequent use in testing.
 */
public class TestConstants {

    private TestConstants() {
        throw becauseConstantsOnly();
    }

    /**
     * An expected value.
     */
    public static final String EXPECTED = "expected";

    /**
     * An unexpected value.
     */
    public static final String UNEXPECTED = "un" + EXPECTED;

    /**
     * An unpredictable value.
     */
    public static final String SHIBBOLETH = UUID.randomUUID().toString();

    /**
     * A halfway-decent random number generator,
     * probably not correlated to that used by the code under test.
     */
    public static final Random TEST_RANDOM = new SecureRandom();

    /**
     * A value that will not be created by any application code.
     */
    public static final Object NOT_EVEN_WRONG = new Object();

    /**
     * A null object without the IDE getting worried.
     */
    public static Object NULL_OBJECT = null;

    /**
     * A null string without the IDE getting worried.
     */
    public static final String NULL_STRING = null;
}
