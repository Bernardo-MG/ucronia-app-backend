
package com.bernardomg.association.fee.service;

import java.time.YearMonth;
import java.util.Collection;
import java.util.Objects;

import com.bernardomg.association.fee.persistence.model.PersistentFee;
import com.bernardomg.association.fee.persistence.repository.FeeRepository;

public final class DefaultFeeMaintenanceService implements FeeMaintenanceService {

    private final FeeRepository feeRepository;

    public DefaultFeeMaintenanceService(final FeeRepository feeRepo) {
        super();

        feeRepository = Objects.requireNonNull(feeRepo);
    }

    @Override
    public final void registerMonthFees() {
        final YearMonth                 previousMonth;
        final Collection<PersistentFee> feesToExtend;
        final Collection<PersistentFee> feesToCreate;

        previousMonth = YearMonth.now()
            .minusMonths(1);

        // Find fees to extend into the current month
        feesToExtend = feeRepository.findAllByDate(previousMonth);

        feesToCreate = feesToExtend.stream()
            // Prepare for the current month
            .map(this::toCurrentMonth)
            // Make sure it doesn't exist
            .filter(this::notExists)
            .toList();
        feeRepository.saveAll(feesToCreate);
    }

    private final boolean notExists(final PersistentFee fee) {
        return !feeRepository.existsByMemberIdAndDate(fee.getMemberId(), fee.getDate());
    }

    private final PersistentFee toCurrentMonth(final PersistentFee fee) {
        return PersistentFee.builder()
            .memberId(fee.getMemberId())
            .date(YearMonth.now())
            .paid(false)
            .build();
    }

}
