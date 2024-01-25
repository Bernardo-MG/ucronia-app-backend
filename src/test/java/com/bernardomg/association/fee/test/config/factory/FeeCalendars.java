
package com.bernardomg.association.fee.test.config.factory;

import java.time.YearMonth;
import java.util.List;

import com.bernardomg.association.fee.domain.model.FeeCalendar;
import com.bernardomg.association.fee.domain.model.FeeCalendarMember;
import com.bernardomg.association.member.test.config.factory.MemberCalendars;
import com.bernardomg.association.member.test.config.factory.MemberConstants;

public final class FeeCalendars {

    public static final FeeCalendar active() {
        final FeeCalendarMember member;

        member = FeeCalendarMember.builder()
            .withFullName(MemberCalendars.FULL_NAME)
            .withNumber(MemberConstants.NUMBER)
            .withActive(true)
            .build();
        return FeeCalendar.builder()
            .withMember(member)
            .withYear(MemberCalendars.YEAR)
            .build();
    }

    public static final FeeCalendar activeAlternative() {
        final FeeCalendarMember member;

        member = FeeCalendarMember.builder()
            .withFullName(MemberCalendars.FULL_NAME_ALTERNATIVE)
            .withNumber(MemberConstants.ALTERNATIVE_NUMBER)
            .withActive(true)
            .build();
        return FeeCalendar.builder()
            .withMember(member)
            .withYear(MemberCalendars.YEAR)
            .build();
    }

    public static final FeeCalendar activeCurrentMonth() {
        final FeeCalendarMember member;

        member = FeeCalendarMember.builder()
            .withFullName(MemberCalendars.FULL_NAME)
            .withNumber(MemberConstants.NUMBER)
            .withActive(true)
            .build();
        return FeeCalendar.builder()
            .withMember(member)
            .withYear(MemberCalendars.CURRENT_DATE.getYear())
            .build();
    }

    public static final FeeCalendar activeNextYear() {
        final FeeCalendarMember member;

        member = FeeCalendarMember.builder()
            .withFullName(MemberCalendars.FULL_NAME)
            .withNumber(MemberConstants.NUMBER)
            .withActive(true)
            .build();
        return FeeCalendar.builder()
            .withMember(member)
            .withYear(MemberCalendars.NEXT_YEAR_DATE.getYear())
            .build();
    }

    public static final FeeCalendar activePreviousMonth() {
        final FeeCalendarMember member;

        member = FeeCalendarMember.builder()
            .withFullName(MemberCalendars.FULL_NAME)
            .withNumber(MemberConstants.NUMBER)
            .withActive(true)
            .build();
        return FeeCalendar.builder()
            .withMember(member)
            .withYear(MemberCalendars.PREVIOUS_MONTH_DATE.getYear())
            .build();
    }

    public static final FeeCalendar currentActive() {
        final FeeCalendarMember member;

        member = FeeCalendarMember.builder()
            .withFullName(MemberCalendars.FULL_NAME)
            .withNumber(MemberConstants.NUMBER)
            .withActive(true)
            .build();
        return FeeCalendar.builder()
            .withMember(member)
            .withYear(YearMonth.now()
                .getYear())
            .build();
    }

    public static final FeeCalendar currentInactive() {
        final FeeCalendarMember member;

        member = FeeCalendarMember.builder()
            .withFullName(MemberCalendars.FULL_NAME)
            .withNumber(MemberConstants.NUMBER)
            .withActive(false)
            .build();
        return FeeCalendar.builder()
            .withMember(member)
            .withYear(YearMonth.now()
                .getYear())
            .build();
    }

    public static final FeeCalendar fullCalendar() {
        final FeeCalendarMember member;

        member = FeeCalendarMember.builder()
            .withFullName(MemberCalendars.FULL_NAME)
            .withNumber(MemberConstants.NUMBER)
            .withActive(false)
            .build();
        return FeeCalendar.builder()
            .withMember(member)
            .withYear(MemberCalendars.YEAR)
            .withMonths(List.of(FeeMonths.paidAtMonth(1), FeeMonths.paidAtMonth(2), FeeMonths.paidAtMonth(3),
                FeeMonths.paidAtMonth(4), FeeMonths.paidAtMonth(5), FeeMonths.paidAtMonth(6), FeeMonths.paidAtMonth(7),
                FeeMonths.paidAtMonth(8), FeeMonths.paidAtMonth(9), FeeMonths.paidAtMonth(10),
                FeeMonths.paidAtMonth(11), FeeMonths.paidAtMonth(12)))
            .build();
    }

    public static final FeeCalendar inactive() {
        final FeeCalendarMember member;

        member = FeeCalendarMember.builder()
            .withFullName(MemberCalendars.FULL_NAME)
            .withNumber(MemberConstants.NUMBER)
            .withActive(false)
            .build();
        return FeeCalendar.builder()
            .withMember(member)
            .withYear(MemberCalendars.YEAR)
            .build();
    }

    public static final FeeCalendar inactiveAlternative() {
        final FeeCalendarMember member;

        member = FeeCalendarMember.builder()
            .withFullName(MemberCalendars.FULL_NAME_ALTERNATIVE)
            .withNumber(MemberConstants.ALTERNATIVE_NUMBER)
            .withActive(false)
            .build();
        return FeeCalendar.builder()
            .withMember(member)
            .withYear(MemberCalendars.YEAR)
            .build();
    }

    public static final FeeCalendar inactiveNextYear() {
        final FeeCalendarMember member;

        member = FeeCalendarMember.builder()
            .withFullName(MemberCalendars.FULL_NAME)
            .withNumber(MemberConstants.NUMBER)
            .withActive(false)
            .build();
        return FeeCalendar.builder()
            .withMember(member)
            .withYear(MemberCalendars.NEXT_YEAR_DATE.getYear())
            .build();
    }

    public static final FeeCalendar inactivePreviousMonth() {
        final FeeCalendarMember member;

        member = FeeCalendarMember.builder()
            .withFullName(MemberCalendars.FULL_NAME)
            .withNumber(MemberConstants.NUMBER)
            .withActive(false)
            .build();
        return FeeCalendar.builder()
            .withMember(member)
            .withYear(MemberCalendars.PREVIOUS_MONTH_DATE.getYear())
            .build();
    }

    public static final FeeCalendar inactivePreviousYear() {
        final FeeCalendarMember member;

        member = FeeCalendarMember.builder()
            .withFullName(MemberCalendars.FULL_NAME)
            .withNumber(MemberConstants.NUMBER)
            .withActive(false)
            .build();
        return FeeCalendar.builder()
            .withMember(member)
            .withYear(MemberCalendars.YEAR_PREVIOUS)
            .build();
    }

    public static final FeeCalendar noSurname() {
        final FeeCalendarMember member;

        member = FeeCalendarMember.builder()
            .withFullName(MemberCalendars.NAME)
            .withNumber(MemberConstants.NUMBER)
            .withActive(false)
            .build();
        return FeeCalendar.builder()
            .withMember(member)
            .withYear(MemberCalendars.YEAR)
            .build();
    }

    private FeeCalendars() {
        super();
    }

}
