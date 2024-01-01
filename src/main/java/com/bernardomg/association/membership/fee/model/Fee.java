
package com.bernardomg.association.membership.fee.model;

import java.time.LocalDate;
import java.time.YearMonth;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public final class Fee {

    @JsonFormat(pattern = "yyyy-MM")
    private final YearMonth date;

    private final String    memberName;

    private final long      memberNumber;

    private final boolean   paid;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private final LocalDate paymentDate;

    private final Long      transactionIndex;

}