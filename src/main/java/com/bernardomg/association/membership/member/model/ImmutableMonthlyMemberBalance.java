
package com.bernardomg.association.membership.member.model;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public final class ImmutableMonthlyMemberBalance implements MonthlyMemberBalance {

    private LocalDate month;

    private Float     total;

}
