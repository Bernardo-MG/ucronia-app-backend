
package com.bernardomg.association.fee.test.configuration.factory;

import java.time.Month;
import java.time.YearMonth;
import java.util.Collection;
import java.util.List;

import com.bernardomg.association.fee.domain.model.FeeCalendar;
import com.bernardomg.association.fee.domain.model.FeeCalendar.FeeCalendarMonth;
import com.bernardomg.association.member.domain.model.Member;
import com.bernardomg.association.member.test.configuration.factory.MemberCalendars;
import com.bernardomg.association.person.domain.model.PersonName;
import com.bernardomg.association.person.test.configuration.factory.PersonConstants;

public final class FeeCalendars {

    public static final FeeCalendar activeAlternative() {
        final Member                       member;
        final Collection<FeeCalendarMonth> months;
        final PersonName                   name;

        name = new PersonName(PersonConstants.FIRST_NAME, PersonConstants.LAST_NAME);
        member = new Member(PersonConstants.ALTERNATIVE_NUMBER, name);
        months = List.of(FeeMonths.paidAlternative());
        // TODO: don't use member calendar constants
        return new FeeCalendar(member, months, MemberCalendars.YEAR.getValue(), true);
    }

    public static final FeeCalendar activeNextYear() {
        final Member                       member;
        final Collection<FeeCalendarMonth> months;
        final PersonName                   name;

        name = new PersonName(PersonConstants.FIRST_NAME, PersonConstants.LAST_NAME);
        member = new Member(PersonConstants.NUMBER, name);
        months = List.of(FeeMonths.paidNextYear());
        return new FeeCalendar(member, months, MemberCalendars.NEXT_YEAR_DATE.getYear(), true);
    }

    public static final FeeCalendar activeNotPaidCurrentMonth() {
        final Member                       member;
        final Collection<FeeCalendarMonth> months;
        final PersonName                   name;

        name = new PersonName(PersonConstants.FIRST_NAME, PersonConstants.LAST_NAME);
        member = new Member(PersonConstants.NUMBER, name);
        months = List.of(FeeMonths.notPaid());
        return new FeeCalendar(member, months, MemberCalendars.YEAR_CURRENT.getValue(), true);
    }

    public static final FeeCalendar activeNotPaidNextYear() {
        final Member                       member;
        final Collection<FeeCalendarMonth> months;
        final PersonName                   name;

        name = new PersonName(PersonConstants.FIRST_NAME, PersonConstants.LAST_NAME);
        member = new Member(PersonConstants.NUMBER, name);
        months = List.of(FeeMonths.notPaidNextYear());
        return new FeeCalendar(member, months, MemberCalendars.NEXT_YEAR_DATE.getYear(), false);
    }

    public static final FeeCalendar activeNotPaidPreviousMonth() {
        final Member                       member;
        final Collection<FeeCalendarMonth> months;
        final PersonName                   name;

        name = new PersonName(PersonConstants.FIRST_NAME, PersonConstants.LAST_NAME);
        member = new Member(PersonConstants.NUMBER, name);
        months = List.of(FeeMonths.notPaidPreviousMonth());
        return new FeeCalendar(member, months, MemberCalendars.PREVIOUS_MONTH_DATE.getYear(), true);
    }

    public static final FeeCalendar activeNotPaidTwoMonthsBack() {
        final Member                       member;
        final Collection<FeeCalendarMonth> months;
        final PersonName                   name;

        name = new PersonName(PersonConstants.FIRST_NAME, PersonConstants.LAST_NAME);
        member = new Member(PersonConstants.NUMBER, name);
        months = List.of(FeeMonths.notPaidTwoMonthsBack());
        return new FeeCalendar(member, months, MemberCalendars.TWO_MONTHS_BACK_DATE.getYear(), true);
    }

    public static final FeeCalendar activePaidCurrentMonth() {
        final Member                       member;
        final Collection<FeeCalendarMonth> months;
        final PersonName                   name;

        name = new PersonName(PersonConstants.FIRST_NAME, PersonConstants.LAST_NAME);
        member = new Member(PersonConstants.NUMBER, name);
        months = List.of(FeeMonths.paid());
        return new FeeCalendar(member, months, MemberCalendars.YEAR_CURRENT.getValue(), true);
    }

    public static final FeeCalendar activePaidNextYear() {
        final Member                       member;
        final Collection<FeeCalendarMonth> months;
        final PersonName                   name;

        name = new PersonName(PersonConstants.FIRST_NAME, PersonConstants.LAST_NAME);
        member = new Member(PersonConstants.NUMBER, name);
        months = List.of(FeeMonths.paidNextYear());
        return new FeeCalendar(member, months, MemberCalendars.NEXT_YEAR_DATE.getYear(), true);
    }

