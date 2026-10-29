
package com.bernardomg.schedule.event;

import java.time.YearMonth;

/** Creates events with the Ucronia producer identity. */
public final class ScheduleEventFactory {

    private static final String SOURCE = "com.bernardomg.ucronia";

    public static MonthStartEvent monthStarted(final YearMonth month) {
        return new MonthStartEvent(SOURCE, month);
    }

    private ScheduleEventFactory() {
        super();
    }

}
