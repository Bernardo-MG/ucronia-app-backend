
package com.bernardomg.association.balance.model;

import java.util.Calendar;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public final class DtoMonthlyBalance implements MonthlyBalance {

    private Float    cumulative;

    private Calendar date;

    private Float    total;

}