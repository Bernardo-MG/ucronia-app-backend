
package com.bernardomg.association.calendar.test.factory;

import com.bernardomg.association.calendar.adapter.inbound.jpa.model.CalendarDateEntity;
import com.bernardomg.association.calendar.test.configuration.factory.CalendarDateConstants;

public final class CalendarDateEntities {

    public static final CalendarDateEntity valid() {
        final CalendarDateEntity entity = new CalendarDateEntity();
        entity.setStart(CalendarDateConstants.START);
        entity.setEnd(CalendarDateConstants.END);
        return entity;
    }

    private CalendarDateEntities() {
        super();
    }

}
