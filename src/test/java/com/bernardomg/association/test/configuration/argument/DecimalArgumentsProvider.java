
package com.bernardomg.association.test.configuration.argument;

import java.util.stream.Stream;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

public final class DecimalArgumentsProvider implements ArgumentsProvider {

    @Override
    public final Stream<? extends Arguments> provideArguments(final ExtensionContext context) throws Exception {
        return Stream.of(Arguments.of(1.1f), Arguments.of(1.11f), Arguments.of(1.12f), Arguments.of(1.19f),
            Arguments.of(1.2f), Arguments.of(1.3f), Arguments.of(1.4f), Arguments.of(1.5f), Arguments.of(1.6f),
            Arguments.of(1.7f), Arguments.of(1.8f), Arguments.of(1.9f), Arguments.of(13.6f), Arguments.of(-40.8f));
    }

}
