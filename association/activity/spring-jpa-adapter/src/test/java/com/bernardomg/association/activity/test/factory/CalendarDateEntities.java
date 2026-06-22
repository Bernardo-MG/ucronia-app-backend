
package com.bernardomg.association.activity.test.factory;

import com.bernardomg.association.activity.adapter.inbound.jpa.model.CalendarDateEntity;
import com.bernardomg.association.activity.test.configuration.factory.CalendarDateConstants;

public final class CalendarDateEntities {

    public static final CalendarDateEntity valid() {
        final CalendarDateEntity entity = new CalendarDateEntity();
        entity.setTitle(CalendarDateConstants.TITLE);
        entity.setStart(CalendarDateConstants.START);
        entity.setEnd(CalendarDateConstants.END);
        return entity;
    }

    private CalendarDateEntities() {
        super();
    }

}
