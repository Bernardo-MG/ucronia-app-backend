
package com.bernardomg.association.transaction.model;

import java.util.Calendar;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public final class DtoTransaction implements Transaction {

    private final Float    amount;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private final Calendar date;

    private final String   description;

    private final Long     id;

}
