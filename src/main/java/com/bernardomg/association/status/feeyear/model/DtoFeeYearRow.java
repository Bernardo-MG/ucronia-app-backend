
package com.bernardomg.association.status.feeyear.model;

import java.util.Calendar;

import lombok.Data;

@Data
public final class DtoFeeYearRow implements FeeYearRow {

    private Boolean  active;

    private Calendar date;

    private Long     id;

    private Long     memberId;

    private String   name;

    private Boolean  paid;

    private String   surname;

}
