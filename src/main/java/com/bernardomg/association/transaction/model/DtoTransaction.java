
package com.bernardomg.association.transaction.model;

import java.util.Calendar;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public final class DtoTransaction implements Transaction {

    private Float    amount;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Calendar date;

    private String   description;

    private Long     id;

}
