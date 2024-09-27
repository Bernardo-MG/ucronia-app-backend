
package com.bernardomg.association.transaction.domain.model;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(setterPrefix = "with")
public final class Transaction {

    private float     amount;

    private LocalDate date;

    private String    description;

    private long      index;

}
