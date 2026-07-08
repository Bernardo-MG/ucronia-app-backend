
package com.bernardomg.association.calendar.test.factory;

import com.bernardomg.association.calendar.activity.adapter.inbound.jpa.model.ActivityEntityConstants;
import com.bernardomg.association.calendar.activity.test.configuration.factory.ActivityConstants;
import com.bernardomg.association.calendar.adapter.inbound.jpa.model.CalendarTypeEntity;
import com.bernardomg.association.calendar.game.test.configuration.factory.ScheduledGameConstants;

public class CalendarTypeEntities {

    public static CalendarTypeEntity activity() {
        final CalendarTypeEntity entity = new CalendarTypeEntity();
        entity.setId(ActivityEntityConstants.PROFILE_TYPE);
        entity.setNumber(ActivityConstants.PROFILE_TYPE_NUMBER);
        entity.setName(ActivityConstants.PROFILE_TYPE_NAME);
        entity.setColor(ActivityConstants.PROFILE_TYPE_COLOR);
        return entity;
    }

    public static CalendarTypeEntity game() {
        final CalendarTypeEntity entity = new CalendarTypeEntity();
        entity.setId(ActivityEntityConstants.PROFILE_TYPE);
        entity.setNumber(ScheduledGameConstants.PROFILE_TYPE_NUMBER);
        entity.setName(ScheduledGameConstants.PROFILE_TYPE_NAME);
        entity.setColor(ScheduledGameConstants.PROFILE_TYPE_COLOR);
        return entity;
    }

    private CalendarTypeEntities() {
        super();
    }

}
