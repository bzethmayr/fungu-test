package net.zethmayr.fungu.test;

import java.security.SecureRandom;
import java.util.Random;
import java.util.UUID;

import static net.zethmayr.fungu.test.TestExceptionFactory.becauseConstantsOnly;

public class TestConstants {

    private TestConstants() {
        throw becauseConstantsOnly();
    }

    public static final String EXPECTED = "expected";

    public static final String UNEXPECTED = "un" + EXPECTED;

    public static final String SHIBBOLETH = UUID.randomUUID().toString();

    public static final Random TEST_RANDOM = new SecureRandom();

    public static final Object NOT_EVEN_WRONG = new Object();

    public static Object NULL_OBJECT = null;

    public static final String NULL_STRING = null;
}
