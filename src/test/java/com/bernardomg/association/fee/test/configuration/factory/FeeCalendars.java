
package com.bernardomg.association.fee.test.configuration.factory;

import java.time.Month;
import java.time.YearMonth;
import java.util.Collection;
import java.util.List;

import com.bernardomg.association.fee.domain.model.FeeCalendar;
import com.bernardomg.association.fee.domain.model.FeeCalendarMonth;
import com.bernardomg.association.member.domain.model.PublicMember;
import com.bernardomg.association.member.test.configuration.factory.MemberCalendars;
import com.bernardomg.association.person.domain.model.PersonName;
import com.bernardomg.association.person.test.configuration.factory.PersonConstants;

public final class FeeCalendars {

    public static final FeeCalendar activeAlternative() {
        final PublicMember                 member;
        final Collection<FeeCalendarMonth> months;
        final PersonName                   name;

        name = new PersonName(PersonConstants.FIRST_NAME, PersonConstants.LAST_NAME);
        member = new PublicMember(PersonConstants.ALTERNATIVE_NUMBER, name, true);
        months = List.of(FeeMonths.paidAlternative());
        // TODO: don't use member calendar constants
        return new FeeCalendar(member, months, MemberCalendars.YEAR.getValue());
    }

    public static final FeeCalendar activeNextYear() {
        final PublicMember                 member;
        final Collection<FeeCalendarMonth> months;
        final PersonName                   name;

        name = new PersonName(PersonConstants.FIRST_NAME, PersonConstants.LAST_NAME);
        member = new PublicMember(PersonConstants.NUMBER, name, true);
        months = List.of(FeeMonths.paidNextYear());
        return new FeeCalendar(member, months, MemberCalendars.NEXT_YEAR_DATE.getYear());
    }

    public static final FeeCalendar activeNotPaidCurrentMonth() {
        final PublicMember                 member;
        final Collection<FeeCalendarMonth> months;
        final PersonName                   name;

        name = new PersonName(PersonConstants.FIRST_NAME, PersonConstants.LAST_NAME);
        member = new PublicMember(PersonConstants.NUMBER, name, true);
        months = List.of(FeeMonths.notPaid());
        return new FeeCalendar(member, months, MemberCalendars.YEAR_CURRENT.getValue());
    }

    public static final FeeCalendar activeNotPaidNextYear() {
        final PublicMember                 member;
        final Collection<FeeCalendarMonth> months;
        final PersonName                   name;

        name = new PersonName(PersonConstants.FIRST_NAME, PersonConstants.LAST_NAME);
        member = new PublicMember(PersonConstants.NUMBER, name, false);
        months = List.of(FeeMonths.notPaidNextYear());
        return new FeeCalendar(member, months, MemberCalendars.NEXT_YEAR_DATE.getYear());
    }

    public static final FeeCalendar activeNotPaidPreviousMonth() {
        final PublicMember                 member;
        final Collection<FeeCalendarMonth> months;
        final PersonName                   name;

        name = new PersonName(PersonConstants.FIRST_NAME, PersonConstants.LAST_NAME);
        member = new PublicMember(PersonConstants.NUMBER, name, true);
        months = List.of(FeeMonths.notPaidPreviousMonth());
        return new FeeCalendar(member, months, MemberCalendars.PREVIOUS_MONTH_DATE.getYear());
    }

    public static final FeeCalendar activeNotPaidTwoMonthsBack() {
        final PublicMember                 member;
        final Collection<FeeCalendarMonth> months;
        final PersonName                   name;

        name = new PersonName(PersonConstants.FIRST_NAME, PersonConstants.LAST_NAME);
        member = new PublicMember(PersonConstants.NUMBER, name, true);
        months = List.of(FeeMonths.notPaidTwoMonthsBack());
        return new FeeCalendar(member, months, MemberCalendars.TWO_MONTHS_BACK_DATE.getYear());
    }

    public static final FeeCalendar activePaidCurrentMonth() {
        final PublicMember                 member;
        final Collection<FeeCalendarMonth> months;
        final PersonName                   name;

        name = new PersonName(PersonConstants.FIRST_NAME, PersonConstants.LAST_NAME);
        member = new PublicMember(PersonConstants.NUMBER, name, true);
        months = List.of(FeeMonths.paid());
        return new FeeCalendar(member, months, MemberCalendars.YEAR_CURRENT.getValue());
    }

