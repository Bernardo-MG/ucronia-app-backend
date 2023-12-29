
package com.bernardomg.association.membership.test.calendar.util.assertion;

import org.assertj.core.api.SoftAssertions;

import com.bernardomg.association.membership.calendar.model.FeeMonth;

public final class FeeMonthAssertions {

    public static final void isEqualTo(final FeeMonth received, final FeeMonth expected) {
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(received.getDate())
                .as("date")
                .isEqualTo(expected.getDate());
            softly.assertThat(received.getMemberId())
                .as("member id")
                .isEqualTo(expected.getMemberId());
            softly.assertThat(received.getMonth())
                .as("month")
                .isEqualTo(expected.getMonth());
            softly.assertThat(received.isPaid())
                .as("paid")
                .isEqualTo(expected.isPaid());
        });
    }

}
