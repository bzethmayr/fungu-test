package net.zethmayr.fungu.test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

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
}
