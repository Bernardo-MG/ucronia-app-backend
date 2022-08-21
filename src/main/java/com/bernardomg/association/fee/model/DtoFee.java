
package com.bernardomg.association.fee.model;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public final class DtoFee implements Fee {

    private Long    id;

    @NotNull
    private Long    member;

    @NotNull
    private Integer month;

    @NotNull
    private Boolean paid;

    @NotNull
    private Integer year;

}
