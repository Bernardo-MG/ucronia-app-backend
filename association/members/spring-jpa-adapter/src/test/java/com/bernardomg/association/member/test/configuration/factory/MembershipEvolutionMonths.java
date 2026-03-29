
package com.bernardomg.association.member.test.configuration.factory;

import java.time.YearMonth;

import com.bernardomg.association.member.domain.model.MembershipEvolutionMonth;

public final class MembershipEvolutionMonths {

    public static final MembershipEvolutionMonth currentMonth() {
        return new MembershipEvolutionMonth(MembershipEvolutionMonthConstants.CURRENT_MONTH, 1L);
    }

    public static final MembershipEvolutionMonth currentMonth(final Long total) {
        return new MembershipEvolutionMonth(MembershipEvolutionMonthConstants.CURRENT_MONTH, total);
    }

    public static final MembershipEvolutionMonth forMonth(final YearMonth month) {
        return new MembershipEvolutionMonth(month, 1L);
    }

    public static final MembershipEvolutionMonth forMonthAndTotal(final YearMonth month, final Long total) {
        return new MembershipEvolutionMonth(month, total);
    }

    public static final MembershipEvolutionMonth nextMonth() {
        return new MembershipEvolutionMonth(MembershipEvolutionMonthConstants.NEXT_MONTH, 1L);
    }

    public static final MembershipEvolutionMonth previousMonth() {
        return new MembershipEvolutionMonth(MembershipEvolutionMonthConstants.PREVIOUS_MONTH, 1L);
    }

    public static final MembershipEvolutionMonth twoMonthsBack() {
        return new MembershipEvolutionMonth(MembershipEvolutionMonthConstants.TWO_MONTHS_BACK, 1L);
    }

    private MembershipEvolutionMonths() {
        super();
    }

}
