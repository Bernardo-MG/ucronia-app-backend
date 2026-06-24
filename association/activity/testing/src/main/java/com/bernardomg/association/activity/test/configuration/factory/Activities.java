
package com.bernardomg.association.activity.test.configuration.factory;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneOffset;
import java.util.List;

import com.bernardomg.association.activity.domain.model.Activity;
import com.bernardomg.association.activity.domain.model.Activity.ActivityDate;

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
            ActivityConstants.IMAGE + "_" + number, List.of(date));
    }

    public static final Activity future() {
        final ActivityDate date;

        date = new ActivityDate(CalendarDateConstants.START_FUTURE, CalendarDateConstants.END_FUTURE);

        return new Activity(ActivityConstants.NUMBER, ActivityConstants.TITLE, ActivityConstants.DESCRIPTION,
            ActivityConstants.LOCATION, ActivityConstants.IMAGE, List.of(date));
    }

    public static final Activity titleChange() {
        final ActivityDate date;

        date = new ActivityDate(CalendarDateConstants.START, CalendarDateConstants.END);

        return new Activity(ActivityConstants.NUMBER, ActivityConstants.ALTERNATIVE_TITLE,
            ActivityConstants.DESCRIPTION, ActivityConstants.LOCATION, ActivityConstants.IMAGE, List.of(date));
    }

    public static final Activity valid() {
        final ActivityDate date;

        date = new ActivityDate(CalendarDateConstants.START, CalendarDateConstants.END);

        return new Activity(ActivityConstants.NUMBER, ActivityConstants.TITLE, ActivityConstants.DESCRIPTION,
            ActivityConstants.LOCATION, ActivityConstants.IMAGE, List.of(date));
    }

    private Activities() {
        super();
    }

}
