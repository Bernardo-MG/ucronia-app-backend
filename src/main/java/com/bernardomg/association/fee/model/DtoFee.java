
package com.bernardomg.association.fee.model;

import java.util.Calendar;

import lombok.Data;

@Data
public final class DtoFee implements Fee {

    private Long     id;

    private String   member;

    private Long     memberId;

    private Boolean  paid;

    private Calendar payDate;

}
