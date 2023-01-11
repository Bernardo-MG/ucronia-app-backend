
package com.bernardomg.association.transaction.model;

import java.util.Calendar;

import lombok.Data;

@Data
public final class DtoTransaction implements Transaction {

    private Float    amount;

    private Calendar date;

    private String   description;

    private Long     id;

}
