
package com.bernardomg.association.balance.model;

import lombok.Data;

@Data
public final class DtoBalance implements Balance {

    private Long    amount;

    private Long    id;

    private Integer month;

    private Integer year;

}
