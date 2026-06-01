
package com.bernardomg.association.member.test.configuration.factory;

import java.time.Instant;
import java.time.Month;
import java.time.YearMonth;
import java.time.ZoneOffset;

public final class MembershipEvolutionMonthConstants {

    public static final Instant CURRENT_MONTH   = YearMonth.now()
        .atDay(1)
        .atStartOfDay(ZoneOffset.UTC)
        .toInstant();

    public static final Instant NEXT_MONTH      = YearMonth.now()
        .plusMonths(1)
        .atDay(1)
        .atStartOfDay(ZoneOffset.UTC)
        .toInstant();

    public static final Instant PREVIOUS_MONTH  = YearMonth.now()
        .minusMonths(1)
        .atDay(1)
        .atStartOfDay(ZoneOffset.UTC)
        .toInstant();

    public static final Instant START_MONTH     = YearMonth.of(2020, Month.JANUARY)
        .atDay(1)
        .atStartOfDay(ZoneOffset.UTC)
        .toInstant();

    public static final Instant TWO_MONTHS_BACK = YearMonth.now()
        .minusMonths(2)
        .atDay(1)
        .atStartOfDay(ZoneOffset.UTC)
        .toInstant();

    private MembershipEvolutionMonthConstants() {
        super();
    }

}