    public static final FeeCalendar activePaidTwoMonthsBack() {
        final Member                       member;
        final Collection<FeeCalendarMonth> months;
        final PersonName                   name;

        name = new PersonName(PersonConstants.FIRST_NAME, PersonConstants.LAST_NAME);
        member = new Member(PersonConstants.NUMBER, name);
        months = List.of(FeeMonths.paidTwoMonthsBack());
        return new FeeCalendar(member, months, MemberCalendars.TWO_MONTHS_BACK_DATE.getYear(), true);
    }

    public static final FeeCalendar activePreviousMonth() {
        final Member                       member;
        final Collection<FeeCalendarMonth> months;
        final PersonName                   name;

        name = new PersonName(PersonConstants.FIRST_NAME, PersonConstants.LAST_NAME);
        member = new Member(PersonConstants.NUMBER, name);
        months = List.of(FeeMonths.paidPreviousMonth());
        return new FeeCalendar(member, months, MemberCalendars.PREVIOUS_MONTH_DATE.getYear(), true);
    }

    public static final FeeCalendar currentActive() {
        final Member     member;
        final PersonName name;

        name = new PersonName(PersonConstants.FIRST_NAME, PersonConstants.LAST_NAME);
        member = new Member(PersonConstants.NUMBER, name);
        return new FeeCalendar(member, List.of(), YearMonth.now()
            .getYear(), true);
    }

    public static final FeeCalendar currentInactive() {
        final Member     member;
        final PersonName name;

        name = new PersonName(PersonConstants.FIRST_NAME, PersonConstants.LAST_NAME);
        member = new Member(PersonConstants.NUMBER, name);
        return new FeeCalendar(member, List.of(), YearMonth.now()
            .getYear(), false);
    }

    public static final FeeCalendar inactive() {
        final Member                       member;
        final Collection<FeeCalendarMonth> months;
        final PersonName                   name;

        name = new PersonName(PersonConstants.FIRST_NAME, PersonConstants.LAST_NAME);
        member = new Member(PersonConstants.NUMBER, name);
        months = List.of(FeeMonths.notPaid());
        return new FeeCalendar(member, months, MemberCalendars.YEAR.getValue(), false);
    }

    public static final FeeCalendar inactiveAlternative() {
        final Member                       member;
        final Collection<FeeCalendarMonth> months;
        final PersonName                   name;

        name = new PersonName(PersonConstants.ALTERNATIVE_FIRST_NAME, PersonConstants.ALTERNATIVE_LAST_NAME);
        member = new Member(PersonConstants.ALTERNATIVE_NUMBER, name);
        months = List.of(FeeMonths.notPaidAlternative());
        return new FeeCalendar(member, months, MemberCalendars.YEAR.getValue(), false);
    }

    public static final FeeCalendar inactivefullCalendar() {
        final Member                       member;
        final Collection<FeeCalendarMonth> months;
        final PersonName                   name;

        name = new PersonName(PersonConstants.FIRST_NAME, PersonConstants.LAST_NAME);
        member = new Member(PersonConstants.NUMBER, name);
        months = List.of(FeeMonths.paidAtMonth(1), FeeMonths.paidAtMonth(2), FeeMonths.paidAtMonth(3),
            FeeMonths.paidAtMonth(4), FeeMonths.paidAtMonth(5), FeeMonths.paidAtMonth(6), FeeMonths.paidAtMonth(7),
            FeeMonths.paidAtMonth(8), FeeMonths.paidAtMonth(9), FeeMonths.paidAtMonth(10), FeeMonths.paidAtMonth(11),
            FeeMonths.paidAtMonth(12));
        return new FeeCalendar(member, months, MemberCalendars.YEAR.getValue(), false);
    }

    public static final FeeCalendar inactivefullCalendarAlternative() {
        final Member                       member;
        final Collection<FeeCalendarMonth> months;
        final PersonName                   name;

        name = new PersonName(PersonConstants.ALTERNATIVE_FIRST_NAME, PersonConstants.ALTERNATIVE_LAST_NAME);
        member = new Member(PersonConstants.ALTERNATIVE_NUMBER, name);
        months = List.of(FeeMonths.paidAtMonth(1), FeeMonths.paidAtMonth(2), FeeMonths.paidAtMonth(3),
            FeeMonths.paidAtMonth(4), FeeMonths.paidAtMonth(5), FeeMonths.paidAtMonth(6), FeeMonths.paidAtMonth(7),
            FeeMonths.paidAtMonth(8), FeeMonths.paidAtMonth(9), FeeMonths.paidAtMonth(10), FeeMonths.paidAtMonth(11),
            FeeMonths.paidAtMonth(12));
        return new FeeCalendar(member, months, MemberCalendars.YEAR.getValue(), false);
    }

