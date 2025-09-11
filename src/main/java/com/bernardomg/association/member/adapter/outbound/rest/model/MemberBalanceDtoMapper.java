
package com.bernardomg.association.member.adapter.outbound.rest.model;

import java.util.Collection;

import com.bernardomg.association.member.domain.model.MonthlyMemberBalance;
import com.bernardomg.ucronia.openapi.model.MonthlyMemberBalanceDto;
import com.bernardomg.ucronia.openapi.model.MonthlyMemberBalancesResponseDto;

public final class MemberBalanceDtoMapper {

    public static final MonthlyMemberBalancesResponseDto
            toResponseDto(final Collection<MonthlyMemberBalance> balances) {
        return new MonthlyMemberBalancesResponseDto().content(balances.stream()
            .map(MemberBalanceDtoMapper::toDto)
            .toList());
    }

    private static final MonthlyMemberBalanceDto toDto(final MonthlyMemberBalance balance) {
        return new MonthlyMemberBalanceDto().month(balance.month())
            .total(balance.total());
    }

    private MemberBalanceDtoMapper() {
        super();
    }

}
