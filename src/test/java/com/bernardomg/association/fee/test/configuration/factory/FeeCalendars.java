
package com.bernardomg.association.fee.test.configuration.factory;

import java.time.YearMonth;
import java.util.Collection;
import java.util.List;

import com.bernardomg.association.fee.domain.model.FeeCalendar;
import com.bernardomg.association.person.domain.model.PersonName;
import com.bernardomg.association.person.test.configuration.factory.PersonConstants;

public final class FeeCalendars {

    public static final FeeCalendar activePaidCurrentMonth() {
        final FeeCalendar.Member                       person;
        final Collection<FeeCalendar.FeeCalendarMonth> months;
        final PersonName                               name;

        name = new PersonName(PersonConstants.FIRST_NAME, PersonConstants.LAST_NAME);
        person = new FeeCalendar.Member(PersonConstants.NUMBER, name, new FeeCalendar.Member.Membership(true));
        months = List.of(FeeMonths.paid());
        return new FeeCalendar(person, months, FeeConstants.CURRENT_YEAR.getValue());
    }

    public static final FeeCalendar inactivePaidCurrentMonth() {
        final FeeCalendar.Member                       person;
        final Collection<FeeCalendar.FeeCalendarMonth> months;
        final PersonName                               name;

        name = new PersonName(PersonConstants.FIRST_NAME, PersonConstants.LAST_NAME);
        person = new FeeCalendar.Member(PersonConstants.NUMBER, name, new FeeCalendar.Member.Membership(false));
        months = List.of(FeeMonths.paid());
        return new FeeCalendar(person, months, YearMonth.now()
            .getYear());
    }

}
