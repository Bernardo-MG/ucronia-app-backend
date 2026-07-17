
package com.bernardomg.association.calendar.test.factory;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.bernardomg.association.calendar.activity.test.configuration.factory.ActivityConstants;
import com.bernardomg.association.calendar.adapter.inbound.jpa.model.CalendarInfoEntity;

public final class CalendarInfoEntities {

    public static final CalendarInfoEntity draft() {
        final CalendarInfoEntity entity;

        entity = new CalendarInfoEntity();
        entity.setNumber(ActivityConstants.NUMBER);
        entity.setStatus(CalendarStatusEntities.draft());
        entity.setTitle(ActivityConstants.TITLE);
        entity.setDescription(ActivityConstants.DESCRIPTION);
        entity.setLocation(ActivityConstants.LOCATION);
        entity.setCalendarDates(Set.of(CalendarDateEntities.valid()));
        entity.setImage(ActivityConstants.IMAGE);
        entity.setTypes(new HashSet<>(List.of(CalendarTypeEntities.activity())));

        return entity;
    }

    public static final CalendarInfoEntity published() {
        final CalendarInfoEntity entity;

        entity = new CalendarInfoEntity();
        entity.setNumber(ActivityConstants.NUMBER);
        entity.setStatus(CalendarStatusEntities.published());
        entity.setTitle(ActivityConstants.TITLE);
        entity.setDescription(ActivityConstants.DESCRIPTION);
        entity.setLocation(ActivityConstants.LOCATION);
        entity.setCalendarDates(Set.of(CalendarDateEntities.valid()));
        entity.setImage(ActivityConstants.IMAGE);
        entity.setTypes(new HashSet<>(List.of(CalendarTypeEntities.activity())));

        return entity;
    }

    public static final CalendarInfoEntity titleChangePublished() {
        final CalendarInfoEntity entity;

        entity = new CalendarInfoEntity();
        entity.setNumber(ActivityConstants.NUMBER);
        entity.setStatus(CalendarStatusEntities.published());
        entity.setTitle(ActivityConstants.ALTERNATIVE_TITLE);
        entity.setDescription(ActivityConstants.DESCRIPTION);
        entity.setLocation(ActivityConstants.LOCATION);
        entity.setCalendarDates(Set.of(CalendarDateEntities.valid()));
        entity.setImage(ActivityConstants.IMAGE);
        entity.setTypes(new HashSet<>(List.of(CalendarTypeEntities.activity())));

        return entity;
    }

    private CalendarInfoEntities() {
        super();
    }

}
