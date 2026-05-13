
package com.bernardomg.association.member.test.configuration.factory;

import java.time.ZoneOffset;

import com.bernardomg.association.member.domain.filter.MembershipEvolutionFilter;

public final class MembershipEvolutionFilters {

    public static final MembershipEvolutionFilter aroundCurrent() {
        return new MembershipEvolutionFilter(MembershipEvolutionMonthConstants.PREVIOUS_MONTH.atDay(1)
            .atStartOfDay(ZoneOffset.UTC)
            .toInstant(),
            MembershipEvolutionMonthConstants.NEXT_MONTH.atDay(1)
                .atStartOfDay(ZoneOffset.UTC)
                .toInstant());
    }

    public static final MembershipEvolutionFilter aroundPrevious() {
        return new MembershipEvolutionFilter(MembershipEvolutionMonthConstants.PREVIOUS_MONTH.atDay(1)
            .atStartOfDay(ZoneOffset.UTC)
            .toInstant(),
            MembershipEvolutionMonthConstants.PREVIOUS_MONTH.atDay(1)
                .atStartOfDay(ZoneOffset.UTC)
                .toInstant());
    }

    public static final MembershipEvolutionFilter empty() {
        return new MembershipEvolutionFilter(null, null);
    }

    public static final MembershipEvolutionFilter endBeforeStart() {
        return new MembershipEvolutionFilter(MembershipEvolutionMonthConstants.CURRENT_MONTH.atDay(1)
            .atStartOfDay(ZoneOffset.UTC)
            .toInstant(),
            MembershipEvolutionMonthConstants.PREVIOUS_MONTH.atDay(1)
                .atStartOfDay(ZoneOffset.UTC)
                .toInstant());
    }

    public static final MembershipEvolutionFilter previousAndThis() {
        return new MembershipEvolutionFilter(MembershipEvolutionMonthConstants.PREVIOUS_MONTH.atDay(1)
            .atStartOfDay(ZoneOffset.UTC)
            .toInstant(),
            MembershipEvolutionMonthConstants.CURRENT_MONTH.atDay(1)
                .atStartOfDay(ZoneOffset.UTC)
                .toInstant());
    }

    private MembershipEvolutionFilters() {
        super();
    }

}
