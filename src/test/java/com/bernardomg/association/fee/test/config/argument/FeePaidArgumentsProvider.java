
package com.bernardomg.association.fee.test.config.argument;

import java.util.stream.Stream;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

public final class FeePaidArgumentsProvider implements ArgumentsProvider {

    @Override
    public final Stream<? extends Arguments> provideArguments(final ExtensionContext context) throws Exception {
        return Stream.of(Arguments.of(true), Arguments.of(false));
    }

}
