
package com.bernardomg.association.membership.test.calendar.util.assertion;

import java.util.Iterator;

import org.assertj.core.api.Assertions;

import com.bernardomg.association.membership.calendar.model.FeeMonth;
import com.bernardomg.association.membership.calendar.model.MemberFeeCalendar;

public final class UserFeeCalendarAssertions {

    public static final void assertFullYear(final MemberFeeCalendar result) {
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

    public static final void isEqualTo(final MemberFeeCalendar received, final MemberFeeCalendar expected) {
        Assertions.assertThat(received.getMemberId())
            .isEqualTo(expected.getMemberId());
        Assertions.assertThat(received.getMemberName())
            .isEqualTo(expected.getMemberName());
        Assertions.assertThat(received.getYear())
            .isEqualTo(expected.getYear());
    }

}
