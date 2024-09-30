
package com.bernardomg.association.fee.test.configuration.factory;

import java.time.Month;
import java.time.YearMonth;
import java.util.Collection;
import java.util.List;

import com.bernardomg.association.fee.domain.model.FeeCalendar;
import com.bernardomg.association.fee.domain.model.FeeCalendarMember;
import com.bernardomg.association.fee.domain.model.FeeCalendarMonth;
import com.bernardomg.association.member.test.configuration.factory.MemberCalendars;
import com.bernardomg.association.person.test.configuration.factory.PersonConstants;

public final class FeeCalendars {

    public static final FeeCalendar activeAlternative() {
        final FeeCalendarMember            member;
        final Collection<FeeCalendarMonth> months;

        member = FeeCalendarMember.builder()
            .withFullName(PersonConstants.ALTERNATIVE_FULL_NAME)
            .withNumber(PersonConstants.ALTERNATIVE_NUMBER)
            .withActive(true)
            .build();
        months = List.of(FeeMonths.paidAlternative());
        // TODO: don't use member calendar constants
        return new FeeCalendar(member, months, MemberCalendars.YEAR.getValue());
    }

    public static final FeeCalendar activeNextYear() {
        final FeeCalendarMember            member;
        final Collection<FeeCalendarMonth> months;

        member = FeeCalendarMember.builder()
            .withFullName(PersonConstants.FULL_NAME)
            .withNumber(PersonConstants.NUMBER)
            .withActive(true)
            .build();
        months = List.of(FeeMonths.paidNextYear());
        return new FeeCalendar(member, months, MemberCalendars.NEXT_YEAR_DATE.getYear());
    }

    public static final FeeCalendar activeNotPaidCurrentMonth() {
        final FeeCalendarMember            member;
        final Collection<FeeCalendarMonth> months;

        member = FeeCalendarMember.builder()
            .withFullName(PersonConstants.FULL_NAME)
            .withNumber(PersonConstants.NUMBER)
            .withActive(true)
            .build();
        months = List.of(FeeMonths.notPaid());
        return new FeeCalendar(member, months, MemberCalendars.YEAR_CURRENT.getValue());
    }

    public static final FeeCalendar activeNotPaidNextYear() {
        final FeeCalendarMember            member;
        final Collection<FeeCalendarMonth> months;

        member = FeeCalendarMember.builder()
            .withFullName(PersonConstants.FULL_NAME)
            .withNumber(PersonConstants.NUMBER)
            .withActive(false)
            .build();
        months = List.of(FeeMonths.notPaidNextYear());
        return new FeeCalendar(member, months, MemberCalendars.NEXT_YEAR_DATE.getYear());
    }

    public static final FeeCalendar activeNotPaidPreviousMonth() {
        final FeeCalendarMember            member;
        final Collection<FeeCalendarMonth> months;

        member = FeeCalendarMember.builder()
            .withFullName(PersonConstants.FULL_NAME)
            .withNumber(PersonConstants.NUMBER)
            .withActive(true)
            .build();
        months = List.of(FeeMonths.notPaidPreviousMonth());
        return new FeeCalendar(member, months, MemberCalendars.PREVIOUS_MONTH_DATE.getYear());
    }

    public static final FeeCalendar activeNotPaidTwoMonthsBack() {
        final FeeCalendarMember            member;
        final Collection<FeeCalendarMonth> months;

        member = FeeCalendarMember.builder()
            .withFullName(PersonConstants.FULL_NAME)
            .withNumber(PersonConstants.NUMBER)
            .withActive(true)
            .build();
        months = List.of(FeeMonths.notPaidTwoMonthsBack());
        return new FeeCalendar(member, months, MemberCalendars.TWO_MONTHS_BACK_DATE.getYear());
    }

    public static final FeeCalendar activePaidCurrentMonth() {
        final FeeCalendarMember            member;
        final Collection<FeeCalendarMonth> months;

        member = FeeCalendarMember.builder()
            .withFullName(PersonConstants.FULL_NAME)
            .withNumber(PersonConstants.NUMBER)
            .withActive(true)
            .build();
        months = List.of(FeeMonths.paid());
        return new FeeCalendar(member, months, MemberCalendars.YEAR_CURRENT.getValue());
    }

    public static final FeeCalendar activePaidNextYear() {
        final FeeCalendarMember            member;
        final Collection<FeeCalendarMonth> months;

        member = FeeCalendarMember.builder()
            .withFullName(PersonConstants.FULL_NAME)
            .withNumber(PersonConstants.NUMBER)
            .withActive(true)
            .build();
        months = List.of(FeeMonths.paidNextYear());
        return new FeeCalendar(member, months, MemberCalendars.NEXT_YEAR_DATE.getYear());
    }

