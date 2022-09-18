
package com.bernardomg.association.status.feeyear.model;

import java.util.Collection;

import lombok.Data;

@Data
public final class DtoFeeYear implements FeeYear {

    private Boolean              active;

    private String               member;

    private Long                 memberId;

    private Collection<FeeMonth> months;

    private Integer              year;

}
