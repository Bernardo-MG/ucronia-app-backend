
package com.bernardomg.schedule.event;

import java.time.Instant;

public final class ScheduleEventFactory {

    private static final String SOURCE = "com.bernardomg.ucronia";

    public static MonthStartEvent monthStarted(final Instant month) {
        return new MonthStartEvent(SOURCE, month);
    }

    private ScheduleEventFactory() {
        super();
    }

}
