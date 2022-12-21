
package com.bernardomg.association.domain.fee.model;

import java.util.Calendar;

import lombok.Data;

@Data
public final class DtoMemberFee implements MemberFee {

    private Calendar date;

    private Long     id;

    private Long     memberId;

    private String   name;

    private Boolean  paid;

    private String   surname;

}
