
package com.bernardomg.association.calendar.domain.event;

public final class CalendarEventFactory {

    private static final String SOURCE = "com.bernardomg.ucronia";

    public static CalendarInfoPublishedEvent calendarInfoPublished(final Long calendarNumber) {
        return new CalendarInfoPublishedEvent(SOURCE, calendarNumber);
    }

    private CalendarEventFactory() {
        super();
    }

}