    public static final FeeCalendar inactivefullCalendarNoLastName() {
        final Member                       member;
        final Collection<FeeCalendarMonth> months;
        final PersonName                   name;

        name = new PersonName(PersonConstants.FIRST_NAME, PersonConstants.LAST_NAME);
        member = new Member(PersonConstants.NUMBER, name);
        months = List.of(FeeMonths.paidAtMonth(1), FeeMonths.paidAtMonth(2), FeeMonths.paidAtMonth(3),
            FeeMonths.paidAtMonth(4), FeeMonths.paidAtMonth(5), FeeMonths.paidAtMonth(6), FeeMonths.paidAtMonth(7),
            FeeMonths.paidAtMonth(8), FeeMonths.paidAtMonth(9), FeeMonths.paidAtMonth(10), FeeMonths.paidAtMonth(11),
            FeeMonths.paidAtMonth(12));
        return new FeeCalendar(member, months, MemberCalendars.YEAR.getValue(), false);
    }

    public static final FeeCalendar inactivefullCalendarNoName() {
        final Member                       member;
        final Collection<FeeCalendarMonth> months;
        final PersonName                   name;

        name = new PersonName("", "");
        member = new Member(PersonConstants.NUMBER, name);
        months = List.of(FeeMonths.paidAtMonth(1), FeeMonths.paidAtMonth(2), FeeMonths.paidAtMonth(3),
            FeeMonths.paidAtMonth(4), FeeMonths.paidAtMonth(5), FeeMonths.paidAtMonth(6), FeeMonths.paidAtMonth(7),
            FeeMonths.paidAtMonth(8), FeeMonths.paidAtMonth(9), FeeMonths.paidAtMonth(10), FeeMonths.paidAtMonth(11),
            FeeMonths.paidAtMonth(12));
        return new FeeCalendar(member, months, MemberCalendars.YEAR.getValue(), false);
    }

    public static final FeeCalendar inactiveNotPaidNextYear() {
        final Member                       member;
        final Collection<FeeCalendarMonth> months;
        final PersonName                   name;

        name = new PersonName(PersonConstants.FIRST_NAME, PersonConstants.LAST_NAME);
        member = new Member(PersonConstants.NUMBER, name);
        months = List.of(FeeMonths.notPaidNextYear());
        return new FeeCalendar(member, months, MemberCalendars.NEXT_YEAR_DATE.getYear(), false);
    }

    public static final FeeCalendar inactiveNotPaidPreviousMonth() {
        final Member                       member;
        final Collection<FeeCalendarMonth> months;
        final PersonName                   name;

        name = new PersonName(PersonConstants.FIRST_NAME, PersonConstants.LAST_NAME);
        member = new Member(PersonConstants.NUMBER, name);
        months = List.of(FeeMonths.notPaidPreviousMonth());
        return new FeeCalendar(member, months, MemberCalendars.PREVIOUS_MONTH_DATE.getYear(), false);
    }

    public static final FeeCalendar inactiveNotPaidTwoMonthsBack() {
        final Member                       member;
        final Collection<FeeCalendarMonth> months;
        final PersonName                   name;

        name = new PersonName(PersonConstants.FIRST_NAME, PersonConstants.LAST_NAME);
        member = new Member(PersonConstants.NUMBER, name);
        months = List.of(FeeMonths.notPaidTwoMonthsBack());
        return new FeeCalendar(member, months, MemberCalendars.TWO_MONTHS_BACK_DATE.getYear(), false);
    }

    public static final FeeCalendar inactivePaidCurrentMonth() {
        final Member                       member;
        final Collection<FeeCalendarMonth> months;
        final PersonName                   name;

        name = new PersonName(PersonConstants.FIRST_NAME, PersonConstants.LAST_NAME);
        member = new Member(PersonConstants.NUMBER, name);
        months = List.of(FeeMonths.paid());
        return new FeeCalendar(member, months, MemberCalendars.YEAR_CURRENT.getValue(), false);
    }

