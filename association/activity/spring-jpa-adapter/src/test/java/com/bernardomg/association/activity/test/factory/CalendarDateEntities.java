
package com.bernardomg.association.activity.test.factory;

import com.bernardomg.association.activity.adapter.inbound.jpa.model.CalendarDateEntity;
import com.bernardomg.association.activity.test.configuration.factory.CalendarDayConstants;

public final class CalendarDateEntities {

    public static final CalendarDateEntity start() {
        final CalendarDateEntity entity = new CalendarDateEntity();
        entity.setTitle(CalendarDayConstants.TITLE);
        entity.setStart(CalendarDayConstants.START);
        entity.setEnd(null);
        return entity;
    }

    private CalendarDateEntities() {
        super();
    }

}
