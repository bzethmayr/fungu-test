package net.zethmayr.fungu.test;

import org.junit.jupiter.api.Test;

import static net.zethmayr.fungu.test.TestConstants.EXPECTED;
import static org.junit.jupiter.api.Assertions.*;

class ExampleCheckedExceptionTest {

    @Test
    void exampleCheckedException_givenMessage_createsInstanceWithMessageAndNoCause() {

        final ExampleCheckedException underTest = new ExampleCheckedException(EXPECTED);

        assertEquals(EXPECTED, underTest.getMessage());
        assertNull(underTest.getCause());
    }

    @Test
    void exampleCheckedException_givenCause_createsInstanceWithCauseAndCauseMessage() {
        final Exception cause = new Exception();

        final ExampleCheckedException underTest = new ExampleCheckedException(cause);

        assertEquals(cause, underTest.getCause());
        assertEquals(cause.toString(), underTest.getMessage());
    }

    @Test
    void exampleCheckedException_givenMessageAndCause_createsInstanceWithMessageAndCause() {
        final Exception cause = new Exception();

        final ExampleCheckedException underTest = new ExampleCheckedException(EXPECTED, cause);

        assertEquals(EXPECTED, underTest.getMessage());
        assertEquals(cause, underTest.getCause());
    }
}
