
package com.bernardomg.association.transaction.model;

import java.util.Calendar;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public final class ImmutableTransaction implements Transaction {

    private final Float    amount;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private final Calendar date;

    private final String   description;

    private final Long     id;

}
