
package com.bernardomg.association.membership.test.calendar.util.assertion;

import org.assertj.core.api.Assertions;

import com.bernardomg.association.membership.calendar.model.FeeMonth;

public final class FeeMonthAssertions {

    public static final void isEqualTo(final FeeMonth received, final FeeMonth expected) {
        Assertions.assertThat(received.getFeeId())
            .as("id")
            .isNotNull();
        Assertions.assertThat(received.getMonth())
            .as("month")
            .isEqualTo(expected.getMonth());
        Assertions.assertThat(received.isPaid())
            .as("paid")
            .isEqualTo(expected.isPaid());
    }

}
