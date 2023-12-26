
package com.bernardomg.association.membership.test.fee.config.argument;

import java.util.stream.Stream;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

public final class FeeMonthPaidArgumentsProvider implements ArgumentsProvider {

    @Override
    public final Stream<? extends Arguments> provideArguments(final ExtensionContext context) throws Exception {
        return Stream.of(Arguments.of(false, false), Arguments.of(true, false), Arguments.of(false, true),
            Arguments.of(true, true));
    }

}
