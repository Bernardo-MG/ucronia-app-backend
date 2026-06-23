
package com.bernardomg.association.activity.test.factory;

import com.bernardomg.association.activity.adapter.inbound.jpa.model.CalendarInfoEntity;
import com.bernardomg.association.activity.test.configuration.factory.ActivityConstants;

public final class CalendarInfoEntities {

    public static final CalendarInfoEntity titleChange() {
        final CalendarInfoEntity entity = new CalendarInfoEntity();
        entity.setNumber(ActivityConstants.NUMBER);
        entity.setTitle(ActivityConstants.ALTERNATIVE_TITLE);
        entity.setDescription(ActivityConstants.DESCRIPTION);
        entity.setLocation(ActivityConstants.LOCATION);
        entity.setCalendarDate(CalendarDateEntities.valid());
        entity.setImage(ActivityConstants.IMAGE);
        return entity;
    }

    public static final CalendarInfoEntity valid() {
        final CalendarInfoEntity entity = new CalendarInfoEntity();
        entity.setNumber(ActivityConstants.NUMBER);
        entity.setTitle(ActivityConstants.TITLE);
        entity.setDescription(ActivityConstants.DESCRIPTION);
        entity.setLocation(ActivityConstants.LOCATION);
        entity.setCalendarDate(CalendarDateEntities.valid());
        entity.setImage(ActivityConstants.IMAGE);
        return entity;
    }

    private CalendarInfoEntities() {
        super();
    }

}
