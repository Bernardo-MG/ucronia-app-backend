
package com.bernardomg.association.fee.domain.model;

import java.time.YearMonth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(setterPrefix = "with")
@NoArgsConstructor
@AllArgsConstructor
public final class FeeQuery {

    private YearMonth date;

    private YearMonth endDate;

    private YearMonth startDate;

}
