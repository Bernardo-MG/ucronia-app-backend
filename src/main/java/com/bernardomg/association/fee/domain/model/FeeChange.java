
package com.bernardomg.association.fee.domain.model;

import java.time.YearMonth;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(setterPrefix = "with")
@NoArgsConstructor
@AllArgsConstructor
public final class FeeChange {

    @DateTimeFormat(pattern = "yyyy-MM")
    private YearMonth date;

}
