
package com.bernardomg.association.balance.model;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public final class DtoMonthlyBalance implements MonthlyBalance {

    private Float         cumulative;

    private LocalDateTime date;

    private Float         total;

}
