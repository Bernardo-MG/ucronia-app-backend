
package com.bernardomg.association.membership.balance.model;

import java.time.YearMonth;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public final class ImmutableMonthlyMemberBalance implements MonthlyMemberBalance {

    private Long      difference;

    private YearMonth month;

    private Long      total;

}
