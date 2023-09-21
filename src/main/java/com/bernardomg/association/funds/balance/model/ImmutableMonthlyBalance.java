
package com.bernardomg.association.funds.balance.model;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public final class ImmutableMonthlyBalance implements MonthlyBalance {

    private Float     cumulative;

    private LocalDate month;

    private Float     monthlyTotal;

}
