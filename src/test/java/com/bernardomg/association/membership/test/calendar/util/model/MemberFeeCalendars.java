
package com.bernardomg.association.membership.test.calendar.util.model;

import java.time.YearMonth;
import java.util.List;

import com.bernardomg.association.membership.calendar.model.FeeMonth;
import com.bernardomg.association.membership.calendar.model.MemberFeeCalendar;
import com.bernardomg.association.membership.test.fee.util.model.Fees;

public final class MemberFeeCalendars {

    public static final MemberFeeCalendar active() {
        return MemberFeeCalendar.builder()
            .memberId(1L)
            .memberName(MemberCalendars.FULL_NAME)
            .year(MemberCalendars.YEAR)
            .active(true)
            .build();
    }

    public static final MemberFeeCalendar activeAlternative() {
        return MemberFeeCalendar.builder()
            .memberId(2L)
            .memberName(MemberCalendars.FULL_NAME_ALTERNATIVE)
            .year(MemberCalendars.YEAR)
            .active(true)
            .build();
    }

    public static final MemberFeeCalendar activeCurrentMonth() {
        return MemberFeeCalendar.builder()
            .memberId(1L)
            .memberName(MemberCalendars.FULL_NAME)
            .year(Fees.CURRENT_YEAR.getValue())
            .active(true)
            .build();
    }

    public static final MemberFeeCalendar activeNextYear() {
        return MemberFeeCalendar.builder()
            .memberId(1L)
            .memberName(MemberCalendars.FULL_NAME)
            .year(Fees.NEXT_YEAR.getValue())
            .active(true)
            .build();
    }

    public static final MemberFeeCalendar activePreviousMonth() {
        return MemberFeeCalendar.builder()
            .memberId(1L)
            .memberName(MemberCalendars.FULL_NAME)
            .year(Fees.PREVIOUS_MONTH.getYear())
            .active(true)
            .build();
    }

    public static final MemberFeeCalendar currentActive() {
        return MemberFeeCalendar.builder()
            .memberId(1L)
            .memberName(MemberCalendars.FULL_NAME)
            .year(YearMonth.now()
                .getYear())
            .active(true)
            .build();
    }

    public static final MemberFeeCalendar currentInactive() {
        return MemberFeeCalendar.builder()
            .memberId(1L)
            .memberName(MemberCalendars.FULL_NAME)
            .year(YearMonth.now()
                .getYear())
            .active(false)
            .build();
    }

    public static final MemberFeeCalendar fullCalendar() {
        return MemberFeeCalendar.builder()
            .memberId(1L)
            .memberName(MemberCalendars.FULL_NAME)
            .year(MemberCalendars.YEAR)
            .active(false)
            .months(List.of(FeeMonth.builder()
                .date(Fees.DATE)
                .memberId(1l)
                .month(1)
                .paid(true)
                .build(),
                FeeMonth.builder()
                    .date(Fees.DATE)
                    .memberId(1l)
                    .month(2)
                    .paid(true)
                    .build(),
                FeeMonth.builder()
                    .date(Fees.DATE)
                    .memberId(1l)
                    .month(3)
                    .paid(true)
                    .build(),
                FeeMonth.builder()
                    .date(Fees.DATE)
                    .memberId(1l)
                    .month(4)
                    .paid(true)
                    .build(),
                FeeMonth.builder()
                    .date(Fees.DATE)
                    .memberId(1l)
                    .month(5)
                    .paid(true)
                    .build(),
                FeeMonth.builder()
                    .date(Fees.DATE)
                    .memberId(1l)
                    .month(6)
                    .paid(true)
                    .build(),
                FeeMonth.builder()
                    .date(Fees.DATE)
                    .memberId(1l)
                    .month(7)
                    .paid(true)
                    .build(),
                FeeMonth.builder()
                    .date(Fees.DATE)
                    .memberId(1l)
                    .month(8)
                    .paid(true)
                    .build(),
                FeeMonth.builder()
                    .date(Fees.DATE)
                    .memberId(1l)
                    .month(9)
                    .paid(true)
                    .build(),
                FeeMonth.builder()
                    .date(Fees.DATE)
                    .memberId(1l)
                    .month(10)
                    .paid(true)
                    .build(),
                FeeMonth.builder()
                    .date(Fees.DATE)
                    .memberId(1l)
                    .month(11)
                    .paid(true)
                    .build(),
                FeeMonth.builder()
                    .date(Fees.DATE)
                    .memberId(1l)
                    .month(12)
                    .paid(true)
                    .build()))
            .build();
    }

    public static final MemberFeeCalendar inactive() {
        return MemberFeeCalendar.builder()
            .memberId(1L)
            .memberName(MemberCalendars.FULL_NAME)
            .year(MemberCalendars.YEAR)
            .active(false)
            .build();
    }

    public static final MemberFeeCalendar inactiveAlternative() {
        return MemberFeeCalendar.builder()
            .memberId(2L)
            .memberName(MemberCalendars.FULL_NAME_ALTERNATIVE)
            .year(MemberCalendars.YEAR)
            .active(false)
            .build();
    }

    public static final MemberFeeCalendar inactiveNextYear() {
        return MemberFeeCalendar.builder()
            .memberId(1L)
            .memberName(MemberCalendars.FULL_NAME)
            .year(Fees.NEXT_YEAR.getValue())
            .active(false)
            .build();
    }

    public static final MemberFeeCalendar inactivePreviousMonth() {
        return MemberFeeCalendar.builder()
            .memberId(1L)
            .memberName(MemberCalendars.FULL_NAME)
            .year(Fees.PREVIOUS_MONTH.getYear())
            .active(false)
            .build();
    }

    public static final MemberFeeCalendar inactivePreviousYear() {
        return MemberFeeCalendar.builder()
            .memberId(1L)
            .memberName(MemberCalendars.FULL_NAME)
            .year(MemberCalendars.YEAR_PREVIOUS)
            .active(false)
            .build();
    }

    public static final MemberFeeCalendar noSurname() {
        return MemberFeeCalendar.builder()
            .memberId(1L)
            .memberName(MemberCalendars.NAME)
            .year(MemberCalendars.YEAR)
            .active(false)
            .build();
    }

    private MemberFeeCalendars() {
        super();
    }

}
