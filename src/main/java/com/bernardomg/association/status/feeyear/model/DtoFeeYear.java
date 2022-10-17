
package com.bernardomg.association.status.feeyear.model;

import java.util.Collection;

import lombok.Data;

@Data
public final class DtoFeeYear implements FeeYear {

    private Boolean              active;

    private Long                 memberId;

    private Collection<FeeMonth> months;

    private String               name;

    private String               surname;

    private Integer              year;

}
