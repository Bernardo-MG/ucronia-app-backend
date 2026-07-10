
package com.bernardomg.association.calendar.test.factory;

import com.bernardomg.association.calendar.adapter.inbound.jpa.model.CalendarStatusEntity;
import com.bernardomg.association.calendar.domain.model.CalendarStatus;

public final class CalendarStatusEntities {

    public static final CalendarStatusEntity draft() {
        final CalendarStatusEntity entity;

        entity = new CalendarStatusEntity();
        entity.setId(1L);
        entity.setName(CalendarStatus.DRAFT);
        return entity;
    }

    public static final CalendarStatusEntity published() {
        final CalendarStatusEntity entity;

        entity = new CalendarStatusEntity();
        entity.setId(3L);
        entity.setName(CalendarStatus.PUBLISHED);
        return entity;
    }

    private CalendarStatusEntities() {
        super();
    }

}
