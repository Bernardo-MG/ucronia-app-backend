
package com.bernardomg.association.fee.test.configuration.factory;

import java.time.Instant;
import java.time.YearMonth;
import java.time.ZoneOffset;
import java.util.Optional;

import com.bernardomg.association.fee.domain.filter.FeeFilter;

public final class FeesQuery {

    public static final FeeFilter empty() {
        return new FeeFilter(Optional.empty(), Optional.empty(), Optional.empty());
    }

    public static final FeeFilter from(final YearMonth month) {
        final Optional<Instant> date;

        date = Optional.of(month.atDay(1)
            .atStartOfDay(ZoneOffset.UTC)
            .toInstant());
        return new FeeFilter(Optional.empty(), date, Optional.empty());
    }

    public static final FeeFilter inMonth(final YearMonth month) {
        final Optional<Instant> date;

        date = Optional.of(month.atDay(1)
            .atStartOfDay(ZoneOffset.UTC)
            .toInstant());
        return new FeeFilter(date, Optional.empty(), Optional.empty());
    }

    public static final FeeFilter inRange(final YearMonth start, final YearMonth end) {
        final Optional<Instant> startDate;
        final Optional<Instant> endDate;

        startDate = Optional.of(start.atDay(1)
            .atStartOfDay(ZoneOffset.UTC)
            .toInstant());
        endDate = Optional.of(end.atDay(1)
            .atStartOfDay(ZoneOffset.UTC)
            .toInstant());
        return new FeeFilter(Optional.empty(), startDate, endDate);
    }

    public static final FeeFilter to(final YearMonth month) {
        final Optional<Instant> date;

        date = Optional.of(month.atDay(1)
            .atStartOfDay(ZoneOffset.UTC)
            .toInstant());
        return new FeeFilter(Optional.empty(), Optional.empty(), date);
    }

}
