
package com.bernardomg.association.fee.model;

import lombok.Data;

@Data
public final class DtoFee implements Fee {

    private Long    id;

    private String  member;

    private Long    memberId;

    private Integer month;

    private Boolean paid;

    private Integer year;

}
