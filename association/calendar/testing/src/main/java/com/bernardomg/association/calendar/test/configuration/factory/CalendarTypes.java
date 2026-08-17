
package com.bernardomg.association.calendar.test.configuration.factory;

import com.bernardomg.association.calendar.domain.model.CalendarType;

public final class CalendarTypes {

    public static CalendarType activity() {
        return new CalendarType(CalendarTypeConstants.NUMBER, CalendarTypeConstants.NAME, CalendarTypeConstants.COLOR);
    }

    public static CalendarType custom() {
        return new CalendarType(CalendarTypeConstants.NEXT_NUMBER, CalendarTypeConstants.ALTERNATIVE_NAME,
            CalendarTypeConstants.ALTERNATIVE_COLOR);
    }

    public static CalendarType nameChange() {
        return new CalendarType(CalendarTypeConstants.NUMBER, CalendarTypeConstants.ALTERNATIVE_NAME,
            CalendarTypeConstants.COLOR);
    }

    private CalendarTypes() {
        super();
    }

}
