
package com.bernardomg.association.member.test.config.factory;

import java.time.YearMonth;
import java.util.List;

import com.bernardomg.association.fee.model.FeeCalendar;
import com.bernardomg.association.fee.model.FeeMonth;

public final class MemberFeeCalendars {

    public static final FeeCalendar active() {
        return FeeCalendar.builder()
            .memberNumber(MemberConstants.NUMBER)
            .fullName(MemberCalendars.FULL_NAME)
            .year(MemberCalendars.YEAR)
            .active(true)
            .build();
    }

    public static final FeeCalendar activeAlternative() {
        return FeeCalendar.builder()
            .memberNumber(MemberConstants.ALTERNATIVE_NUMBER)
            .fullName(MemberCalendars.FULL_NAME_ALTERNATIVE)
            .year(MemberCalendars.YEAR)
            .active(true)
            .build();
    }

    public static final FeeCalendar activeCurrentMonth() {
        return FeeCalendar.builder()
            .memberNumber(MemberConstants.NUMBER)
            .fullName(MemberCalendars.FULL_NAME)
            .year(MemberCalendars.CURRENT_DATE.getYear())
            .active(true)
            .build();
    }

    public static final FeeCalendar activeNextYear() {
        return FeeCalendar.builder()
            .memberNumber(MemberConstants.NUMBER)
            .fullName(MemberCalendars.FULL_NAME)
            .year(MemberCalendars.NEXT_YEAR_DATE.getYear())
            .active(true)
            .build();
    }

    public static final FeeCalendar activePreviousMonth() {
        return FeeCalendar.builder()
            .memberNumber(MemberConstants.NUMBER)
            .fullName(MemberCalendars.FULL_NAME)
            .year(MemberCalendars.PREVIOUS_MONTH_DATE.getYear())
            .active(true)
            .build();
    }

    public static final FeeCalendar currentActive() {
        return FeeCalendar.builder()
            .memberNumber(MemberConstants.NUMBER)
            .fullName(MemberCalendars.FULL_NAME)
            .year(YearMonth.now()
                .getYear())
            .active(true)
            .build();
    }

    public static final FeeCalendar currentInactive() {
        return FeeCalendar.builder()
            .memberNumber(MemberConstants.NUMBER)
            .fullName(MemberCalendars.FULL_NAME)
            .year(YearMonth.now()
                .getYear())
            .active(false)
            .build();
    }

    public static final FeeCalendar fullCalendar() {
        return FeeCalendar.builder()
            .memberNumber(MemberConstants.NUMBER)
            .fullName(MemberCalendars.FULL_NAME)
            .year(MemberCalendars.YEAR)
            .active(false)
            .months(List.of(FeeMonth.builder()
                .date(MemberCalendars.CURRENT_DATE)
                .memberNumber(1l)
                .month(1)
                .paid(true)
                .build(),
                FeeMonth.builder()
                    .date(MemberCalendars.CURRENT_DATE)
                    .memberNumber(1l)
                    .month(2)
                    .paid(true)
                    .build(),
                FeeMonth.builder()
                    .date(MemberCalendars.CURRENT_DATE)
                    .memberNumber(1l)
                    .month(3)
                    .paid(true)
                    .build(),
                FeeMonth.builder()
                    .date(MemberCalendars.CURRENT_DATE)
                    .memberNumber(1l)
                    .month(4)
                    .paid(true)
                    .build(),
                FeeMonth.builder()
                    .date(MemberCalendars.CURRENT_DATE)
                    .memberNumber(1l)
                    .month(5)
                    .paid(true)
                    .build(),
                FeeMonth.builder()
                    .date(MemberCalendars.CURRENT_DATE)
                    .memberNumber(1l)
                    .month(6)
                    .paid(true)
                    .build(),
                FeeMonth.builder()
                    .date(MemberCalendars.CURRENT_DATE)
                    .memberNumber(1l)
                    .month(7)
                    .paid(true)
                    .build(),
                FeeMonth.builder()
                    .date(MemberCalendars.CURRENT_DATE)
                    .memberNumber(1l)
                    .month(8)
                    .paid(true)
                    .build(),
                FeeMonth.builder()
                    .date(MemberCalendars.CURRENT_DATE)
                    .memberNumber(1l)
                    .month(9)
                    .paid(true)
                    .build(),
                FeeMonth.builder()
                    .date(MemberCalendars.CURRENT_DATE)
                    .memberNumber(1l)
                    .month(10)
                    .paid(true)
                    .build(),
                FeeMonth.builder()
                    .date(MemberCalendars.CURRENT_DATE)
                    .memberNumber(1l)
                    .month(11)
                    .paid(true)
                    .build(),
                FeeMonth.builder()
                    .date(MemberCalendars.CURRENT_DATE)
                    .memberNumber(1l)
                    .month(12)
                    .paid(true)
                    .build()))
            .build();
    }

    public static final FeeCalendar inactive() {
        return FeeCalendar.builder()
            .memberNumber(MemberConstants.NUMBER)
            .fullName(MemberCalendars.FULL_NAME)
            .year(MemberCalendars.YEAR)
            .active(false)
            .build();
    }

    public static final FeeCalendar inactiveAlternative() {
        return FeeCalendar.builder()
            .memberNumber(MemberConstants.ALTERNATIVE_NUMBER)
            .fullName(MemberCalendars.FULL_NAME_ALTERNATIVE)
            .year(MemberCalendars.YEAR)
            .active(false)
            .build();
    }

    public static final FeeCalendar inactiveNextYear() {
        return FeeCalendar.builder()
            .memberNumber(MemberConstants.NUMBER)
            .fullName(MemberCalendars.FULL_NAME)
            .year(MemberCalendars.NEXT_YEAR_DATE.getYear())
            .active(false)
            .build();
    }

    public static final FeeCalendar inactivePreviousMonth() {
        return FeeCalendar.builder()
            .memberNumber(MemberConstants.NUMBER)
            .fullName(MemberCalendars.FULL_NAME)
            .year(MemberCalendars.PREVIOUS_MONTH_DATE.getYear())
            .active(false)
            .build();
    }

    public static final FeeCalendar inactivePreviousYear() {
        return FeeCalendar.builder()
            .memberNumber(MemberConstants.NUMBER)
            .fullName(MemberCalendars.FULL_NAME)
            .year(MemberCalendars.YEAR_PREVIOUS)
            .active(false)
            .build();
    }

    public static final FeeCalendar noSurname() {
        return FeeCalendar.builder()
            .memberNumber(MemberConstants.NUMBER)
            .fullName(MemberCalendars.NAME)
            .year(MemberCalendars.YEAR)
            .active(false)
            .build();
    }

    private MemberFeeCalendars() {
        super();
    }

}
