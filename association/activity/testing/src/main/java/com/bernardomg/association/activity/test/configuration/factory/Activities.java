
package com.bernardomg.association.activity.test.configuration.factory;

import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneOffset;

import com.bernardomg.association.activity.domain.model.Activity;

public final class Activities {

    public static final Activity forNumberAndMonth(final long number, final Month month) {
        // TODO: constant for the year
        return new Activity(number, LocalDate.of(2020, month, (int) number)
            .atStartOfDay(ZoneOffset.UTC)
            .toInstant(), ActivityConstants.TITLE + " " + number, ActivityConstants.DESCRIPTION + " " + number);
    }

    public static final Activity future() {
        return new Activity(ActivityConstants.NUMBER, ActivityConstants.DATE_FUTURE, ActivityConstants.TITLE,
            ActivityConstants.DESCRIPTION);
    }

    public static final Activity valid() {
        return new Activity(ActivityConstants.NUMBER, ActivityConstants.DATE, ActivityConstants.TITLE,
            ActivityConstants.DESCRIPTION);
    }

    private Activities() {
        super();
    }

}
