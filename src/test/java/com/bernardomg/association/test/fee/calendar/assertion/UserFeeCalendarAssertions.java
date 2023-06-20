
package com.bernardomg.association.test.fee.calendar.assertion;

import java.util.Iterator;

import org.assertj.core.api.Assertions;

import com.bernardomg.association.fee.calendar.model.FeeMonth;
import com.bernardomg.association.fee.calendar.model.UserFeeCalendar;

public final class UserFeeCalendarAssertions {

    public static final void assertFullYear(final UserFeeCalendar result) {
        final Iterator<FeeMonth> months;
        FeeMonth                 month;

        months = result.getMonths()
            .iterator();

        month = months.next();
        Assertions.assertThat(month.getMonth())
            .isEqualTo(1);
        Assertions.assertThat(month.getPaid())
            .isTrue();

        month = months.next();
        Assertions.assertThat(month.getMonth())
            .isEqualTo(2);
        Assertions.assertThat(month.getPaid())
            .isTrue();

        month = months.next();
        Assertions.assertThat(month.getMonth())
            .isEqualTo(3);
        Assertions.assertThat(month.getPaid())
            .isTrue();

        month = months.next();
        Assertions.assertThat(month.getMonth())
            .isEqualTo(4);
        Assertions.assertThat(month.getPaid())
            .isTrue();

        month = months.next();
        Assertions.assertThat(month.getMonth())
            .isEqualTo(5);
        Assertions.assertThat(month.getPaid())
            .isTrue();

        month = months.next();
        Assertions.assertThat(month.getMonth())
            .isEqualTo(6);
        Assertions.assertThat(month.getPaid())
            .isTrue();

        month = months.next();
        Assertions.assertThat(month.getMonth())
            .isEqualTo(7);
        Assertions.assertThat(month.getPaid())
            .isTrue();

        month = months.next();
        Assertions.assertThat(month.getMonth())
            .isEqualTo(8);
        Assertions.assertThat(month.getPaid())
            .isTrue();

        month = months.next();
        Assertions.assertThat(month.getMonth())
            .isEqualTo(9);
        Assertions.assertThat(month.getPaid())
            .isTrue();

        month = months.next();
        Assertions.assertThat(month.getMonth())
            .isEqualTo(10);
        Assertions.assertThat(month.getPaid())
            .isTrue();

        month = months.next();
        Assertions.assertThat(month.getMonth())
            .isEqualTo(11);
        Assertions.assertThat(month.getPaid())
            .isTrue();

        month = months.next();
        Assertions.assertThat(month.getMonth())
            .isEqualTo(12);
        Assertions.assertThat(month.getPaid())
            .isTrue();
    }

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
