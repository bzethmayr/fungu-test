package net.zethmayr.fungu.test;

import org.junit.jupiter.api.Test;

import static java.lang.Thread.currentThread;
import static java.util.concurrent.TimeUnit.NANOSECONDS;
import static net.zethmayr.fungu.test.TestConstants.TEST_RANDOM;
import static net.zethmayr.fungu.test.TestHelper.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

class TestHelperTest {

    @Test
    void testHelper_whenInstantiated_throwsInstead() {

        assertThrows(UnsupportedOperationException.class, () ->

                invokeDefaultConstructor(TestHelper.class));
    }

    @Test
    void randomString_givenLengthAndBounds_returnsStringOfLengthInBounds() {
        final int expectedLength = TEST_RANDOM.nextInt(5, 10);

        final String result = randomString(expectedLength, "A", "A");

        assertThat(result, allOf(
                hasLength(expectedLength),
                containsString("A")
        ));
    }

    @Test
    void randomString_givenInvalidLowerBoundingString_throwsWithLowerInMessage() {
        final IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () ->

                randomString(1, "", "z"));

        assertThat(thrown.getMessage(), stringContainsInOrder("''", "lower"));
    }

    @Test
    void randomString_givenInvalidUpperBoundingString_throwsWithUpperInMessage() {
        final IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () ->

                randomString(1, "a", ""));

        assertThat(thrown.getMessage(), stringContainsInOrder("''", "upper"));
    }

    @Test
    void randomString_givenInvalidLength_throwsWithLengthInMessage() {
        final IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () ->

                randomString(-1, 0, 0));

        assertThat(thrown.getMessage(), containsString("length"));
    }

    @Test
    void randomString_givenNegativeLowerBound_throwsWithLowerInMessage() {
        final IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () ->

                randomString(1, -1, 0));

        assertThat(thrown.getMessage(), containsStringIgnoringCase("lower"));
    }

    @Test
    void randomString_givenExcessiveUpperBound_throwsWithUpperInMessage() {
        final IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () ->

                randomString(1, 0, Integer.MAX_VALUE));

        assertThat(thrown.getMessage(), containsStringIgnoringCase("upper"));
    }

    @Test
    void randomString_givenMisalignedBounds_throwsWithBoundsInMessage() {
        final IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () ->

                randomString(1, "B", "A"));

        assertThat(thrown.getMessage(), stringContainsInOrder("bound 65", "lower bound"));
    }

    @Test
    void randomString_givenLength_returnsStringOfLength() {
        final int expectedLength = TEST_RANDOM.nextInt(5, 10);

        final String result = randomString(expectedLength);

        assertThat(result, hasLength(expectedLength));
    }

    @Test
    void concurrently_givenCompletingTests_returnsTrue() {

        assertTrue(concurrently(() -> {
        }));
    }

    @Test
    void concurrently_givenInvalidMax_throwsPerImplementation() {

        assertThrows(IllegalArgumentException.class, () ->

                concurrently(0, () -> {
                }));
    }

    @Test
    void concurrently_givenNoTasks_throwsPerImplementation() {

        assertThrows(IllegalArgumentException.class,

                TestHelper::concurrently);
    }

    @Test
    void concurrently_whenTimeout_returnsFalse() {
        assertFalse(() ->
                concurrently(1, 10, NANOSECONDS, () -> {

                    try {
                        Thread.sleep(1, 1);
                    } catch (final InterruptedException thrown) {
                        currentThread().interrupt();
                    }
                }));
    }

    @Test
    void concurrently_whenTimeoutIgnoresInterrupts_returnsFalse() {
        assertFalse(() ->
                concurrently(1, 10, NANOSECONDS, () -> {

                    try {
                        Thread.sleep(1, 1);
                    } catch (final InterruptedException thrown) {
                        assertNotNull(thrown);
                    }
                }));
    }
}
