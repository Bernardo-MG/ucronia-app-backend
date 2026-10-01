
package com.bernardomg.association.calendar.test.factory;

import java.time.Instant;

import com.bernardomg.association.calendar.adapter.inbound.jpa.model.CalendarDateEntity;
import com.bernardomg.association.calendar.test.configuration.factory.CalendarDateConstants;

public final class CalendarDateEntities {

    public static final CalendarDateEntity date(final Instant start, final Instant end) {
        final CalendarDateEntity entity;

        entity = new CalendarDateEntity();
        entity.setStart(start);
        entity.setEnd(end);
        return entity;
    }

    public static final CalendarDateEntity valid() {
        final CalendarDateEntity entity;

        entity = new CalendarDateEntity();
        entity.setStart(CalendarDateConstants.START);
        entity.setEnd(CalendarDateConstants.END);
        return entity;
    }

    private CalendarDateEntities() {
        super();
    }

}
