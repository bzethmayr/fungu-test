package net.zethmayr.fungu.test;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Optional;

import static java.lang.String.format;
import static net.zethmayr.fungu.test.TestConstants.TEST_RANDOM;
import static net.zethmayr.fungu.test.TestExceptionFactory.becauseIllegal;
import static net.zethmayr.fungu.test.TestExceptionFactory.becauseStaticsOnly;

/**
 * Static methods used in very common test cases.
 */
public final class TestHelper {
    private TestHelper() {
        throw becauseStaticsOnly();
    }

    /**
     * Invokes a class's no-argument constructor, if any,
     * rethrowing any exception thrown by the constructor
     * or during invocation.
     *
     * @param underTest a class under test
     * @param <T>       the type under test
     * @return any instance created
     * @throws Exception as thrown in instantiation
     */
    public static <T> T invokeDefaultConstructor(
            final Class<T> underTest
    ) throws Exception {
        final Constructor<T> defaultConstructor = underTest.getDeclaredConstructor();
        defaultConstructor.setAccessible(true);
        try {
            return defaultConstructor.newInstance();
        } catch (final InvocationTargetException ite) {
            throw (Exception) ite.getTargetException();
        }
    }

    private static int checkedBound(@NotNull final String bound, final String boundName) {
        return Optional.of(bound)
                .filter(s -> s.length() == 1)
                .map(String::chars)
                .orElseThrow(() -> becauseIllegal("'%s' is not a valid %s bound", bound, boundName))
                .findFirst()
                .orElseThrow(IllegalStateException::new); // unreachable
    }

    /**
     * Returns a random string with the given length in codepoints,
     * containing characters within the given bounds.
     * @param length the required length
     * @param lower the minimum single character string.
     * @param upper the maximum single character string.
     * @return a random string.
     */
    public static String randomString(final int length, final String lower, final String upper) {
        return randomString(length, checkedBound(lower, "lower"), checkedBound(upper, "upper"));
    }

    /**
     * Returns a random string with the given length in codepoints,
     * containing characters within the given bounds.
     *
     * @param length the required length.
     * @param lower  the minimum codepoint value (inclusive).
     * @param upper  the maximum codepoint value (inclusive).
     * @return a random string.
     */
    public static String randomString(final int length, final int lower, final int upper) {
        if (length < 1) {
            throw becauseIllegal("%s is not a valid length", length);
        }
        if (upper > Character.MAX_CODE_POINT) {
            throw becauseIllegal("Upper bound %s exceeds codepoint range", upper);
        }
        if (lower < 0) {
            throw becauseIllegal("Lower bound %s is less than zero", lower);
        }
        if (upper < lower) {
            throw becauseIllegal("Upper bound %s is less than lower bound %s", upper, lower);
        }
        final StringBuilder buffer = new StringBuilder();
        TEST_RANDOM.ints(lower, upper + 1)
                .sequential()
                .limit(length)
                .mapToObj(Character::toChars)
                .forEach(buffer::append);
        return buffer.toString();
    }

    /**
     * Returns a random string of the given length,
     * excluding legacy control characters and 3-4 byte characters.
     *
     * @param length the required length.
     * @return a random string.
     */
    public static String randomString(final int length) {
        return randomString(length, 32, Character.MAX_VALUE);
    }
}
