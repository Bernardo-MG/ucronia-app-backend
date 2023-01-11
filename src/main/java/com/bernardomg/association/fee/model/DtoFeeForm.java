
package com.bernardomg.association.fee.model;

import java.util.Calendar;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public final class DtoFeeForm implements FeeForm {

    @NotNull
    private Calendar date;

    @NotNull
    private Long     memberId;

    @NotNull
    private Boolean  paid;

}
