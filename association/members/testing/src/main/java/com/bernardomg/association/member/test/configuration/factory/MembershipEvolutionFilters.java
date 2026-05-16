
package com.bernardomg.association.member.test.configuration.factory;

import java.time.Instant;
import java.time.ZoneOffset;
import java.util.Optional;

import com.bernardomg.association.member.domain.filter.MembershipEvolutionFilter;

public final class MembershipEvolutionFilters {

    public static final MembershipEvolutionFilter aroundCurrent() {
        final Instant from;
        final Instant to;

        from = MembershipEvolutionMonthConstants.PREVIOUS_MONTH.atDay(1)
            .atStartOfDay(ZoneOffset.UTC)
            .toInstant();
        to = MembershipEvolutionMonthConstants.NEXT_MONTH.atDay(1)
            .atStartOfDay(ZoneOffset.UTC)
            .toInstant();
        return new MembershipEvolutionFilter(Optional.of(from), Optional.of(to));
    }

    public static final MembershipEvolutionFilter aroundPrevious() {
        final Instant from;
        final Instant to;

        from = MembershipEvolutionMonthConstants.PREVIOUS_MONTH.atDay(1)
            .atStartOfDay(ZoneOffset.UTC)
            .toInstant();
        to = MembershipEvolutionMonthConstants.PREVIOUS_MONTH.atDay(1)
            .atStartOfDay(ZoneOffset.UTC)
            .toInstant();
        return new MembershipEvolutionFilter(Optional.of(from), Optional.of(to));
    }

    public static final MembershipEvolutionFilter empty() {
        return new MembershipEvolutionFilter(Optional.empty(), Optional.empty());
    }

    public static final MembershipEvolutionFilter endBeforeStart() {
        final Instant from;
        final Instant to;

        from = MembershipEvolutionMonthConstants.CURRENT_MONTH.atDay(1)
            .atStartOfDay(ZoneOffset.UTC)
            .toInstant();
        to = MembershipEvolutionMonthConstants.PREVIOUS_MONTH.atDay(1)
            .atStartOfDay(ZoneOffset.UTC)
            .toInstant();
        return new MembershipEvolutionFilter(Optional.of(from), Optional.of(to));
    }

    public static final MembershipEvolutionFilter previousAndThis() {
        final Instant from;
        final Instant to;

        from = MembershipEvolutionMonthConstants.PREVIOUS_MONTH.atDay(1)
            .atStartOfDay(ZoneOffset.UTC)
            .toInstant();
        to = MembershipEvolutionMonthConstants.CURRENT_MONTH.atDay(1)
            .atStartOfDay(ZoneOffset.UTC)
            .toInstant();
        return new MembershipEvolutionFilter(Optional.of(from), Optional.of(to));
    }

    private MembershipEvolutionFilters() {
        super();
    }

}
