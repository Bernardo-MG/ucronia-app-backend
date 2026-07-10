
package com.bernardomg.association.calendar.test.factory;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.bernardomg.association.calendar.activity.test.configuration.factory.ActivityConstants;
import com.bernardomg.association.calendar.adapter.inbound.jpa.model.CalendarInfoEntity;
import com.bernardomg.association.calendar.game.adapter.inbound.jpa.model.RecurrenceEmbeddable;
import com.bernardomg.association.calendar.game.domain.model.Recurrence;

public final class CalendarInfoEntities {

    public static final CalendarInfoEntity draft() {
        final CalendarInfoEntity   entity;
        final RecurrenceEmbeddable recurrence;

        entity = new CalendarInfoEntity();
        entity.setNumber(ActivityConstants.NUMBER);
        entity.setStatus(CalendarStatusEntities.draft());
        entity.setTitle(ActivityConstants.TITLE);
        entity.setDescription(ActivityConstants.DESCRIPTION);
        entity.setLocation(ActivityConstants.LOCATION);
        entity.setCalendarDates(Set.of(CalendarDateEntities.valid()));
        entity.setImage(ActivityConstants.IMAGE);
        entity.setTypes(new HashSet<>(List.of(CalendarTypeEntities.activity())));

        recurrence = new RecurrenceEmbeddable();
        recurrence.setInterval(0);
        recurrence.setUnit(Recurrence.RecurrenceUnit.DAILY);

        entity.setRecurrence(recurrence);

        return entity;
    }

    public static final CalendarInfoEntity published() {
        final CalendarInfoEntity   entity;
        final RecurrenceEmbeddable recurrence;

        entity = new CalendarInfoEntity();
        entity.setNumber(ActivityConstants.NUMBER);
        entity.setStatus(CalendarStatusEntities.published());
        entity.setTitle(ActivityConstants.TITLE);
        entity.setDescription(ActivityConstants.DESCRIPTION);
        entity.setLocation(ActivityConstants.LOCATION);
        entity.setCalendarDates(Set.of(CalendarDateEntities.valid()));
        entity.setImage(ActivityConstants.IMAGE);
        entity.setTypes(new HashSet<>(List.of(CalendarTypeEntities.activity())));

        recurrence = new RecurrenceEmbeddable();
        recurrence.setInterval(0);
        recurrence.setUnit(Recurrence.RecurrenceUnit.DAILY);

        entity.setRecurrence(recurrence);

        return entity;
    }

    public static final CalendarInfoEntity titleChangePublished() {
        final CalendarInfoEntity   entity;
        final RecurrenceEmbeddable recurrence;

        entity = new CalendarInfoEntity();
        entity.setNumber(ActivityConstants.NUMBER);
        entity.setStatus(CalendarStatusEntities.published());
        entity.setTitle(ActivityConstants.ALTERNATIVE_TITLE);
        entity.setDescription(ActivityConstants.DESCRIPTION);
        entity.setLocation(ActivityConstants.LOCATION);
        entity.setCalendarDates(Set.of(CalendarDateEntities.valid()));
        entity.setImage(ActivityConstants.IMAGE);
        entity.setTypes(new HashSet<>(List.of(CalendarTypeEntities.activity())));

        recurrence = new RecurrenceEmbeddable();
        recurrence.setInterval(0);
        recurrence.setUnit(Recurrence.RecurrenceUnit.DAILY);

        entity.setRecurrence(recurrence);

        return entity;
    }

    private CalendarInfoEntities() {
        super();
    }

}
