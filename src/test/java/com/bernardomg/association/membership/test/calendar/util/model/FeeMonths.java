
package com.bernardomg.association.membership.test.calendar.util.model;

import java.time.YearMonth;

import com.bernardomg.association.membership.calendar.model.FeeMonth;

public final class FeeMonths {

    public static final FeeMonth notPaid() {
        return FeeMonth.builder()
            .date(MemberCalendars.CURRENT_DATE)
            .memberNumber(1l)
            .month(MemberCalendars.CURRENT_DATE.getMonthValue())
            .paid(false)
            .build();
    }

    public static final FeeMonth notPaidCurrent() {
        return FeeMonth.builder()
            .date(MemberCalendars.CURRENT_DATE)
            .memberNumber(1l)
            .month(MemberCalendars.CURRENT_DATE.getMonthValue())
            .paid(false)
            .build();
    }

    public static final FeeMonth notPaidNextYear() {
        return FeeMonth.builder()
            .date(MemberCalendars.NEXT_YEAR_DATE)
            .memberNumber(1l)
            .month(MemberCalendars.NEXT_YEAR_DATE.getMonthValue())
            .paid(false)
            .build();
    }

    public static final FeeMonth notPaidPreviousMonth() {
        return FeeMonth.builder()
            .date(MemberCalendars.PREVIOUS_MONTH_DATE)
            .memberNumber(1l)
            .month(MemberCalendars.PREVIOUS_MONTH_DATE.getMonthValue())
            .paid(false)
            .build();
    }

    public static final FeeMonth notPaidTwoMonthsBack() {
        return FeeMonth.builder()
            .date(MemberCalendars.TWO_MONTHS_BACK_DATE)
            .memberNumber(1l)
            .month(MemberCalendars.TWO_MONTHS_BACK_DATE.getMonthValue())
            .paid(false)
            .build();
    }

    public static final FeeMonth paid() {
        return FeeMonth.builder()
            .date(MemberCalendars.CURRENT_DATE)
            .memberNumber(1l)
            .month(MemberCalendars.CURRENT_DATE.getMonthValue())
            .paid(true)
            .build();
    }

    public static final FeeMonth paid(final int year, final int month) {
        return FeeMonth.builder()
            .date(YearMonth.of(year, month))
            .memberNumber(1l)
            .month(month)
            .paid(true)
            .build();
    }

    public static final FeeMonth paidCurrent() {
        return FeeMonth.builder()
            .date(MemberCalendars.CURRENT_DATE)
            .memberNumber(1l)
            .month(MemberCalendars.CURRENT_DATE.getMonthValue())
            .paid(true)
            .build();
    }

    public static final FeeMonth paidNextYear() {
        return FeeMonth.builder()
            .date(MemberCalendars.NEXT_YEAR_DATE)
            .memberNumber(1l)
            .month(MemberCalendars.NEXT_YEAR_DATE.getMonthValue())
            .paid(true)
            .build();
    }

    public static final FeeMonth paidPreviousMonth() {
        return FeeMonth.builder()
            .date(MemberCalendars.PREVIOUS_MONTH_DATE)
            .memberNumber(1l)
            .month(MemberCalendars.PREVIOUS_MONTH_DATE.getMonthValue())
            .paid(true)
            .build();
    }

    public static final FeeMonth paidTwoMonthsBack() {
        return FeeMonth.builder()
            .date(MemberCalendars.TWO_MONTHS_BACK_DATE)
            .memberNumber(1l)
            .month(MemberCalendars.TWO_MONTHS_BACK_DATE.getMonthValue())
            .paid(true)
            .build();
    }

    private FeeMonths() {
        super();
    }

}
