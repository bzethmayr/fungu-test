package net.zethmayr.fungu.test;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static java.lang.String.format;
import static java.util.Objects.nonNull;

class TestExceptionFactory {

    private TestExceptionFactory() {
        throw becauseFactory();
    }

    private static final String NOT_INSTANTIABLE_F = "This test class%scannot be instantiated.";
    private static final String AND_F = " %s and ";

    static final String CONSTANTS_ONLY = notInstantiableAndReason("only defines constants");

    static final String FACTORY_METHODS_ONLY = notInstantiableAndReason("only supplies factory methods");

    static final String STATICS_ONLY = notInstantiableAndReason("has only static members");

    @NotNull
    private static String notInstantiableAndReason(@Nullable final String specificReason) {
        return nonNull(specificReason)
                ? format(format(NOT_INSTANTIABLE_F, AND_F), specificReason)
                : format(NOT_INSTANTIABLE_F, " ");
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

}