    public static final FeeCalendar activePaidTwoMonthsBack() {
        final FeeCalendarMember            member;
        final Collection<FeeCalendarMonth> months;

        member = FeeCalendarMember.builder()
            .withFullName(PersonConstants.FULL_NAME)
            .withNumber(PersonConstants.NUMBER)
            .withActive(true)
            .build();
        months = List.of(FeeMonths.paidTwoMonthsBack());
        return new FeeCalendar(member, months, MemberCalendars.TWO_MONTHS_BACK_DATE.getYear());
    }

    public static final FeeCalendar activePreviousMonth() {
        final FeeCalendarMember            member;
        final Collection<FeeCalendarMonth> months;

        member = FeeCalendarMember.builder()
            .withFullName(PersonConstants.FULL_NAME)
            .withNumber(PersonConstants.NUMBER)
            .withActive(true)
            .build();
        months = List.of(FeeMonths.paidPreviousMonth());
        return new FeeCalendar(member, months, MemberCalendars.PREVIOUS_MONTH_DATE.getYear());
    }

    public static final FeeCalendar currentActive() {
        final FeeCalendarMember member;

        member = FeeCalendarMember.builder()
            .withFullName(PersonConstants.FULL_NAME)
            .withNumber(PersonConstants.NUMBER)
            .withActive(true)
            .build();
        return new FeeCalendar(member, List.of(), YearMonth.now()
            .getYear());
    }

    public static final FeeCalendar currentInactive() {
        final FeeCalendarMember member;

        member = FeeCalendarMember.builder()
            .withFullName(PersonConstants.FULL_NAME)
            .withNumber(PersonConstants.NUMBER)
            .withActive(false)
            .build();
        return new FeeCalendar(member, List.of(), YearMonth.now()
            .getYear());
    }

    public static final FeeCalendar inactive() {
        final FeeCalendarMember            member;
        final Collection<FeeCalendarMonth> months;

        member = FeeCalendarMember.builder()
            .withFullName(PersonConstants.FULL_NAME)
            .withNumber(PersonConstants.NUMBER)
            .withActive(false)
            .build();
        months = List.of(FeeMonths.notPaid());
        return new FeeCalendar(member, months, MemberCalendars.YEAR.getValue());
    }

    public static final FeeCalendar inactiveAlternative() {
        final FeeCalendarMember            member;
        final Collection<FeeCalendarMonth> months;

        member = FeeCalendarMember.builder()
            .withFullName(PersonConstants.ALTERNATIVE_FULL_NAME)
            .withNumber(PersonConstants.ALTERNATIVE_NUMBER)
            .withActive(false)
            .build();
        months = List.of(FeeMonths.notPaidAlternative());
        return new FeeCalendar(member, months, MemberCalendars.YEAR.getValue());
    }

    public static final FeeCalendar inactivefullCalendar() {
        final FeeCalendarMember            member;
        final Collection<FeeCalendarMonth> months;

        member = FeeCalendarMember.builder()
            .withFullName(PersonConstants.FULL_NAME)
            .withNumber(PersonConstants.NUMBER)
            .withActive(false)
            .build();
        months = List.of(FeeMonths.paidAtMonth(1), FeeMonths.paidAtMonth(2), FeeMonths.paidAtMonth(3),
            FeeMonths.paidAtMonth(4), FeeMonths.paidAtMonth(5), FeeMonths.paidAtMonth(6), FeeMonths.paidAtMonth(7),
            FeeMonths.paidAtMonth(8), FeeMonths.paidAtMonth(9), FeeMonths.paidAtMonth(10), FeeMonths.paidAtMonth(11),
            FeeMonths.paidAtMonth(12));
        return new FeeCalendar(member, months, MemberCalendars.YEAR.getValue());
    }

    public static final FeeCalendar inactivefullCalendarAlternative() {
        final FeeCalendarMember            member;
        final Collection<FeeCalendarMonth> months;

        member = FeeCalendarMember.builder()
            .withFullName(PersonConstants.ALTERNATIVE_FULL_NAME)
            .withNumber(PersonConstants.ALTERNATIVE_NUMBER)
            .withActive(false)
            .build();
        months = List.of(FeeMonths.paidAtMonth(1), FeeMonths.paidAtMonth(2), FeeMonths.paidAtMonth(3),
            FeeMonths.paidAtMonth(4), FeeMonths.paidAtMonth(5), FeeMonths.paidAtMonth(6), FeeMonths.paidAtMonth(7),
            FeeMonths.paidAtMonth(8), FeeMonths.paidAtMonth(9), FeeMonths.paidAtMonth(10), FeeMonths.paidAtMonth(11),
            FeeMonths.paidAtMonth(12));
        return new FeeCalendar(member, months, MemberCalendars.YEAR.getValue());
    }