    public static final FeeCalendar inactivePaidNextYear() {
        final Member                       member;
        final Collection<FeeCalendarMonth> months;
        final PersonName                   name;

        name = new PersonName(PersonConstants.FIRST_NAME, PersonConstants.LAST_NAME);
        member = new Member(PersonConstants.NUMBER, name);
        months = List.of(FeeMonths.paidNextYear());
        return new FeeCalendar(member, months, MemberCalendars.NEXT_YEAR_DATE.getYear(), false);
    }

    public static final FeeCalendar inactivePaidTwoMonthsBack() {
        final Member                       member;
        final Collection<FeeCalendarMonth> months;
        final PersonName                   name;

        name = new PersonName(PersonConstants.FIRST_NAME, PersonConstants.LAST_NAME);
        member = new Member(PersonConstants.NUMBER, name);
        months = List.of(FeeMonths.paidTwoMonthsBack());
        return new FeeCalendar(member, months, MemberCalendars.TWO_MONTHS_BACK_DATE.getYear(), false);
    }

    public static final FeeCalendar inactivePreviousMonth() {
        final Member                       member;
        final Collection<FeeCalendarMonth> months;
        final PersonName                   name;

        name = new PersonName(PersonConstants.FIRST_NAME, PersonConstants.LAST_NAME);
        member = new Member(PersonConstants.NUMBER, name);
        months = List.of(FeeMonths.paidPreviousMonth());
        return new FeeCalendar(member, months, MemberCalendars.PREVIOUS_MONTH_DATE.getYear(), false);
    }

    public static final FeeCalendar inactivePreviousYear() {
        final Member     member;
        final PersonName name;

        name = new PersonName(PersonConstants.FIRST_NAME, PersonConstants.LAST_NAME);
        member = new Member(PersonConstants.NUMBER, name);
        return new FeeCalendar(member, List.of(), MemberCalendars.YEAR_PREVIOUS.getValue(), false);
    }

    public static final FeeCalendar inactiveTwoConnectedFirst() {
        final Member                       member;
        final Collection<FeeCalendarMonth> months;
        final PersonName                   name;

        name = new PersonName(PersonConstants.FIRST_NAME, PersonConstants.LAST_NAME);
        member = new Member(PersonConstants.NUMBER, name);
        months = List.of(FeeMonths.paid(MemberCalendars.YEAR_PREVIOUS.getValue(), Month.OCTOBER.getValue()),
            FeeMonths.paid(MemberCalendars.YEAR_PREVIOUS.getValue(), Month.NOVEMBER.getValue()),
            FeeMonths.paid(MemberCalendars.YEAR_PREVIOUS.getValue(), Month.DECEMBER.getValue()));
        return new FeeCalendar(member, months, MemberCalendars.YEAR_PREVIOUS.getValue(), false);
    }

    public static final FeeCalendar inactiveTwoConnectedSecond() {
        final Member                       member;
        final Collection<FeeCalendarMonth> months;
        final PersonName                   name;

        name = new PersonName(PersonConstants.FIRST_NAME, PersonConstants.LAST_NAME);
        member = new Member(PersonConstants.NUMBER, name);
        months = List.of(FeeMonths.paid(2020, Month.JANUARY.getValue()),
            FeeMonths.paid(2020, Month.FEBRUARY.getValue()), FeeMonths.paid(2020, Month.MARCH.getValue()),
            FeeMonths.paid(2020, Month.APRIL.getValue()), FeeMonths.paid(2020, Month.MAY.getValue()),
            FeeMonths.paid(2020, Month.JUNE.getValue()), FeeMonths.paid(2020, Month.JULY.getValue()));
        return new FeeCalendar(member, months, MemberCalendars.YEAR.getValue(), false);
    }

    public static final FeeCalendar noLastName() {
        final Member     member;
        final PersonName name;

        name = new PersonName(PersonConstants.FIRST_NAME, "");
        member = new Member(PersonConstants.NUMBER, name);
        return new FeeCalendar(member, List.of(), MemberCalendars.YEAR.getValue(), false);
    }

    public static final FeeCalendar paidTwoMonthsBack() {
        final Member                       member;
        final Collection<FeeCalendarMonth> months;
        final PersonName                   name;

        name = new PersonName(PersonConstants.FIRST_NAME, PersonConstants.LAST_NAME);
        member = new Member(PersonConstants.NUMBER, name);
        months = List.of(FeeMonths.paidTwoMonthsBack());
        return new FeeCalendar(member, months, MemberCalendars.TWO_MONTHS_BACK_DATE.getYear(), false);
    }

    private FeeCalendars() {
        super();
    }

}
