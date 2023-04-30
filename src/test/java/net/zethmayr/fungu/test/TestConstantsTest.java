package net.zethmayr.fungu.test;

import org.junit.jupiter.api.Test;

import static net.zethmayr.fungu.test.TestHelper.invokeDefaultConstructor;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TestConstantsTest {

    @Test
    void testConstants_whenInstantiated_throwsInstead() {
        assertThrows(UnsupportedOperationException.class, () ->

                invokeDefaultConstructor(TestConstants.class));
    }
}
