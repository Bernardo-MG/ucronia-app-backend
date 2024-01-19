
package com.bernardomg.association.fee.test.util.assertion;

import java.util.Iterator;

import org.assertj.core.api.SoftAssertions;

import com.bernardomg.association.fee.domain.model.FeeCalendar;
import com.bernardomg.association.fee.domain.model.FeeCalendarMonth;

public final class MemberFeeCalendarAssertions {

    public static final void assertFullYear(final FeeCalendar calendar) {
        SoftAssertions.assertSoftly(softly -> {
            final Iterator<FeeCalendarMonth> months;
            FeeCalendarMonth                 month;

            softly.assertThat(calendar.getMonths())
                .hasSize(12);

            months = calendar.getMonths()
                .iterator();

            month = months.next();
            softly.assertThat(month.getMonth())
                .isEqualTo(1);
            softly.assertThat(month.getFee()
                .isPaid())
                .isTrue();

            month = months.next();
            softly.assertThat(month.getMonth())
                .isEqualTo(2);
            softly.assertThat(month.getFee()
                .isPaid())
                .isTrue();

            month = months.next();
            softly.assertThat(month.getMonth())
                .isEqualTo(3);
            softly.assertThat(month.getFee()
                .isPaid())
                .isTrue();

            month = months.next();
            softly.assertThat(month.getMonth())
                .isEqualTo(4);
            softly.assertThat(month.getFee()
                .isPaid())
                .isTrue();

            month = months.next();
            softly.assertThat(month.getMonth())
                .isEqualTo(5);
            softly.assertThat(month.getFee()
                .isPaid())
                .isTrue();

            month = months.next();
            softly.assertThat(month.getMonth())
                .isEqualTo(6);
            softly.assertThat(month.getFee()
                .isPaid())
                .isTrue();

            month = months.next();
            softly.assertThat(month.getMonth())
                .isEqualTo(7);
            softly.assertThat(month.getFee()
                .isPaid())
                .isTrue();

            month = months.next();
            softly.assertThat(month.getMonth())
                .isEqualTo(8);
            softly.assertThat(month.getFee()
                .isPaid())
                .isTrue();

            month = months.next();
            softly.assertThat(month.getMonth())
                .isEqualTo(9);
            softly.assertThat(month.getFee()
                .isPaid())
                .isTrue();

            month = months.next();
            softly.assertThat(month.getMonth())
                .isEqualTo(10);
            softly.assertThat(month.getFee()
                .isPaid())
                .isTrue();

            month = months.next();
            softly.assertThat(month.getMonth())
                .isEqualTo(11);
            softly.assertThat(month.getFee()
                .isPaid())
                .isTrue();

            month = months.next();
            softly.assertThat(month.getMonth())
                .isEqualTo(12);
            softly.assertThat(month.getFee()
                .isPaid())
                .isTrue();
        });
    }

    public static final void isEqualTo(final FeeCalendar received, final FeeCalendar expected) {
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(received.getMember()
                .getNumber())
                .as("member number")
                .isEqualTo(expected.getMember()
                    .getNumber());
            softly.assertThat(received.getMember()
                .getFullName())
                .as("member name")
                .isEqualTo(expected.getMember()
                    .getFullName());
            softly.assertThat(received.getYear())
                .as("year")
                .isEqualTo(expected.getYear());
            softly.assertThat(received.getMember()
                .isActive())
                .as("active")
                .isEqualTo(expected.getMember()
                    .isActive());
        });
    }

}
