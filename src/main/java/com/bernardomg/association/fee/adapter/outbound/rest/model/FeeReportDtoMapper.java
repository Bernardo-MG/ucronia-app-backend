
package com.bernardomg.association.fee.adapter.outbound.rest.model;

import com.bernardomg.association.fee.domain.model.FeePaymentReport;
import com.bernardomg.ucronia.openapi.model.FeePaymentReportDto;
import com.bernardomg.ucronia.openapi.model.FeePaymentReportResponseDto;

public final class FeeReportDtoMapper {

    public static final FeePaymentReportResponseDto toResponseDto(final FeePaymentReport report) {
        final FeePaymentReportDto reportDto;

        reportDto = new FeePaymentReportDto().paid(report.paid())
            .unpaid(report.unpaid());
        return new FeePaymentReportResponseDto().content(reportDto);
    }

    private FeeReportDtoMapper() {
        super();
    }

}
