package net.zethmayr.fungu.test;

import org.junit.jupiter.api.Test;

import static net.zethmayr.fungu.test.TestConstants.EXPECTED;
import static org.junit.jupiter.api.Assertions.*;

class TestRuntimeExceptionTest {

    @Test
    void testRuntimeException_givenMessage_createsInstanceWithMessageAndNoCause() {

        final TestRuntimeException underTest = new TestRuntimeException(EXPECTED);

        assertEquals(EXPECTED, underTest.getMessage());
        assertNull(underTest.getCause());
    }

    @Test
    void testRuntimeException_givenCause_createsInstanceWithCauseAndCauseMessage() {
        final Exception cause = new Exception();

        final TestRuntimeException underTest = new TestRuntimeException(cause);

        assertEquals(cause, underTest.getCause());
        assertEquals(cause.toString(), underTest.getMessage());
    }

    @Test
    void testRuntimeException_givenMessageAndCause_createsInstanceWithMessageAndCause() {
        final Exception cause = new Exception();

        final TestRuntimeException underTest = new TestRuntimeException(EXPECTED, cause);

        assertEquals(EXPECTED, underTest.getMessage());
        assertEquals(cause, underTest.getCause());
    }
}
