package net.zethmayr.fungu.test;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.StringDescription;
import org.junit.jupiter.api.Test;

import static net.zethmayr.fungu.test.MatcherFactory.has;
import static net.zethmayr.fungu.test.MatcherFactory.hasNull;
import static net.zethmayr.fungu.test.TestConstants.*;
import static net.zethmayr.fungu.test.TestHelper.invokeDefaultConstructor;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

class MatcherFactoryTest {

    private static final String EXPECTED_UNEXPECTED_JOINER = " result was ";
    private static final String WAS_NULL = "was null";

    @Test
    void matcherFactory_whenInstantiated_throwsInstead() {
        assertThrows(UnsupportedOperationException.class, () ->

                invokeDefaultConstructor(MatcherFactory.class));
    }

    @Test
    void has_givenAccessAndMatcher_givenNull_doesNotMatchAndDescribesAsNull() {
        final Matcher<Object> hashesZero = has(Object::hashCode, equalTo(0));
        final Object testValue = NULL_OBJECT;

        assertFalse(hashesZero.matches(testValue));
        assertThat(testValue, not(hashesZero));
        final Description mismatch = new StringDescription();
        hashesZero.describeMismatch(testValue, mismatch);
        assertThat(mismatch.toString(), containsString(WAS_NULL));
    }

    @Test
    void has_givenAccessAndMatcher_givenNonMatching_doesNotMatchAndDescribesMismatch() {
        final Matcher<Object> hashesZero = has(Object::hashCode, equalTo(0));
        final Object testValue = EXPECTED;

        assertFalse(hashesZero.matches(testValue));
        assertThat(testValue, not(hashesZero));
        final Description mismatch = new StringDescription();
        hashesZero.describeMismatch(testValue, mismatch);
        assertThat(mismatch.toString(), allOf(
                containsString(EXPECTED),
                containsString(EXPECTED_UNEXPECTED_JOINER)
        ));
    }

    @Test
    void has_givenAccessAndMatcher_givenMatching_matchesAndDescribesRequirement() {
        final Matcher<String> notShort = has(String::length, greaterThan(2));
        final String testValue = EXPECTED;

        assertTrue(notShort.matches(testValue));
        assertThat(testValue, notShort);
        final Description description = new StringDescription();
        notShort.describeTo(description);
        assertThat(description.toString(), containsString("exposes a value"));
    }

    @Test
    void has_givenAccessAndValue_givenNull_doesNotMatchAndDescribesAsNull() {
        final Matcher<String> notEmpty = has(String::length, greaterThan(0));
        final String testValue = NULL_STRING;

        assertFalse(notEmpty.matches(testValue));
        assertThat(testValue, not(notEmpty));
        final Description mismatch = new StringDescription();
        notEmpty.describeMismatch(testValue, mismatch);
        assertThat(mismatch.toString(), containsString(WAS_NULL));
    }

    @Test
    void has_givenAccessAndValue_givenNotMatching_doesNotMatchAndDescribesMismatch() {
        final Matcher<String> hardToGuess = has(String::toString, SHIBBOLETH);
        final String testValue = EXPECTED;

        assertFalse(hardToGuess.matches(testValue));
        assertThat(testValue, not(hardToGuess));
        final Description mismatch = new StringDescription();
        hardToGuess.describeMismatch(testValue, mismatch);
        assertThat(mismatch.toString(), stringContainsInOrder(
                testValue, EXPECTED_UNEXPECTED_JOINER, testValue
        ));
    }

    @Test
    void has_givenAccessAndNull_givenNotMatching_doesNotMatchAndDescribesMismatch() {
        final Matcher<String> isNotThatString = has(Object::toString, NULL_OBJECT);
        final String testValue = EXPECTED;

        assertFalse(isNotThatString.matches(testValue));
        assertThat(testValue, not(isNotThatString));
        final Description mismatch = new StringDescription();
        isNotThatString.describeMismatch(testValue, mismatch);
        assertThat(mismatch.toString(), stringContainsInOrder(
                testValue, EXPECTED_UNEXPECTED_JOINER, testValue
        ));
    }

    @Test
    void hasNull_givenAccess_givenMatching_matchesAndDescribesRequirement() {
        final Matcher<Irrelevant> hasNullField = hasNull(Irrelevant::getField);
        final Irrelevant testValue = new Irrelevant();

        assertTrue(hasNullField.matches(testValue));
        assertThat(testValue, hasNullField);
        final Description expectation = new StringDescription();
        hasNullField.describeTo(expectation);
        assertThat(expectation.toString(), is("exposes null"));
    }
}
