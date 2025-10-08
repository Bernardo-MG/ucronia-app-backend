
package com.bernardomg.association.fee.adapter.outbound.rest.model;

import com.bernardomg.association.fee.domain.model.FeeBalance;
import com.bernardomg.ucronia.openapi.model.FeeBalanceDto;
import com.bernardomg.ucronia.openapi.model.FeeBalanceResponseDto;

public final class FeeBalanceDtoMapper {

    public static final FeeBalanceResponseDto toResponseDto(final FeeBalance balance) {
        final FeeBalanceDto reportDto;

        reportDto = new FeeBalanceDto().paid(balance.paid())
            .unpaid(balance.unpaid());
        return new FeeBalanceResponseDto().content(reportDto);
    }

    private FeeBalanceDtoMapper() {
        super();
    }

}
