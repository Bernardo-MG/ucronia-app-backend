
package com.bernardomg.association.fee.model;

import java.util.Calendar;

import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

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
