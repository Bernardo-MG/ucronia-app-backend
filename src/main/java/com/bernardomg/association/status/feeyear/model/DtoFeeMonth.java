
package com.bernardomg.association.status.feeyear.model;

import lombok.Data;

@Data
public final class DtoFeeMonth implements FeeMonth {

    private Integer month;

    private Boolean paid;

}
