
package com.bernardomg.association.fee.model;

import java.util.Calendar;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public final class DtoMemberFee implements MemberFee {

    @JsonFormat(pattern = "yyyy-MM")
    private final Calendar date;

    private final Long     id;

    private final Long     memberId;

    private final String   name;

    private final Boolean  paid;

}
