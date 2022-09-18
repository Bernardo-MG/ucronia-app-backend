
package com.bernardomg.association.crud.transaction.model;

import java.util.Calendar;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public final class DtoTransaction implements Transaction {

    @NotEmpty
    @NotNull
    private String   description;

    private Long     id;

    @NotNull
    private Calendar payDate;

    @NotNull
    private Float    amount;

}
