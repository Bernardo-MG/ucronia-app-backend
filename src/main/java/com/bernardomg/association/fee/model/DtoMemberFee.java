
package com.bernardomg.association.fee.model;

import java.time.YearMonth;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public final class DtoMemberFee implements MemberFee {

    @JsonFormat(pattern = "yyyy-MM")
    private final YearMonth date;

    private final Long      id;

    private final Long      memberId;

    private final String    memberName;

    private final Boolean   paid;

}
