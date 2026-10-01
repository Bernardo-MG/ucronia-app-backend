
package com.bernardomg.association.calendar.test.factory;

import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.List;

import com.bernardomg.association.calendar.activity.test.configuration.factory.ActivityConstants;
import com.bernardomg.association.calendar.adapter.inbound.jpa.model.CalendarDateEntity;
import com.bernardomg.association.calendar.adapter.inbound.jpa.model.CalendarInfoEntity;
import com.bernardomg.association.calendar.test.configuration.factory.CalendarDateConstants;

public final class CalendarInfoEntities {

    public static final CalendarInfoEntity draft() {
        final CalendarInfoEntity entity;

        entity = new CalendarInfoEntity();
        entity.setNumber(ActivityConstants.NUMBER);
        entity.setStatus(CalendarStatusEntities.draft());
        entity.setTitle(ActivityConstants.TITLE);
        entity.setDescription(ActivityConstants.DESCRIPTION);
        entity.setLocation(ActivityConstants.LOCATION);
        entity.setCalendarDates(List.of(CalendarDateEntities.valid()));
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
        entity.setCalendarDates(List.of(CalendarDateEntities.valid()));
        entity.setImage(ActivityConstants.IMAGE);
        entity.setTypes(new HashSet<>(List.of(CalendarTypeEntities.activity())));

        return entity;
    }

    public static final CalendarInfoEntity publishedWithMultipleDays() {
        final CalendarInfoEntity entity;
        final CalendarDateEntity date1;
        final CalendarDateEntity date2;
        final CalendarDateEntity date3;
        final CalendarDateEntity date4;
        final CalendarDateEntity date5;

        date1 = CalendarDateEntities.date(CalendarDateConstants.START, CalendarDateConstants.END);
        date2 = CalendarDateEntities.date(CalendarDateConstants.START.plus(1L, ChronoUnit.DAYS),
            CalendarDateConstants.END.plus(1L, ChronoUnit.DAYS));
        date3 = CalendarDateEntities.date(CalendarDateConstants.START.plus(2L, ChronoUnit.DAYS),
            CalendarDateConstants.END.plus(2L, ChronoUnit.DAYS));
        date4 = CalendarDateEntities.date(CalendarDateConstants.START.plus(3L, ChronoUnit.DAYS),
            CalendarDateConstants.END.plus(3L, ChronoUnit.DAYS));
        date5 = CalendarDateEntities.date(CalendarDateConstants.START.plus(4L, ChronoUnit.DAYS),
            CalendarDateConstants.END.plus(4L, ChronoUnit.DAYS));

        entity = new CalendarInfoEntity();
        entity.setNumber(ActivityConstants.NUMBER);
        entity.setStatus(CalendarStatusEntities.published());
        entity.setTitle(ActivityConstants.TITLE);
        entity.setDescription(ActivityConstants.DESCRIPTION);
        entity.setLocation(ActivityConstants.LOCATION);
        entity.setCalendarDates(List.of(date1, date2, date3, date4, date5));
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
        entity.setCalendarDates(List.of(CalendarDateEntities.valid()));
        entity.setImage(ActivityConstants.IMAGE);
        entity.setTypes(new HashSet<>(List.of(CalendarTypeEntities.activity())));

        return entity;
    }

    private CalendarInfoEntities() {
        super();
    }

}
