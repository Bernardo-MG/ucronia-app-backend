
package com.bernardomg.association.balance.model;

import java.util.Calendar;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public final class ImmutableMonthlyBalance implements MonthlyBalance {

    private Float    cumulative;

    private Calendar date;

    private Float    total;

}