    public static final FeeCalendar inactivefullCalendarNoLastName() {
        final FeeCalendarMember            member;
        final Collection<FeeCalendarMonth> months;

        member = FeeCalendarMember.builder()
            .withFullName(PersonConstants.NAME)
            .withNumber(PersonConstants.NUMBER)
            .withActive(false)
            .build();
        months = List.of(FeeMonths.paidAtMonth(1), FeeMonths.paidAtMonth(2), FeeMonths.paidAtMonth(3),
            FeeMonths.paidAtMonth(4), FeeMonths.paidAtMonth(5), FeeMonths.paidAtMonth(6), FeeMonths.paidAtMonth(7),
            FeeMonths.paidAtMonth(8), FeeMonths.paidAtMonth(9), FeeMonths.paidAtMonth(10), FeeMonths.paidAtMonth(11),
            FeeMonths.paidAtMonth(12));
        return new FeeCalendar(member, months, MemberCalendars.YEAR.getValue());
    }

    public static final FeeCalendar inactivefullCalendarNoName() {
        final FeeCalendarMember            member;
        final Collection<FeeCalendarMonth> months;

        member = FeeCalendarMember.builder()
            .withFullName("")
            .withNumber(PersonConstants.NUMBER)
            .withActive(false)
            .build();
        months = List.of(FeeMonths.paidAtMonth(1), FeeMonths.paidAtMonth(2), FeeMonths.paidAtMonth(3),
            FeeMonths.paidAtMonth(4), FeeMonths.paidAtMonth(5), FeeMonths.paidAtMonth(6), FeeMonths.paidAtMonth(7),
            FeeMonths.paidAtMonth(8), FeeMonths.paidAtMonth(9), FeeMonths.paidAtMonth(10), FeeMonths.paidAtMonth(11),
            FeeMonths.paidAtMonth(12));
        return new FeeCalendar(member, months, MemberCalendars.YEAR.getValue());
    }

    public static final FeeCalendar inactiveNotPaidNextYear() {
        final FeeCalendarMember            member;
        final Collection<FeeCalendarMonth> months;

        member = FeeCalendarMember.builder()
            .withFullName(PersonConstants.FULL_NAME)
            .withNumber(PersonConstants.NUMBER)
            .withActive(false)
            .build();
        months = List.of(FeeMonths.notPaidNextYear());
        return new FeeCalendar(member, months, MemberCalendars.NEXT_YEAR_DATE.getYear());
    }

    public static final FeeCalendar inactiveNotPaidPreviousMonth() {
        final FeeCalendarMember            member;
        final Collection<FeeCalendarMonth> months;

        member = FeeCalendarMember.builder()
            .withFullName(PersonConstants.FULL_NAME)
            .withNumber(PersonConstants.NUMBER)
            .withActive(false)
            .build();
        months = List.of(FeeMonths.notPaidPreviousMonth());
        return new FeeCalendar(member, months, MemberCalendars.PREVIOUS_MONTH_DATE.getYear());
    }

    public static final FeeCalendar inactiveNotPaidTwoMonthsBack() {
        final FeeCalendarMember            member;
        final Collection<FeeCalendarMonth> months;

        member = FeeCalendarMember.builder()
            .withFullName(PersonConstants.FULL_NAME)
            .withNumber(PersonConstants.NUMBER)
            .withActive(false)
            .build();
        months = List.of(FeeMonths.notPaidTwoMonthsBack());
        return new FeeCalendar(member, months, MemberCalendars.TWO_MONTHS_BACK_DATE.getYear());
    }

    public static final FeeCalendar inactivePaidCurrentMonth() {
        final FeeCalendarMember            member;
        final Collection<FeeCalendarMonth> months;

        member = FeeCalendarMember.builder()
            .withFullName(PersonConstants.FULL_NAME)
            .withNumber(PersonConstants.NUMBER)
            .withActive(false)
            .build();
        months = List.of(FeeMonths.paid());
        return new FeeCalendar(member, months, MemberCalendars.YEAR_CURRENT.getValue());
    }

