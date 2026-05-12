
package com.bernardomg.association.fee.test.configuration.factory;

import java.time.Instant;
import java.time.YearMonth;
import java.time.ZoneOffset;
import java.util.Optional;

import com.bernardomg.association.fee.domain.model.FeeQuery;

public final class FeesQuery {

    public static final FeeQuery empty() {
        return new FeeQuery(Optional.empty(), Optional.empty(), Optional.empty());
    }

    public static final FeeQuery from(final YearMonth month) {
        final Optional<Instant> date;

        date = Optional.of(month.atDay(1)
            .atStartOfDay(ZoneOffset.UTC)
            .toInstant());
        return new FeeQuery(Optional.empty(), date, Optional.empty());
    }

    public static final FeeQuery inMonth(final YearMonth month) {
        final Optional<Instant> date;

        date = Optional.of(month.atDay(1)
            .atStartOfDay(ZoneOffset.UTC)
            .toInstant());
        return new FeeQuery(date, Optional.empty(), Optional.empty());
    }

    public static final FeeQuery inRange(final YearMonth start, final YearMonth end) {
        final Optional<Instant> startDate;
        final Optional<Instant> endDate;

        startDate = Optional.of(start.atDay(1)
            .atStartOfDay(ZoneOffset.UTC)
            .toInstant());
        endDate = Optional.of(end.atDay(1)
            .atStartOfDay(ZoneOffset.UTC)
            .toInstant());
        return new FeeQuery(Optional.empty(), startDate, endDate);
    }

    public static final FeeQuery to(final YearMonth month) {
        final Optional<Instant> date;

        date = Optional.of(month.atDay(1)
            .atStartOfDay(ZoneOffset.UTC)
            .toInstant());
        return new FeeQuery(Optional.empty(), Optional.empty(), date);
    }

}
