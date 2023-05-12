package net.zethmayr.fungu.test;

import org.hamcrest.Description;
import org.hamcrest.DiagnosingMatcher;
import org.hamcrest.Matcher;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.function.Function;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static net.zethmayr.fungu.test.TestConstants.NULL_OBJECT;
import static net.zethmayr.fungu.test.TestExceptionFactory.becauseFactory;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

/**
 * Provides composable matchers allowing field traversal.
 */
public final class MatcherFactory {
    private MatcherFactory() {
        throw becauseFactory();
    }

    /**
     * Returns a matcher which
     * evaluates a method of the item under test
     * using the matcher provided.
     *
     * @param access        a function to apply
     * @param resultMatcher a result matcher
     * @param <T>           the item type
     * @param <R>           the result type
     * @return a matcher against results
     */
    @NotNull
    public static <T, R> Matcher<T> has(
            @NotNull final Function<T, ? extends R> access, final Matcher<R> resultMatcher
    ) {
        return Optional.ofNullable(resultMatcher)
                .map(m -> new ResultMatcher<>(access, m))
                .orElseThrow(() -> new IllegalArgumentException("resultMatcher may not be null"));
    }

    /**
     * Returns a matcher which
     * evaluates a method of the item under test
     * against the value provided.
     *
     * @param access      a function to apply
     * @param resultValue the expected result
     * @param <T>         the item type
     * @param <R>         the result type
     * @return a matcher against results
     */
    @NotNull
    public static <T, R> Matcher<T> has(
            @NotNull final Function<T, ? extends R> access, @Nullable final R resultValue
    ) {
        return new ResultMatcher<>(
                access,
                nonNull(resultValue)
                        ? is(resultValue)
                        : nullValue()
        );
    }

    /**
     * Returns a matcher which
     * evaluates a method of the item under test
     * expecting a null result.
     *
     * @param access a function to apply
     * @param <T>    the item type
     * @param <R>    the result type
     * @return a matcher against results
     */
    @NotNull
    public static <T, R> Matcher<T> hasNull(
            @NotNull final Function<T, ? extends R> access
    ) {
        /*
         * Because the value is declared explicitly as an object,
         * this calling convention does not conflict with the Matcher overload.
         * The other simple convention, providing an untyped null,
         * targets the Matcher overload and will fail.
         */
        return has(access, NULL_OBJECT);
    }

    private static final class ResultMatcher<T, R> extends DiagnosingMatcher<T> {
        private final Function<T, ? extends R> access;
        private final Matcher<R> matcher;

        @SuppressWarnings("unchecked") // Object will always be less specific than R here
        public ResultMatcher(
                final Function<T, ? extends R> access, final Matcher<R> matcher
        ) {
            this.access = access;
            this.matcher = nonNull(matcher) ? matcher : (Matcher<R>) nullValue();
        }

        @Override
        @SuppressWarnings("unchecked") // Only type-safe constructors are exposed
        public boolean matches(final Object item, final Description description) {
            if (isNull(item)) {
                description.appendText("was null");
                return false;
            } else {
                final R value = access.apply((T) item);
                final boolean matched = matcher.matches(value);
                if (!matched) {
                    description.appendValue(item)
                            .appendText(" result ");
                    matcher.describeMismatch(value, description);
                }
                return matched;
            }
        }

        @Override
        public void describeTo(final Description description) {
            description.appendText("exposes ").appendDescriptionOf(matcher);
        }
    }
}