    public static final FeeCalendar inactivePaidNextYear() {
        final FeeCalendarMember            member;
        final Collection<FeeCalendarMonth> months;

        member = FeeCalendarMember.builder()
            .withFullName(PersonConstants.FULL_NAME)
            .withNumber(PersonConstants.NUMBER)
            .withActive(false)
            .build();
        months = List.of(FeeMonths.paidNextYear());
        return new FeeCalendar(member, months, MemberCalendars.NEXT_YEAR_DATE.getYear());
    }

    public static final FeeCalendar inactivePaidTwoMonthsBack() {
        final FeeCalendarMember            member;
        final Collection<FeeCalendarMonth> months;

        member = FeeCalendarMember.builder()
            .withFullName(PersonConstants.FULL_NAME)
            .withNumber(PersonConstants.NUMBER)
            .withActive(false)
            .build();
        months = List.of(FeeMonths.paidTwoMonthsBack());
        return new FeeCalendar(member, months, MemberCalendars.TWO_MONTHS_BACK_DATE.getYear());
    }

    public static final FeeCalendar inactivePreviousMonth() {
        final FeeCalendarMember            member;
        final Collection<FeeCalendarMonth> months;

        member = FeeCalendarMember.builder()
            .withFullName(PersonConstants.FULL_NAME)
            .withNumber(PersonConstants.NUMBER)
            .withActive(false)
            .build();
        months = List.of(FeeMonths.paidPreviousMonth());
        return new FeeCalendar(member, months, MemberCalendars.PREVIOUS_MONTH_DATE.getYear());
    }

    public static final FeeCalendar inactivePreviousYear() {
        final FeeCalendarMember member;

        member = FeeCalendarMember.builder()
            .withFullName(PersonConstants.FULL_NAME)
            .withNumber(PersonConstants.NUMBER)
            .withActive(false)
            .build();
        return new FeeCalendar(member, List.of(), MemberCalendars.YEAR_PREVIOUS.getValue());
    }

    public static final FeeCalendar inactiveTwoConnectedFirst() {
        final FeeCalendarMember            member;
        final Collection<FeeCalendarMonth> months;

        member = FeeCalendarMember.builder()
            .withFullName(PersonConstants.FULL_NAME)
            .withNumber(PersonConstants.NUMBER)
            .withActive(false)
            .build();
        months = List.of(FeeMonths.paid(MemberCalendars.YEAR_PREVIOUS.getValue(), Month.OCTOBER.getValue()),
            FeeMonths.paid(MemberCalendars.YEAR_PREVIOUS.getValue(), Month.NOVEMBER.getValue()),
            FeeMonths.paid(MemberCalendars.YEAR_PREVIOUS.getValue(), Month.DECEMBER.getValue()));
        return new FeeCalendar(member, months, MemberCalendars.YEAR_PREVIOUS.getValue());
    }

    public static final FeeCalendar inactiveTwoConnectedSecond() {
        final FeeCalendarMember            member;
        final Collection<FeeCalendarMonth> months;

        member = FeeCalendarMember.builder()
            .withFullName(PersonConstants.FULL_NAME)
            .withNumber(PersonConstants.NUMBER)
            .withActive(false)
            .build();
        months = List.of(FeeMonths.paid(2020, Month.JANUARY.getValue()),
            FeeMonths.paid(2020, Month.FEBRUARY.getValue()), FeeMonths.paid(2020, Month.MARCH.getValue()),
            FeeMonths.paid(2020, Month.APRIL.getValue()), FeeMonths.paid(2020, Month.MAY.getValue()),
            FeeMonths.paid(2020, Month.JUNE.getValue()), FeeMonths.paid(2020, Month.JULY.getValue()));
        return new FeeCalendar(member, months, MemberCalendars.YEAR.getValue());
    }

    public static final FeeCalendar noLastName() {
        final FeeCalendarMember member;

        member = FeeCalendarMember.builder()
            .withFullName(PersonConstants.NAME)
            .withNumber(PersonConstants.NUMBER)
            .withActive(false)
            .build();
        return new FeeCalendar(member, List.of(), MemberCalendars.YEAR.getValue());
    }

    public static final FeeCalendar paidTwoMonthsBack() {
        final FeeCalendarMember            member;
        final Collection<FeeCalendarMonth> months;

        member = FeeCalendarMember.builder()
            .withFullName(PersonConstants.FULL_NAME)
            .withNumber(PersonConstants.NUMBER)
            .withActive(false)
            .build();
        months = List.of(FeeMonths.paidTwoMonthsBack());
        return new FeeCalendar(member, months, MemberCalendars.TWO_MONTHS_BACK_DATE.getYear());
    }

    private FeeCalendars() {
        super();
    }

}
