
package com.bernardomg.association.member.model;

import lombok.Data;

@Data
public final class DtoPaidMonth implements PaidMonth {

    private Long    id;

    private Long    member;

    private Integer month;

    private Integer year;

}
