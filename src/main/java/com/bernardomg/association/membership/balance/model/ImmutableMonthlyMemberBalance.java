
package com.bernardomg.association.membership.balance.model;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public final class ImmutableMonthlyMemberBalance implements MonthlyMemberBalance {

    private Long      difference;

    private LocalDate month;

    private Long      total;

}
