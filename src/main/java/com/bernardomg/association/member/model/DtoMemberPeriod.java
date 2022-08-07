
package com.bernardomg.association.member.model;

import lombok.Data;

@Data
public final class DtoMemberPeriod implements MemberPeriod {

    private Integer endMonth   = -1;

    private Integer endYear    = -1;

    private Long    id         = -1L;

    private Integer startMonth = -1;

    private Integer startYear  = -1;

}
