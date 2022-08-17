
package com.bernardomg.association.paidmonth.model;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public final class DtoPaidMonth implements PaidMonth {

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
