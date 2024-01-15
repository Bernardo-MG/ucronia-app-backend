
package com.bernardomg.association.fee.test.config.factory;

import java.time.YearMonth;
import java.util.List;

import com.bernardomg.association.fee.model.FeeCalendar;
import com.bernardomg.association.fee.model.FeeCalendarMember;
import com.bernardomg.association.member.test.config.factory.MemberCalendars;
import com.bernardomg.association.member.test.config.factory.MemberConstants;

public final class FeeCalendars {

    public static final FeeCalendar active() {
        final FeeCalendarMember member;

        member = FeeCalendarMember.builder()
            .fullName(MemberCalendars.FULL_NAME)
            .number(MemberConstants.NUMBER)
            .active(true)
            .build();
        return FeeCalendar.builder()
            .member(member)
            .year(MemberCalendars.YEAR)
            .build();
    }

    public static final FeeCalendar activeAlternative() {
        final FeeCalendarMember member;

        member = FeeCalendarMember.builder()
            .fullName(MemberCalendars.FULL_NAME_ALTERNATIVE)
            .number(MemberConstants.ALTERNATIVE_NUMBER)
            .active(true)
            .build();
        return FeeCalendar.builder()
            .member(member)
            .year(MemberCalendars.YEAR)
            .build();
    }

    public static final FeeCalendar activeCurrentMonth() {
        final FeeCalendarMember member;

        member = FeeCalendarMember.builder()
            .fullName(MemberCalendars.FULL_NAME)
            .number(MemberConstants.NUMBER)
            .active(true)
            .build();
        return FeeCalendar.builder()
            .member(member)
            .year(MemberCalendars.CURRENT_DATE.getYear())
            .build();
    }

    public static final FeeCalendar activeNextYear() {
        final FeeCalendarMember member;

        member = FeeCalendarMember.builder()
            .fullName(MemberCalendars.FULL_NAME)
            .number(MemberConstants.NUMBER)
            .active(true)
            .build();
        return FeeCalendar.builder()
            .member(member)
            .year(MemberCalendars.NEXT_YEAR_DATE.getYear())
            .build();
    }

    public static final FeeCalendar activePreviousMonth() {
        final FeeCalendarMember member;

        member = FeeCalendarMember.builder()
            .fullName(MemberCalendars.FULL_NAME)
            .number(MemberConstants.NUMBER)
            .active(true)
            .build();
        return FeeCalendar.builder()
            .member(member)
            .year(MemberCalendars.PREVIOUS_MONTH_DATE.getYear())
            .build();
    }

    public static final FeeCalendar currentActive() {
        final FeeCalendarMember member;

        member = FeeCalendarMember.builder()
            .fullName(MemberCalendars.FULL_NAME)
            .number(MemberConstants.NUMBER)
            .active(true)
            .build();
        return FeeCalendar.builder()
            .member(member)
            .year(YearMonth.now()
                .getYear())
            .build();
    }

    public static final FeeCalendar currentInactive() {
        final FeeCalendarMember member;

        member = FeeCalendarMember.builder()
            .fullName(MemberCalendars.FULL_NAME)
            .number(MemberConstants.NUMBER)
            .active(false)
            .build();
        return FeeCalendar.builder()
            .member(member)
            .year(YearMonth.now()
                .getYear())
            .build();
    }

    public static final FeeCalendar fullCalendar() {
        final FeeCalendarMember member;

        member = FeeCalendarMember.builder()
            .fullName(MemberCalendars.FULL_NAME)
            .number(MemberConstants.NUMBER)
            .active(false)
            .build();
        return FeeCalendar.builder()
            .member(member)
            .year(MemberCalendars.YEAR)
            .months(List.of(FeeMonths.paidAtMonth(1), FeeMonths.paidAtMonth(2), FeeMonths.paidAtMonth(3),
                FeeMonths.paidAtMonth(4), FeeMonths.paidAtMonth(5), FeeMonths.paidAtMonth(6), FeeMonths.paidAtMonth(7),
                FeeMonths.paidAtMonth(8), FeeMonths.paidAtMonth(9), FeeMonths.paidAtMonth(10),
                FeeMonths.paidAtMonth(11), FeeMonths.paidAtMonth(12)))
            .build();
    }

    public static final FeeCalendar inactive() {
        final FeeCalendarMember member;

        member = FeeCalendarMember.builder()
            .fullName(MemberCalendars.FULL_NAME)
            .number(MemberConstants.NUMBER)
            .active(false)
            .build();
        return FeeCalendar.builder()
            .member(member)
            .year(MemberCalendars.YEAR)
            .build();
    }

    public static final FeeCalendar inactiveAlternative() {
        final FeeCalendarMember member;

        member = FeeCalendarMember.builder()
            .fullName(MemberCalendars.FULL_NAME_ALTERNATIVE)
            .number(MemberConstants.ALTERNATIVE_NUMBER)
            .active(false)
            .build();
        return FeeCalendar.builder()
            .member(member)
            .year(MemberCalendars.YEAR)
            .build();
    }

    public static final FeeCalendar inactiveNextYear() {
        final FeeCalendarMember member;

        member = FeeCalendarMember.builder()
            .fullName(MemberCalendars.FULL_NAME)
            .number(MemberConstants.NUMBER)
            .active(false)
            .build();
        return FeeCalendar.builder()
            .member(member)
            .year(MemberCalendars.NEXT_YEAR_DATE.getYear())
            .build();
    }

    public static final FeeCalendar inactivePreviousMonth() {
        final FeeCalendarMember member;

        member = FeeCalendarMember.builder()
            .fullName(MemberCalendars.FULL_NAME)
            .number(MemberConstants.NUMBER)
            .active(false)
            .build();
        return FeeCalendar.builder()
            .member(member)
            .year(MemberCalendars.PREVIOUS_MONTH_DATE.getYear())
            .build();
    }

    public static final FeeCalendar inactivePreviousYear() {
        final FeeCalendarMember member;

        member = FeeCalendarMember.builder()
            .fullName(MemberCalendars.FULL_NAME)
            .number(MemberConstants.NUMBER)
            .active(false)
            .build();
        return FeeCalendar.builder()
            .member(member)
            .year(MemberCalendars.YEAR_PREVIOUS)
            .build();
    }

    public static final FeeCalendar noSurname() {
        final FeeCalendarMember member;

        member = FeeCalendarMember.builder()
            .fullName(MemberCalendars.NAME)
            .number(MemberConstants.NUMBER)
            .active(false)
            .build();
        return FeeCalendar.builder()
            .member(member)
            .year(MemberCalendars.YEAR)
            .build();
    }

    private FeeCalendars() {
        super();
    }

}
