package net.zethmayr.fungu.test;

import org.jetbrains.annotations.NotNull;

import static java.lang.String.format;

class TestExceptionFactory {

    private TestExceptionFactory() {
        throw becauseFactory();
    }

    private static final String NOT_INSTANTIABLE_F = "This test class %s and cannot be instantiated.";

    static final String CONSTANTS_ONLY = notInstantiableAndReason("only defines constants");

    static final String FACTORY_METHODS_ONLY = notInstantiableAndReason("only supplies factory methods");

    static final String STATICS_ONLY = notInstantiableAndReason("has only static members");

    @NotNull
    private static String notInstantiableAndReason(@NotNull final String specificReason) {
        return format(NOT_INSTANTIABLE_F, specificReason);
    }

    static UnsupportedOperationException becauseUnsupported(@NotNull final String reason) {
        return new UnsupportedOperationException(reason);
    }

    static UnsupportedOperationException becauseConstantsOnly() {
        return becauseUnsupported(CONSTANTS_ONLY);
    }

    static UnsupportedOperationException becauseFactory() {
        return becauseUnsupported(FACTORY_METHODS_ONLY);
    }


    static UnsupportedOperationException becauseStaticsOnly() {
        return becauseUnsupported(STATICS_ONLY);
    }

    static IllegalArgumentException becauseIllegal(final String messageFormat, final Object... details) {
        return new IllegalArgumentException(format(messageFormat, details));
    }
}
