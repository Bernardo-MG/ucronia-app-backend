
package com.bernardomg.association.fee.model;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public final class DtoFeeForm implements FeeForm {

    private Long    id;

    @NotNull
    private Long    memberId;

    @NotNull
    private Integer month;

    @NotNull
    private Boolean paid;

    @NotNull
    private Integer year;

}
