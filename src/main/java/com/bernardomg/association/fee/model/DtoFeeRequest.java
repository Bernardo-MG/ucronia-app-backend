
package com.bernardomg.association.fee.model;

import java.util.Calendar;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public final class DtoFeeRequest implements FeeRequest {

    @NotNull
    private Calendar date;

    @NotNull
    private Calendar endDate;

    @NotNull
    private Calendar startDate;

}
