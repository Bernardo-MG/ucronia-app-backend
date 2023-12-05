
package com.bernardomg.association.funds.test.configuration.argument;

import java.time.YearMonth;
import java.util.stream.Stream;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

public final class CurrentAndPreviousMonthProvider implements ArgumentsProvider {

    @Override
    public final Stream<? extends Arguments> provideArguments(final ExtensionContext context) throws Exception {
        return Stream.of(
            // This month
            Arguments.of(YearMonth.now()),
            // Previous month
            Arguments.of(YearMonth.now()
                .minusMonths(1)));
    }

}
