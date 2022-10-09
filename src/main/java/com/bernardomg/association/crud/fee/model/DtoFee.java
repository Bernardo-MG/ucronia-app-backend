
package com.bernardomg.association.crud.fee.model;

import java.util.Calendar;

import lombok.Data;

@Data
public final class DtoFee implements Fee {

    private Calendar date;

    private Long     id;

    private String   member;

    private Long     memberId;

    private Boolean  paid;

}
