
package com.bernardomg.association.memberperiod.model;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public final class DtoMemberPeriod implements MemberPeriod {

    @NotNull
    private Integer endMonth;

    @NotNull
    private Integer endYear;

    private Long    id;

    @NotNull
    private Long    member;

    @NotNull
    private Integer startMonth;

    @NotNull
    private Integer startYear;

}
