
package com.bernardomg.association.balance.model;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public final class DtoMonthlyBalance implements MonthlyBalance {

    private Float     cumulative;

    private LocalDate date;

    private Float     total;

}
