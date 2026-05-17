
package com.bernardomg.association.fee.test.configuration.factory;

import java.time.Instant;
import java.util.Optional;

import com.bernardomg.association.fee.domain.filter.FeeFilter;

public final class FeesQuery {

    public static final FeeFilter empty() {
        return new FeeFilter(Optional.empty(), Optional.empty(), Optional.empty());
    }

    public static final FeeFilter from(final Instant month) {
        final Optional<Instant> date;

        date = Optional.of(month);
        return new FeeFilter(Optional.empty(), date, Optional.empty());
    }

    public static final FeeFilter inMonth(final Instant month) {
        final Optional<Instant> date;

        date = Optional.of(month);
        return new FeeFilter(date, Optional.empty(), Optional.empty());
    }

    public static final FeeFilter inRange(final Instant start, final Instant end) {
        final Optional<Instant> startDate;
        final Optional<Instant> endDate;

        startDate = Optional.of(start);
        endDate = Optional.of(end);
        return new FeeFilter(Optional.empty(), startDate, endDate);
    }

    public static final FeeFilter to(final Instant month) {
        final Optional<Instant> date;

        date = Optional.of(month);
        return new FeeFilter(Optional.empty(), Optional.empty(), date);
    }

}
