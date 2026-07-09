
package com.bernardomg.association.calendar.test.factory;

import com.bernardomg.association.calendar.activity.adapter.inbound.jpa.model.ActivityEntityConstants;
import com.bernardomg.association.calendar.activity.test.configuration.factory.ActivityConstants;
import com.bernardomg.association.calendar.adapter.inbound.jpa.model.CalendarTypeEntity;
import com.bernardomg.association.calendar.game.test.configuration.factory.ScheduledGameConstants;

public class CalendarTypeEntities {

    public static CalendarTypeEntity activity() {
        final CalendarTypeEntity entity = new CalendarTypeEntity();
        entity.setId(ActivityEntityConstants.TYPE);
        entity.setNumber(ActivityConstants.CALENDAR_TYPE_NUMBER);
        entity.setName(ActivityConstants.CALENDAR_TYPE_NAME);
        entity.setColor(ActivityConstants.CALENDAR_TYPE_COLOR);
        return entity;
    }

    public static CalendarTypeEntity campaign() {
        final CalendarTypeEntity entity = new CalendarTypeEntity();
        entity.setId(ActivityEntityConstants.TYPE);
        entity.setNumber(ScheduledGameConstants.CALENDAR_TYPE_CAMPAIGN_NUMBER);
        entity.setName(ScheduledGameConstants.CALENDAR_TYPE_CAMPAIGN_NAME);
        entity.setColor(ScheduledGameConstants.CALENDAR_TYPE_CAMPAIGN_COLOR);
        return entity;
    }

    public static CalendarTypeEntity oneshot() {
        final CalendarTypeEntity entity = new CalendarTypeEntity();
        entity.setId(ActivityEntityConstants.TYPE);
        entity.setNumber(ScheduledGameConstants.CALENDAR_TYPE_ONESHOT_NUMBER);
        entity.setName(ScheduledGameConstants.CALENDAR_TYPE_ONESHOT_NAME);
        entity.setColor(ScheduledGameConstants.CALENDAR_TYPE_ONESHOT_COLOR);
        return entity;
    }

    private CalendarTypeEntities() {
        super();
    }

}
