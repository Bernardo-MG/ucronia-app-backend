
package com.bernardomg.association.transaction.domain.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public final class Transaction {

    private float     amount;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    private String    description;

    private long      index;

}
