
package com.bernardomg.association.membership.test.calendar.util.assertion;

import java.util.Iterator;

import org.assertj.core.api.SoftAssertions;

import com.bernardomg.association.membership.calendar.model.FeeMonth;
import com.bernardomg.association.membership.calendar.model.MemberFeeCalendar;

public final class MemberFeeCalendarAssertions {

    public static final void assertFullYear(final MemberFeeCalendar calendar) {
        SoftAssertions.assertSoftly(softly -> {
            final Iterator<FeeMonth> months;
            FeeMonth                 month;

            softly.assertThat(calendar.getMonths())
                .hasSize(12);

            months = calendar.getMonths()
                .iterator();

            month = months.next();
            softly.assertThat(month.getMonth())
                .isEqualTo(1);
            softly.assertThat(month.isPaid())
                .isTrue();

            month = months.next();
            softly.assertThat(month.getMonth())
                .isEqualTo(2);
            softly.assertThat(month.isPaid())
                .isTrue();

            month = months.next();
            softly.assertThat(month.getMonth())
                .isEqualTo(3);
            softly.assertThat(month.isPaid())
                .isTrue();

            month = months.next();
            softly.assertThat(month.getMonth())
                .isEqualTo(4);
            softly.assertThat(month.isPaid())
                .isTrue();

            month = months.next();
            softly.assertThat(month.getMonth())
                .isEqualTo(5);
            softly.assertThat(month.isPaid())
                .isTrue();

            month = months.next();
            softly.assertThat(month.getMonth())
                .isEqualTo(6);
            softly.assertThat(month.isPaid())
                .isTrue();

            month = months.next();
            softly.assertThat(month.getMonth())
                .isEqualTo(7);
            softly.assertThat(month.isPaid())
                .isTrue();

            month = months.next();
            softly.assertThat(month.getMonth())
                .isEqualTo(8);
            softly.assertThat(month.isPaid())
                .isTrue();

            month = months.next();
            softly.assertThat(month.getMonth())
                .isEqualTo(9);
            softly.assertThat(month.isPaid())
                .isTrue();

            month = months.next();
            softly.assertThat(month.getMonth())
                .isEqualTo(10);
            softly.assertThat(month.isPaid())
                .isTrue();

            month = months.next();
            softly.assertThat(month.getMonth())
                .isEqualTo(11);
            softly.assertThat(month.isPaid())
                .isTrue();

            month = months.next();
            softly.assertThat(month.getMonth())
                .isEqualTo(12);
            softly.assertThat(month.isPaid())
                .isTrue();
        });
    }

    public static final void isEqualTo(final MemberFeeCalendar received, final MemberFeeCalendar expected) {
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(received.getMemberNumber())
                .as("member number")
                .isEqualTo(expected.getMemberNumber());
            softly.assertThat(received.getFullName())
                .as("member name")
                .isEqualTo(expected.getFullName());
            softly.assertThat(received.getYear())
                .as("year")
                .isEqualTo(expected.getYear());
            softly.assertThat(received.isActive())
                .as("active")
                .isEqualTo(expected.isActive());
        });
    }

}
