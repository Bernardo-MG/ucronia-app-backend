
package com.bernardomg.association.fee.model.request;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class ValidatedFeeQuery implements FeeQuery {

    @DateTimeFormat(pattern = "yyyy-MM")
    private LocalDateTime date;

    @DateTimeFormat(pattern = "yyyy-MM")
    private LocalDateTime endDate;

    @DateTimeFormat(pattern = "yyyy-MM")
    private LocalDateTime startDate;

}
