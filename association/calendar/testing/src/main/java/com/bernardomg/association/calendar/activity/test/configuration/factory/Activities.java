
package com.bernardomg.association.calendar.activity.test.configuration.factory;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.List;

import com.bernardomg.association.calendar.activity.domain.model.Activity;
import com.bernardomg.association.calendar.activity.domain.model.Activity.ActivityDate;
import com.bernardomg.association.calendar.test.configuration.factory.CalendarDateConstants;

public final class Activities {

    public static final Activity forNumberAndMonth(final long number, final Month month) {
        // TODO: constant for the year
        final Instant      start;
        final Instant      end;
        final ActivityDate date;

        start = LocalDateTime.of(2020, month, (int) number, 14, 0)
            .toInstant(ZoneOffset.UTC);
        end = LocalDateTime.of(2020, month, (int) number, 21, 0)
            .toInstant(ZoneOffset.UTC);
        date = new ActivityDate(start, end);

        return new Activity(number, ActivityConstants.TITLE + " " + number,
            ActivityConstants.DESCRIPTION + " " + number, ActivityConstants.LOCATION + " " + number,
            ActivityConstants.IMAGE.replace(".png", "") + "_" + number + ".png", List.of(date));
    }

    public static final Activity future() {
        final ActivityDate date;

        date = new ActivityDate(CalendarDateConstants.START_FUTURE, CalendarDateConstants.END_FUTURE);

        return new Activity(ActivityConstants.NUMBER, ActivityConstants.TITLE, ActivityConstants.DESCRIPTION,
            ActivityConstants.LOCATION, ActivityConstants.IMAGE, List.of(date));
    }

    public static final Activity multipleDay() {
        final ActivityDate date1;
        final ActivityDate date2;
        final ActivityDate date3;
        final ActivityDate date4;
        final ActivityDate date5;

        date1 = new ActivityDate(CalendarDateConstants.START, CalendarDateConstants.END);
        date2 = new ActivityDate(CalendarDateConstants.START.plus(1L, ChronoUnit.DAYS),
            CalendarDateConstants.END.plus(1L, ChronoUnit.DAYS));
        date3 = new ActivityDate(CalendarDateConstants.START.plus(2L, ChronoUnit.DAYS),
            CalendarDateConstants.END.plus(2L, ChronoUnit.DAYS));
        date4 = new ActivityDate(CalendarDateConstants.START.plus(3L, ChronoUnit.DAYS),
            CalendarDateConstants.END.plus(3L, ChronoUnit.DAYS));
        date5 = new ActivityDate(CalendarDateConstants.START.plus(4L, ChronoUnit.DAYS),
            CalendarDateConstants.END.plus(4L, ChronoUnit.DAYS));

        return new Activity(ActivityConstants.NUMBER, ActivityConstants.TITLE, ActivityConstants.DESCRIPTION,
            ActivityConstants.LOCATION, ActivityConstants.IMAGE, List.of(date1, date2, date3, date4, date5));
    }

    public static final Activity sameDate() {
        final ActivityDate date;

        date = new ActivityDate(CalendarDateConstants.START, CalendarDateConstants.START);

        return new Activity(ActivityConstants.NUMBER, ActivityConstants.TITLE, ActivityConstants.DESCRIPTION,
            ActivityConstants.LOCATION, ActivityConstants.IMAGE, List.of(date));
    }

    public static final Activity singleDay() {
        final ActivityDate date;

        date = new ActivityDate(CalendarDateConstants.START, CalendarDateConstants.END);

        return new Activity(ActivityConstants.NUMBER, ActivityConstants.TITLE, ActivityConstants.DESCRIPTION,
            ActivityConstants.LOCATION, ActivityConstants.IMAGE, List.of(date));
    }

    public static final Activity startsAfterEnd() {
        final ActivityDate date;

        date = new ActivityDate(CalendarDateConstants.END, CalendarDateConstants.START);

        return new Activity(ActivityConstants.NUMBER, ActivityConstants.TITLE, ActivityConstants.DESCRIPTION,
            ActivityConstants.LOCATION, ActivityConstants.IMAGE, List.of(date));
    }

    public static final Activity titleChange() {
        final ActivityDate date;

        date = new ActivityDate(CalendarDateConstants.START, CalendarDateConstants.END);

        return new Activity(ActivityConstants.NUMBER, ActivityConstants.ALTERNATIVE_TITLE,
            ActivityConstants.DESCRIPTION, ActivityConstants.LOCATION, ActivityConstants.IMAGE, List.of(date));
    }

    private Activities() {
        super();
    }

}
