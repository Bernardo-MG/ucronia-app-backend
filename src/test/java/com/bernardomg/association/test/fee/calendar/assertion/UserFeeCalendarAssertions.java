
package com.bernardomg.association.test.fee.calendar.assertion;

import org.assertj.core.api.Assertions;

import com.bernardomg.association.fee.calendar.model.UserFeeCalendar;

public final class UserFeeCalendarAssertions {

    public static final void isEqualTo(final UserFeeCalendar received, final UserFeeCalendar expected) {
        Assertions.assertThat(received.getMemberId())
            .isEqualTo(expected.getMemberId());
        Assertions.assertThat(received.getName())
            .isEqualTo(expected.getName());
        Assertions.assertThat(received.getSurname())
            .isEqualTo(expected.getSurname());
        Assertions.assertThat(received.getYear())
            .isEqualTo(expected.getYear());
        Assertions.assertThat(received.getActive())
            .isEqualTo(expected.getActive());
    }

}
