package net.zethmayr.fungu.test;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

import static java.lang.Integer.min;
import static java.lang.Thread.currentThread;
import static java.util.concurrent.Executors.newFixedThreadPool;
import static java.util.concurrent.TimeUnit.MINUTES;
import static java.util.stream.IntStream.range;
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
    public static <T> T invokeDefaultConstructor(final Class<T> underTest) throws Exception {
        final Constructor<T> defaultConstructor = underTest.getDeclaredConstructor();
        defaultConstructor.setAccessible(true);
        try {
            return defaultConstructor.newInstance();
        } catch (final InvocationTargetException ite) {
            throw (Exception) ite.getTargetException();
        }
    }

    /**
     * Returns the first and only codepoint in the string, or throws.
     *
     * @param bound     a bounding string expected to contain one codepoint.
     * @param boundName what sort of bound this is.
     * @return the first and only codepoint
     * @throws IllegalArgumentException if the string does not represent a codepoint.
     */
    private static int checkedBound(@NotNull final String bound, final String boundName) {
        return Optional.of(bound)
                .filter(s -> s.length() == 1)
                .map(String::codePoints)
                .orElseThrow(() -> becauseIllegal("'%s' is not a valid %s bound", bound, boundName))
                .findFirst()
                .orElseThrow(IllegalStateException::new); // unreachable
    }

    /**
     * Returns a random string with the given length in codepoints,
     * containing characters within the given bounds.
     *
     * @param length the required length
     * @param lower  the minimum single character string.
     * @param upper  the maximum single character string.
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
                .forEach(buffer::appendCodePoint);
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

    /**
     * Runs the given tests
     * using as many threads as tests,
     * with a timeout of three minutes.
     *
     * @param tests some tests.
     * @return false if the timeout was exceeded, otherwise true.
     */
    public static boolean concurrently(final Runnable... tests) {
        return concurrently(tests.length, tests);
    }

    /**
     * Runs the given tests
     * on up to the given maximum number of threads,
     * with a timeout of three minutes.
     *
     * @param max   the maximum thread count.
     * @param tests some tests.
     * @return false if the timeout was exceeded, otherwise true.
     */
    public static boolean concurrently(final int max, final Runnable... tests) {
        return concurrently(max, 3L, MINUTES, tests);
    }

    /**
     * Runs the given tests
     * on up to the given maximum number of threads,
     * with the given timeout.
     *
     * @param max    the maximum thread count.
     * @param length the length of the timeout.
     * @param units  the units of the timeout.
     * @param tests  some tests.
     * @return false if the timeout was exceeded, otherwise true.
     */
    public static boolean concurrently(final int max, final long length, final TimeUnit units, final Runnable... tests) {
        final int realMax = min(max, tests.length);
        final ExecutorService runner = newFixedThreadPool(realMax);
        range(0, realMax).forEach(x -> runner.submit(() -> {
        }));
        for (Runnable each : tests) {
            runner.submit(each);
        }
        runner.shutdown();
        try {
            return runner.awaitTermination(length, units);
        } catch (final InterruptedException thrown) {
            currentThread().interrupt();
        }
        return false;
    }
}
