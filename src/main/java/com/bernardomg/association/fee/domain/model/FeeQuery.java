
package com.bernardomg.association.fee.domain.model;

import java.time.YearMonth;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class FeeQuery {

    @DateTimeFormat(pattern = "yyyy-MM")
    private YearMonth date;

    @DateTimeFormat(pattern = "yyyy-MM")
    private YearMonth endDate;

    @DateTimeFormat(pattern = "yyyy-MM")
    private YearMonth startDate;

}