    public static final FeeCalendar activePaidNextYear() {
        final PublicMember                 member;
        final Collection<FeeCalendarMonth> months;
        final PersonName                   name;

        name = new PersonName(PersonConstants.FIRST_NAME, PersonConstants.LAST_NAME);
        member = new PublicMember(PersonConstants.NUMBER, name, true);
        months = List.of(FeeMonths.paidNextYear());
        return new FeeCalendar(member, months, MemberCalendars.NEXT_YEAR_DATE.getYear());
    }

    public static final FeeCalendar activePaidTwoMonthsBack() {
        final PublicMember                 member;
        final Collection<FeeCalendarMonth> months;
        final PersonName                   name;

        name = new PersonName(PersonConstants.FIRST_NAME, PersonConstants.LAST_NAME);
        member = new PublicMember(PersonConstants.NUMBER, name, true);
        months = List.of(FeeMonths.paidTwoMonthsBack());
        return new FeeCalendar(member, months, MemberCalendars.TWO_MONTHS_BACK_DATE.getYear());
    }

    public static final FeeCalendar activePreviousMonth() {
        final PublicMember                 member;
        final Collection<FeeCalendarMonth> months;
        final PersonName                   name;

        name = new PersonName(PersonConstants.FIRST_NAME, PersonConstants.LAST_NAME);
        member = new PublicMember(PersonConstants.NUMBER, name, true);
        months = List.of(FeeMonths.paidPreviousMonth());
        return new FeeCalendar(member, months, MemberCalendars.PREVIOUS_MONTH_DATE.getYear());
    }

    public static final FeeCalendar currentActive() {
        final PublicMember member;
        final PersonName   name;

        name = new PersonName(PersonConstants.FIRST_NAME, PersonConstants.LAST_NAME);
        member = new PublicMember(PersonConstants.NUMBER, name, true);
        return new FeeCalendar(member, List.of(), YearMonth.now()
            .getYear());
    }

    public static final FeeCalendar currentInactive() {
        final PublicMember member;
        final PersonName   name;

        name = new PersonName(PersonConstants.FIRST_NAME, PersonConstants.LAST_NAME);
        member = new PublicMember(PersonConstants.NUMBER, name, false);
        return new FeeCalendar(member, List.of(), YearMonth.now()
            .getYear());
    }

    public static final FeeCalendar inactive() {
        final PublicMember                 member;
        final Collection<FeeCalendarMonth> months;
        final PersonName                   name;

        name = new PersonName(PersonConstants.FIRST_NAME, PersonConstants.LAST_NAME);
        member = new PublicMember(PersonConstants.NUMBER, name, false);
        months = List.of(FeeMonths.notPaid());
        return new FeeCalendar(member, months, MemberCalendars.YEAR.getValue());
    }

    public static final FeeCalendar inactiveAlternative() {
        final PublicMember                 member;
        final Collection<FeeCalendarMonth> months;
        final PersonName                   name;

        name = new PersonName(PersonConstants.ALTERNATIVE_FIRST_NAME, PersonConstants.ALTERNATIVE_LAST_NAME);
        member = new PublicMember(PersonConstants.ALTERNATIVE_NUMBER, name, false);
        months = List.of(FeeMonths.notPaidAlternative());
        return new FeeCalendar(member, months, MemberCalendars.YEAR.getValue());
    }

    public static final FeeCalendar inactivefullCalendar() {
        final PublicMember                 member;
        final Collection<FeeCalendarMonth> months;
        final PersonName                   name;

        name = new PersonName(PersonConstants.FIRST_NAME, PersonConstants.LAST_NAME);
        member = new PublicMember(PersonConstants.NUMBER, name, false);
        months = List.of(FeeMonths.paidAtMonth(1), FeeMonths.paidAtMonth(2), FeeMonths.paidAtMonth(3),
            FeeMonths.paidAtMonth(4), FeeMonths.paidAtMonth(5), FeeMonths.paidAtMonth(6), FeeMonths.paidAtMonth(7),
            FeeMonths.paidAtMonth(8), FeeMonths.paidAtMonth(9), FeeMonths.paidAtMonth(10), FeeMonths.paidAtMonth(11),
            FeeMonths.paidAtMonth(12));
        return new FeeCalendar(member, months, MemberCalendars.YEAR.getValue());
    }

    public static final FeeCalendar inactivefullCalendarAlternative() {
        final PublicMember                 member;
        final Collection<FeeCalendarMonth> months;
        final PersonName                   name;

        name = new PersonName(PersonConstants.ALTERNATIVE_FIRST_NAME, PersonConstants.ALTERNATIVE_LAST_NAME);
        member = new PublicMember(PersonConstants.ALTERNATIVE_NUMBER, name, false);
        months = List.of(FeeMonths.paidAtMonth(1), FeeMonths.paidAtMonth(2), FeeMonths.paidAtMonth(3),
            FeeMonths.paidAtMonth(4), FeeMonths.paidAtMonth(5), FeeMonths.paidAtMonth(6), FeeMonths.paidAtMonth(7),
            FeeMonths.paidAtMonth(8), FeeMonths.paidAtMonth(9), FeeMonths.paidAtMonth(10), FeeMonths.paidAtMonth(11),
            FeeMonths.paidAtMonth(12));
        return new FeeCalendar(member, months, MemberCalendars.YEAR.getValue());
    }

