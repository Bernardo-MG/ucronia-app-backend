
package com.bernardomg.association.fee.model.request;

import java.util.Calendar;

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
    private Calendar date;

    @DateTimeFormat(pattern = "yyyy-MM")
    private Calendar endDate;

    @DateTimeFormat(pattern = "yyyy-MM")
    private Calendar startDate;

}
