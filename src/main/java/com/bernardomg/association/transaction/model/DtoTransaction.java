
package com.bernardomg.association.transaction.model;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public final class DtoTransaction implements Transaction {

    @NotNull
    private Integer day;

    @NotEmpty
    @NotNull
    private String  description;

    private Long    id;

    @NotNull
    private Integer month;

    @NotNull
    private Long    quantity;

    @NotNull
    private Integer year;

}
