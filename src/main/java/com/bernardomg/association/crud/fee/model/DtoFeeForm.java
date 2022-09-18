
package com.bernardomg.association.crud.fee.model;

import java.util.Calendar;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public final class DtoFeeForm implements FeeForm {

    private Long     id;

    @NotNull
    private Long     memberId;

    @NotNull
    private Boolean  paid;

    @NotNull
    private Calendar payDate;

}
