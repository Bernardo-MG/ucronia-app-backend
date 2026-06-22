
package com.bernardomg.association.activity.test.configuration.factory;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneOffset;

import com.bernardomg.association.activity.domain.model.Activity;

public final class Activities {

    public static final Activity forNumberAndMonth(final long number, final Month month) {
        // TODO: constant for the year
        final Instant start;
        final Instant end;

        start = LocalDateTime.of(2020, month, (int) number, 14, 0)
            .toInstant(ZoneOffset.UTC);
        end = LocalDateTime.of(2020, month, (int) number, 21, 0)
            .toInstant(ZoneOffset.UTC);
        return new Activity(number, ActivityConstants.TITLE + " " + number,
            ActivityConstants.DESCRIPTION + " " + number, ActivityConstants.IMAGE + "_" + number, start, end);
    }

    public static final Activity future() {
        return new Activity(ActivityConstants.NUMBER, ActivityConstants.TITLE, ActivityConstants.DESCRIPTION,
            ActivityConstants.IMAGE, CalendarDateConstants.START_FUTURE, CalendarDateConstants.END_FUTURE);
    }

    public static final Activity titleChange() {
        return new Activity(ActivityConstants.NUMBER, ActivityConstants.ALTERNATIVE_TITLE,
            ActivityConstants.DESCRIPTION, ActivityConstants.IMAGE, CalendarDateConstants.START,
            CalendarDateConstants.END);
    }

    public static final Activity valid() {
        return new Activity(ActivityConstants.NUMBER, ActivityConstants.TITLE, ActivityConstants.DESCRIPTION,
            ActivityConstants.IMAGE, CalendarDateConstants.START, CalendarDateConstants.END);
    }

    private Activities() {
        super();
    }

}
