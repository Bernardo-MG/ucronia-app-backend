
package com.bernardomg.association.activity.test.configuration.factory;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneOffset;

import com.bernardomg.association.activity.domain.model.Activity;

public final class Activities {

    public static final Activity forNumberAndMonth(final long number, final Month month) {
        // TODO: constant for the year
        final Instant date;

        date = LocalDateTime.of(2020, month, (int) number, 14, 0)
            .toInstant(ZoneOffset.UTC);
        return new Activity(number, date, ActivityConstants.TITLE + " " + number,
            ActivityConstants.DESCRIPTION + " " + number, ActivityConstants.IMAGE + "_" + number);
    }

    public static final Activity future() {
        return new Activity(ActivityConstants.NUMBER, ActivityConstants.DATE_FUTURE, ActivityConstants.TITLE,
            ActivityConstants.DESCRIPTION, ActivityConstants.IMAGE);
    }

    public static final Activity titleChange() {
        return new Activity(ActivityConstants.NUMBER, ActivityConstants.DATE, ActivityConstants.ALTERNATIVE_TITLE,
            ActivityConstants.DESCRIPTION, ActivityConstants.IMAGE);
    }

    public static final Activity valid() {
        return new Activity(ActivityConstants.NUMBER, ActivityConstants.DATE, ActivityConstants.TITLE,
            ActivityConstants.DESCRIPTION, ActivityConstants.IMAGE);
    }

    private Activities() {
        super();
    }

}
