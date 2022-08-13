
package com.bernardomg.association.member.model;

import lombok.Data;

@Data
public final class DtoPaidMonth implements PaidMonth {

    private Long    id     = -1L;

    private Long    member = -1L;

    private Integer month  = -1;

    private Boolean paid   = false;

    private Integer year   = -1;

}
