
package com.bernardomg.association.fee.usecase.service;

import java.time.YearMonth;
import java.util.Collection;
import java.util.Objects;
import java.util.function.Predicate;

import com.bernardomg.association.fee.domain.model.Fee;
import com.bernardomg.association.fee.domain.model.FeePaymentReport;
import com.bernardomg.association.fee.domain.repository.FeeRepository;

public final class DefaultFeeReportService implements FeeReportService {

    private final FeeRepository feeRepository;

    public DefaultFeeReportService(final FeeRepository feeRepo) {
        super();

        feeRepository = Objects.requireNonNull(feeRepo);
    }

    @Override
    public final FeePaymentReport getPaymentReport() {
        final Collection<Fee> fees;
        final long            paid;
        final long            unpaid;

        fees = feeRepository.findAllInMonth(YearMonth.now());
        paid = fees.stream()
            .filter(Fee::isPaid)
            .count();
        unpaid = fees.stream()
            .filter(Predicate.not(Fee::isPaid))
            .count();

        return FeePaymentReport.builder()
            .withPaid(paid)
            .withUnpaid(unpaid)
            .build();
    }

}