    public static final FeeCalendar inactivefullCalendarNoLastName() {
        final PublicMember                 member;
        final Collection<FeeCalendarMonth> months;
        final PersonName                   name;

        name = new PersonName(PersonConstants.FIRST_NAME, PersonConstants.LAST_NAME);
        member = new PublicMember(PersonConstants.NUMBER, name, false);
        months = List.of(FeeMonths.paidAtMonth(1), FeeMonths.paidAtMonth(2), FeeMonths.paidAtMonth(3),
            FeeMonths.paidAtMonth(4), FeeMonths.paidAtMonth(5), FeeMonths.paidAtMonth(6), FeeMonths.paidAtMonth(7),
            FeeMonths.paidAtMonth(8), FeeMonths.paidAtMonth(9), FeeMonths.paidAtMonth(10), FeeMonths.paidAtMonth(11),
            FeeMonths.paidAtMonth(12));
        return new FeeCalendar(member, months, MemberCalendars.YEAR.getValue());
    }

    public static final FeeCalendar inactivefullCalendarNoName() {
        final PublicMember                 member;
        final Collection<FeeCalendarMonth> months;
        final PersonName                   name;

        name = new PersonName("", "");
        member = new PublicMember(PersonConstants.NUMBER, name, false);
        months = List.of(FeeMonths.paidAtMonth(1), FeeMonths.paidAtMonth(2), FeeMonths.paidAtMonth(3),
            FeeMonths.paidAtMonth(4), FeeMonths.paidAtMonth(5), FeeMonths.paidAtMonth(6), FeeMonths.paidAtMonth(7),
            FeeMonths.paidAtMonth(8), FeeMonths.paidAtMonth(9), FeeMonths.paidAtMonth(10), FeeMonths.paidAtMonth(11),
            FeeMonths.paidAtMonth(12));
        return new FeeCalendar(member, months, MemberCalendars.YEAR.getValue());
    }

    public static final FeeCalendar inactiveNotPaidNextYear() {
        final PublicMember                 member;
        final Collection<FeeCalendarMonth> months;
        final PersonName                   name;

        name = new PersonName(PersonConstants.FIRST_NAME, PersonConstants.LAST_NAME);
        member = new PublicMember(PersonConstants.NUMBER, name, false);
        months = List.of(FeeMonths.notPaidNextYear());
        return new FeeCalendar(member, months, MemberCalendars.NEXT_YEAR_DATE.getYear());
    }

    public static final FeeCalendar inactiveNotPaidPreviousMonth() {
        final PublicMember                 member;
        final Collection<FeeCalendarMonth> months;
        final PersonName                   name;

        name = new PersonName(PersonConstants.FIRST_NAME, PersonConstants.LAST_NAME);
        member = new PublicMember(PersonConstants.NUMBER, name, false);
        months = List.of(FeeMonths.notPaidPreviousMonth());
        return new FeeCalendar(member, months, MemberCalendars.PREVIOUS_MONTH_DATE.getYear());
    }

    public static final FeeCalendar inactiveNotPaidTwoMonthsBack() {
        final PublicMember                 member;
        final Collection<FeeCalendarMonth> months;
        final PersonName                   name;

        name = new PersonName(PersonConstants.FIRST_NAME, PersonConstants.LAST_NAME);
        member = new PublicMember(PersonConstants.NUMBER, name, false);
        months = List.of(FeeMonths.notPaidTwoMonthsBack());
        return new FeeCalendar(member, months, MemberCalendars.TWO_MONTHS_BACK_DATE.getYear());
    }

    public static final FeeCalendar inactivePaidCurrentMonth() {
        final PublicMember                 member;
        final Collection<FeeCalendarMonth> months;
        final PersonName                   name;

        name = new PersonName(PersonConstants.FIRST_NAME, PersonConstants.LAST_NAME);
        member = new PublicMember(PersonConstants.NUMBER, name, false);
        months = List.of(FeeMonths.paid());
        return new FeeCalendar(member, months, MemberCalendars.YEAR_CURRENT.getValue());
    }

