package net.zethmayr.fungu.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TestExceptionFactoryTest {

    @Test
    void testExceptionFactory_whenInstantiated_throwsInstead() {

        assertThrows(UnsupportedOperationException.class, () ->

                TestHelper.invokeDefaultConstructor(TestExceptionFactory.class));
    }

    @Test
    void becauseStaticsOnly_result_hasExpectedMessage() {

        final UnsupportedOperationException underTest = TestExceptionFactory.becauseStaticsOnly();

        Assertions.assertEquals(TestExceptionFactory.STATICS_ONLY, underTest.getMessage());
    }

    @Test
    void becauseFactory_result_hasExpectedMessage() {

        final UnsupportedOperationException underTest = TestExceptionFactory.becauseFactory();

        Assertions.assertEquals(TestExceptionFactory.FACTORY_METHODS_ONLY, underTest.getMessage());
    }

    @Test
    void becauseConstantsOnly_result_hasExpectedMessage() {

        final UnsupportedOperationException underTest = TestExceptionFactory.becauseConstantsOnly();

        Assertions.assertEquals(TestExceptionFactory.CONSTANTS_ONLY, underTest.getMessage());
    }
}
