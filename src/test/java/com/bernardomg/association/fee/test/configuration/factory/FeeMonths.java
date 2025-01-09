
package com.bernardomg.association.fee.test.configuration.factory;

import java.time.YearMonth;

import com.bernardomg.association.fee.domain.model.FeeCalendar.FeeCalendarMonth;
import com.bernardomg.association.member.test.configuration.factory.MemberCalendars;

public final class FeeMonths {

    public static final FeeCalendarMonth notPaid() {
        // TODO: don't use member dates
        return new FeeCalendarMonth(MemberCalendars.CURRENT_DATE, false);
    }

    public static final FeeCalendarMonth notPaidAlternative() {
        return new FeeCalendarMonth(MemberCalendars.CURRENT_DATE, false);
    }

    public static final FeeCalendarMonth notPaidCurrent() {
        return new FeeCalendarMonth(MemberCalendars.CURRENT_DATE, false);
    }

    public static final FeeCalendarMonth notPaidNextYear() {
        return new FeeCalendarMonth(MemberCalendars.NEXT_YEAR_DATE, false);
    }

    public static final FeeCalendarMonth notPaidPreviousMonth() {
        return new FeeCalendarMonth(MemberCalendars.PREVIOUS_MONTH_DATE, false);
    }

    public static final FeeCalendarMonth notPaidTwoMonthsBack() {
        return new FeeCalendarMonth(MemberCalendars.TWO_MONTHS_BACK_DATE, false);
    }

    public static final FeeCalendarMonth paid() {
        return new FeeCalendarMonth(MemberCalendars.CURRENT_DATE, true);
    }

    public static final FeeCalendarMonth paid(final int year, final int month) {
        return new FeeCalendarMonth(YearMonth.of(year, month), true);
    }

    public static final FeeCalendarMonth paidAlternative() {
        return new FeeCalendarMonth(MemberCalendars.CURRENT_DATE, true);
    }

    public static final FeeCalendarMonth paidAtMonth(final int month) {
        return new FeeCalendarMonth(YearMonth.of(FeeConstants.YEAR_VALUE, month), true);
    }

    public static final FeeCalendarMonth paidCurrent() {
        return new FeeCalendarMonth(MemberCalendars.CURRENT_DATE, true);
    }

    public static final FeeCalendarMonth paidNextYear() {
        return new FeeCalendarMonth(MemberCalendars.NEXT_YEAR_DATE, true);
    }

    public static final FeeCalendarMonth paidPreviousMonth() {
        return new FeeCalendarMonth(MemberCalendars.PREVIOUS_MONTH_DATE, true);
    }

    public static final FeeCalendarMonth paidTwoMonthsBack() {
        return new FeeCalendarMonth(MemberCalendars.TWO_MONTHS_BACK_DATE, true);
    }

    private FeeMonths() {
        super();
    }

}
