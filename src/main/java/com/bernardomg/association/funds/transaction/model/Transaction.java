
package com.bernardomg.association.funds.transaction.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public final class Transaction {

    private final Float     amount;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private final LocalDate date;

    private final String    description;

    private final Long      index;

}