    public static final FeeCalendar inactivePaidNextYear() {
        final PublicMember                 member;
        final Collection<FeeCalendarMonth> months;
        final PersonName                   name;

        name = new PersonName(PersonConstants.FIRST_NAME, PersonConstants.LAST_NAME);
        member = new PublicMember(PersonConstants.NUMBER, name, false);
        months = List.of(FeeMonths.paidNextYear());
        return new FeeCalendar(member, months, MemberCalendars.NEXT_YEAR_DATE.getYear());
    }

    public static final FeeCalendar inactivePaidTwoMonthsBack() {
        final PublicMember                 member;
        final Collection<FeeCalendarMonth> months;
        final PersonName                   name;

        name = new PersonName(PersonConstants.FIRST_NAME, PersonConstants.LAST_NAME);
        member = new PublicMember(PersonConstants.NUMBER, name, false);
        months = List.of(FeeMonths.paidTwoMonthsBack());
        return new FeeCalendar(member, months, MemberCalendars.TWO_MONTHS_BACK_DATE.getYear());
    }

    public static final FeeCalendar inactivePreviousMonth() {
        final PublicMember                 member;
        final Collection<FeeCalendarMonth> months;
        final PersonName                   name;

        name = new PersonName(PersonConstants.FIRST_NAME, PersonConstants.LAST_NAME);
        member = new PublicMember(PersonConstants.NUMBER, name, false);
        months = List.of(FeeMonths.paidPreviousMonth());
        return new FeeCalendar(member, months, MemberCalendars.PREVIOUS_MONTH_DATE.getYear());
    }

    public static final FeeCalendar inactivePreviousYear() {
        final PublicMember member;
        final PersonName   name;

        name = new PersonName(PersonConstants.FIRST_NAME, PersonConstants.LAST_NAME);
        member = new PublicMember(PersonConstants.NUMBER, name, false);
        return new FeeCalendar(member, List.of(), MemberCalendars.YEAR_PREVIOUS.getValue());
    }

    public static final FeeCalendar inactiveTwoConnectedFirst() {
        final PublicMember                 member;
        final Collection<FeeCalendarMonth> months;
        final PersonName                   name;

        name = new PersonName(PersonConstants.FIRST_NAME, PersonConstants.LAST_NAME);
        member = new PublicMember(PersonConstants.NUMBER, name, false);
        months = List.of(FeeMonths.paid(MemberCalendars.YEAR_PREVIOUS.getValue(), Month.OCTOBER.getValue()),
            FeeMonths.paid(MemberCalendars.YEAR_PREVIOUS.getValue(), Month.NOVEMBER.getValue()),
            FeeMonths.paid(MemberCalendars.YEAR_PREVIOUS.getValue(), Month.DECEMBER.getValue()));
        return new FeeCalendar(member, months, MemberCalendars.YEAR_PREVIOUS.getValue());
    }

    public static final FeeCalendar inactiveTwoConnectedSecond() {
        final PublicMember                 member;
        final Collection<FeeCalendarMonth> months;
        final PersonName                   name;

        name = new PersonName(PersonConstants.FIRST_NAME, PersonConstants.LAST_NAME);
        member = new PublicMember(PersonConstants.NUMBER, name, false);
        months = List.of(FeeMonths.paid(2020, Month.JANUARY.getValue()),
            FeeMonths.paid(2020, Month.FEBRUARY.getValue()), FeeMonths.paid(2020, Month.MARCH.getValue()),
            FeeMonths.paid(2020, Month.APRIL.getValue()), FeeMonths.paid(2020, Month.MAY.getValue()),
            FeeMonths.paid(2020, Month.JUNE.getValue()), FeeMonths.paid(2020, Month.JULY.getValue()));
        return new FeeCalendar(member, months, MemberCalendars.YEAR.getValue());
    }

    public static final FeeCalendar noLastName() {
        final PublicMember member;
        final PersonName   name;

        name = new PersonName(PersonConstants.FIRST_NAME, "");
        member = new PublicMember(PersonConstants.NUMBER, name, false);
        return new FeeCalendar(member, List.of(), MemberCalendars.YEAR.getValue());
    }

    public static final FeeCalendar paidTwoMonthsBack() {
        final PublicMember                 member;
        final Collection<FeeCalendarMonth> months;
        final PersonName                   name;

        name = new PersonName(PersonConstants.FIRST_NAME, PersonConstants.LAST_NAME);
        member = new PublicMember(PersonConstants.NUMBER, name, false);
        months = List.of(FeeMonths.paidTwoMonthsBack());
        return new FeeCalendar(member, months, MemberCalendars.TWO_MONTHS_BACK_DATE.getYear());
    }

    private FeeCalendars() {
        super();
    }

}
