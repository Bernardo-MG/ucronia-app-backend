
package com.bernardomg.association.calendar.test.factory;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.bernardomg.association.calendar.activity.test.configuration.factory.ActivityConstants;
import com.bernardomg.association.calendar.adapter.inbound.jpa.model.CalendarInfoEntity;

public final class CalendarInfoEntities {

    public static final CalendarInfoEntity created() {
        final CalendarInfoEntity entity = new CalendarInfoEntity();
        entity.setNumber(ActivityConstants.NUMBER);
        entity.setTitle(ActivityConstants.TITLE);
        entity.setDescription(ActivityConstants.DESCRIPTION);
        entity.setLocation(ActivityConstants.LOCATION);
        entity.setCalendarDates(Set.of(CalendarDateEntities.valid()));
        entity.setImage(ActivityConstants.IMAGE);
        entity.setTypes(new HashSet<>(List.of(CalendarTypeEntities.activity())));
        return entity;
    }

    public static final CalendarInfoEntity titleChange() {
        final CalendarInfoEntity entity = new CalendarInfoEntity();
        entity.setNumber(ActivityConstants.NUMBER);
        entity.setTitle(ActivityConstants.ALTERNATIVE_TITLE);
        entity.setDescription(ActivityConstants.DESCRIPTION);
        entity.setLocation(ActivityConstants.LOCATION);
        entity.setCalendarDates(Set.of(CalendarDateEntities.valid()));
        entity.setImage(ActivityConstants.IMAGE);
        entity.setTypes(new HashSet<>(List.of(CalendarTypeEntities.activity())));
        return entity;
    }

    public static final CalendarInfoEntity valid() {
        final CalendarInfoEntity entity = new CalendarInfoEntity();
        entity.setNumber(ActivityConstants.NUMBER);
        entity.setTitle(ActivityConstants.TITLE);
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
