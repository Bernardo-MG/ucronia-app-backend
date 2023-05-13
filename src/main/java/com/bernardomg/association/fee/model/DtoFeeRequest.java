
package com.bernardomg.association.fee.model;

import java.util.Calendar;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public final class DtoFeeRequest implements FeeRequest {

    @DateTimeFormat(pattern = "yyyy-MM")
    @NotNull
    private Calendar date;

    @DateTimeFormat(pattern = "yyyy-MM")
    @NotNull
    private Calendar endDate;

    @DateTimeFormat(pattern = "yyyy-MM")
    @NotNull
    private Calendar startDate;

}
