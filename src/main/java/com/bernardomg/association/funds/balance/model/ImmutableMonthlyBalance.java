
package com.bernardomg.association.funds.balance.model;

import java.time.YearMonth;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public final class ImmutableMonthlyBalance implements MonthlyBalance {

    private Float     difference;

    private YearMonth month;

    private Float     total;

}
