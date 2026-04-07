
package com.bernardomg.association.member.test.configuration.factory;

import java.time.ZoneOffset;

import com.bernardomg.association.member.domain.filter.MembershipEvolutionQuery;

public final class MembershipEvolutionQueries {

    public static final MembershipEvolutionQuery aroundCurrent() {
        return new MembershipEvolutionQuery(MembershipEvolutionMonthConstants.PREVIOUS_MONTH.atDay(1)
            .atStartOfDay(ZoneOffset.UTC)
            .toInstant(),
            MembershipEvolutionMonthConstants.NEXT_MONTH.atDay(1)
                .atStartOfDay(ZoneOffset.UTC)
                .toInstant());
    }

    public static final MembershipEvolutionQuery aroundPrevious() {
        return new MembershipEvolutionQuery(MembershipEvolutionMonthConstants.PREVIOUS_MONTH.atDay(1)
            .atStartOfDay(ZoneOffset.UTC)
            .toInstant(),
            MembershipEvolutionMonthConstants.PREVIOUS_MONTH.atDay(1)
                .atStartOfDay(ZoneOffset.UTC)
                .toInstant());
    }

    public static final MembershipEvolutionQuery empty() {
        return new MembershipEvolutionQuery(null, null);
    }

    public static final MembershipEvolutionQuery endBeforeStart() {
        return new MembershipEvolutionQuery(MembershipEvolutionMonthConstants.CURRENT_MONTH.atDay(1)
            .atStartOfDay(ZoneOffset.UTC)
            .toInstant(),
            MembershipEvolutionMonthConstants.PREVIOUS_MONTH.atDay(1)
                .atStartOfDay(ZoneOffset.UTC)
                .toInstant());
    }

    public static final MembershipEvolutionQuery previousAndThis() {
        return new MembershipEvolutionQuery(MembershipEvolutionMonthConstants.PREVIOUS_MONTH.atDay(1)
            .atStartOfDay(ZoneOffset.UTC)
            .toInstant(),
            MembershipEvolutionMonthConstants.CURRENT_MONTH.atDay(1)
                .atStartOfDay(ZoneOffset.UTC)
                .toInstant());
    }

    private MembershipEvolutionQueries() {
        super();
    }

}
