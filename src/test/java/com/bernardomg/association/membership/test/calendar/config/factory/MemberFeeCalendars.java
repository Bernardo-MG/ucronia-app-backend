
package com.bernardomg.association.membership.test.calendar.config.factory;

import java.time.YearMonth;
import java.util.List;

import com.bernardomg.association.membership.test.member.config.factory.MemberConstants;
import com.bernardomg.association.model.fee.FeeMonth;
import com.bernardomg.association.model.fee.MemberFeeCalendar;

public final class MemberFeeCalendars {

    public static final MemberFeeCalendar active() {
        return MemberFeeCalendar.builder()
            .memberNumber(MemberConstants.NUMBER)
            .fullName(MemberCalendars.FULL_NAME)
            .year(MemberCalendars.YEAR)
            .active(true)
            .build();
    }

    public static final MemberFeeCalendar activeAlternative() {
        return MemberFeeCalendar.builder()
            .memberNumber(MemberConstants.ALTERNATIVE_NUMBER)
            .fullName(MemberCalendars.FULL_NAME_ALTERNATIVE)
            .year(MemberCalendars.YEAR)
            .active(true)
            .build();
    }

    public static final MemberFeeCalendar activeCurrentMonth() {
        return MemberFeeCalendar.builder()
            .memberNumber(MemberConstants.NUMBER)
            .fullName(MemberCalendars.FULL_NAME)
            .year(MemberCalendars.CURRENT_DATE.getYear())
            .active(true)
            .build();
    }

    public static final MemberFeeCalendar activeNextYear() {
        return MemberFeeCalendar.builder()
            .memberNumber(MemberConstants.NUMBER)
            .fullName(MemberCalendars.FULL_NAME)
            .year(MemberCalendars.NEXT_YEAR_DATE.getYear())
            .active(true)
            .build();
    }

    public static final MemberFeeCalendar activePreviousMonth() {
        return MemberFeeCalendar.builder()
            .memberNumber(MemberConstants.NUMBER)
            .fullName(MemberCalendars.FULL_NAME)
            .year(MemberCalendars.PREVIOUS_MONTH_DATE.getYear())
            .active(true)
            .build();
    }

    public static final MemberFeeCalendar currentActive() {
        return MemberFeeCalendar.builder()
            .memberNumber(MemberConstants.NUMBER)
            .fullName(MemberCalendars.FULL_NAME)
            .year(YearMonth.now()
                .getYear())
            .active(true)
            .build();
    }

    public static final MemberFeeCalendar currentInactive() {
        return MemberFeeCalendar.builder()
            .memberNumber(MemberConstants.NUMBER)
            .fullName(MemberCalendars.FULL_NAME)
            .year(YearMonth.now()
                .getYear())
            .active(false)
            .build();
    }

    public static final MemberFeeCalendar fullCalendar() {
        return MemberFeeCalendar.builder()
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

    public static final MemberFeeCalendar inactive() {
        return MemberFeeCalendar.builder()
            .memberNumber(MemberConstants.NUMBER)
            .fullName(MemberCalendars.FULL_NAME)
            .year(MemberCalendars.YEAR)
            .active(false)
            .build();
    }

    public static final MemberFeeCalendar inactiveAlternative() {
        return MemberFeeCalendar.builder()
            .memberNumber(MemberConstants.ALTERNATIVE_NUMBER)
            .fullName(MemberCalendars.FULL_NAME_ALTERNATIVE)
            .year(MemberCalendars.YEAR)
            .active(false)
            .build();
    }

    public static final MemberFeeCalendar inactiveNextYear() {
        return MemberFeeCalendar.builder()
            .memberNumber(MemberConstants.NUMBER)
            .fullName(MemberCalendars.FULL_NAME)
            .year(MemberCalendars.NEXT_YEAR_DATE.getYear())
            .active(false)
            .build();
    }

    public static final MemberFeeCalendar inactivePreviousMonth() {
        return MemberFeeCalendar.builder()
            .memberNumber(MemberConstants.NUMBER)
            .fullName(MemberCalendars.FULL_NAME)
            .year(MemberCalendars.PREVIOUS_MONTH_DATE.getYear())
            .active(false)
            .build();
    }

    public static final MemberFeeCalendar inactivePreviousYear() {
        return MemberFeeCalendar.builder()
            .memberNumber(MemberConstants.NUMBER)
            .fullName(MemberCalendars.FULL_NAME)
            .year(MemberCalendars.YEAR_PREVIOUS)
            .active(false)
            .build();
    }

    public static final MemberFeeCalendar noSurname() {
        return MemberFeeCalendar.builder()
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
