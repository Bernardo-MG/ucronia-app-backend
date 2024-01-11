
package com.bernardomg.association.membership.fee.model;

import java.time.YearMonth;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public final class Fee {

    @JsonFormat(pattern = "yyyy-MM")
    private final YearMonth      date;

    private final FeeMember      member;

    private final boolean        paid;

    private final FeeTransaction transaction;

}
