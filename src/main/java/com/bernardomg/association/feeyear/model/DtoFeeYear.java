
package com.bernardomg.association.feeyear.model;

import java.util.Collection;

import lombok.Data;

@Data
public final class DtoFeeYear implements FeeYear {

    private String               member;

    private Long                 memberId;

    private Collection<FeeMonth> months;

    private Integer              year;

}
