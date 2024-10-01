
package com.bernardomg.association.fee.test.configuration.factory;

import java.time.YearMonth;

import com.bernardomg.association.fee.domain.model.FeeCalendar.FeeCalendarMonth;
import com.bernardomg.association.fee.domain.model.FeeCalendar.FeeCalendarMonth.FeeCalendarMonthFee;
import com.bernardomg.association.member.test.configuration.factory.MemberCalendars;

public final class FeeMonths {

    public static final FeeCalendarMonth notPaid() {
        final FeeCalendarMonthFee fee;

        fee = new FeeCalendarMonthFee(MemberCalendars.CURRENT_DATE, false);
        // TODO: don't use member dates
        return new FeeCalendarMonth(fee, MemberCalendars.CURRENT_DATE.getMonthValue());
    }

    public static final FeeCalendarMonth notPaidAlternative() {
        final FeeCalendarMonthFee fee;

        fee = new FeeCalendarMonthFee(MemberCalendars.CURRENT_DATE, false);
        return new FeeCalendarMonth(fee, MemberCalendars.CURRENT_DATE.getMonthValue());
    }

    public static final FeeCalendarMonth notPaidCurrent() {
        final FeeCalendarMonthFee fee;

        fee = new FeeCalendarMonthFee(MemberCalendars.CURRENT_DATE, false);
        return new FeeCalendarMonth(fee, MemberCalendars.CURRENT_DATE.getMonthValue());
    }

    public static final FeeCalendarMonth notPaidNextYear() {
        final FeeCalendarMonthFee fee;

        fee = new FeeCalendarMonthFee(MemberCalendars.NEXT_YEAR_DATE, false);
        return new FeeCalendarMonth(fee, MemberCalendars.NEXT_YEAR_DATE.getMonthValue());
    }

    public static final FeeCalendarMonth notPaidPreviousMonth() {
        final FeeCalendarMonthFee fee;

        fee = new FeeCalendarMonthFee(MemberCalendars.PREVIOUS_MONTH_DATE, false);
        return new FeeCalendarMonth(fee, MemberCalendars.PREVIOUS_MONTH_DATE.getMonthValue());
    }

    public static final FeeCalendarMonth notPaidTwoMonthsBack() {
        final FeeCalendarMonthFee fee;

        fee = new FeeCalendarMonthFee(MemberCalendars.TWO_MONTHS_BACK_DATE, false);
        return new FeeCalendarMonth(fee, MemberCalendars.TWO_MONTHS_BACK_DATE.getMonthValue());
    }

    public static final FeeCalendarMonth paid() {
        final FeeCalendarMonthFee fee;

        fee = new FeeCalendarMonthFee(MemberCalendars.CURRENT_DATE, true);
        return new FeeCalendarMonth(fee, MemberCalendars.CURRENT_DATE.getMonthValue());
    }

    public static final FeeCalendarMonth paid(final int year, final int month) {
        final FeeCalendarMonthFee fee;

        fee = new FeeCalendarMonthFee(YearMonth.of(year, month), true);
        return new FeeCalendarMonth(fee, month);
    }

    public static final FeeCalendarMonth paidAlternative() {
        final FeeCalendarMonthFee fee;

        fee = new FeeCalendarMonthFee(MemberCalendars.CURRENT_DATE, true);
        return new FeeCalendarMonth(fee, MemberCalendars.CURRENT_DATE.getMonthValue());
    }

    public static final FeeCalendarMonth paidAtMonth(final int month) {
        final FeeCalendarMonthFee fee;

        fee = new FeeCalendarMonthFee(YearMonth.of(FeeConstants.YEAR_VALUE, month), true);
        return new FeeCalendarMonth(fee, month);
    }

    public static final FeeCalendarMonth paidCurrent() {
        final FeeCalendarMonthFee fee;

        fee = new FeeCalendarMonthFee(MemberCalendars.CURRENT_DATE, true);
        return new FeeCalendarMonth(fee, MemberCalendars.CURRENT_DATE.getMonthValue());
    }

    public static final FeeCalendarMonth paidNextYear() {
        final FeeCalendarMonthFee fee;

        fee = new FeeCalendarMonthFee(MemberCalendars.NEXT_YEAR_DATE, true);
        return new FeeCalendarMonth(fee, MemberCalendars.NEXT_YEAR_DATE.getMonthValue());
    }

    public static final FeeCalendarMonth paidPreviousMonth() {
        final FeeCalendarMonthFee fee;

        fee = new FeeCalendarMonthFee(MemberCalendars.PREVIOUS_MONTH_DATE, true);
        return new FeeCalendarMonth(fee, MemberCalendars.PREVIOUS_MONTH_DATE.getMonthValue());
    }

    public static final FeeCalendarMonth paidTwoMonthsBack() {
        final FeeCalendarMonthFee fee;
        fee = new FeeCalendarMonthFee(MemberCalendars.TWO_MONTHS_BACK_DATE, true);
        return new FeeCalendarMonth(fee, MemberCalendars.TWO_MONTHS_BACK_DATE.getMonthValue());
    }

    private FeeMonths() {
        super();
    }

}
