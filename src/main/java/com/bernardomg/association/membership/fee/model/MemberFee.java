
package com.bernardomg.association.membership.fee.model;

import java.time.YearMonth;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public final class MemberFee {

    @JsonFormat(pattern = "yyyy-MM")
    private final YearMonth date;

    private final long      id;

    private final long      memberId;

    private final String    memberName;

    private final boolean   paid;

}
