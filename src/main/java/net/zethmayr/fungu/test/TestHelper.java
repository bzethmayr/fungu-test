package net.zethmayr.fungu.test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import static net.zethmayr.fungu.test.TestExceptionFactory.becauseStaticsOnly;

public final class TestHelper {
    private TestHelper() {
        throw becauseStaticsOnly();
    }

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
