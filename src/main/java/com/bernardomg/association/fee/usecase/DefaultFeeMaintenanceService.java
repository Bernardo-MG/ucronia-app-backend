
package com.bernardomg.association.fee.usecase;

import java.time.YearMonth;
import java.util.Collection;
import java.util.Objects;

import com.bernardomg.association.fee.infra.jpa.model.FeeEntity;
import com.bernardomg.association.fee.infra.jpa.repository.ActiveMemberSpringRepository;
import com.bernardomg.association.fee.infra.jpa.repository.FeeSpringRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class DefaultFeeMaintenanceService implements FeeMaintenanceService {

    private final ActiveMemberSpringRepository activeMemberRepository;

    private final FeeSpringRepository          feeRepository;

    public DefaultFeeMaintenanceService(final FeeSpringRepository feeRepo,
            final ActiveMemberSpringRepository activeMemberRepo) {
        super();

        feeRepository = Objects.requireNonNull(feeRepo);
        activeMemberRepository = Objects.requireNonNull(activeMemberRepo);
    }

    @Override
    public final void registerMonthFees() {
        final YearMonth             previousMonth;
        final Collection<FeeEntity> feesToExtend;
        final Collection<FeeEntity> feesToCreate;

        previousMonth = YearMonth.now()
            .minusMonths(1);

        // Find fees to extend into the current month
        feesToExtend = feeRepository.findAllByDate(previousMonth);

        feesToCreate = feesToExtend.stream()
            // Prepare for the current month
            .map(this::toCurrentMonth)
            // Make sure the user is active
            .filter(this::notInactive)
            // Make sure it doesn't exist
            .filter(this::notExists)
            .toList();

        log.debug("Registering {} fees for this month", feesToCreate.size());
        feeRepository.saveAll(feesToCreate);
    }

    private final boolean notExists(final FeeEntity fee) {
        return !feeRepository.existsByMemberIdAndDate(fee.getMemberId(), fee.getDate());
    }

    private final boolean notInactive(final FeeEntity fee) {
        final YearMonth validStart;
        final YearMonth validEnd;

        // TODO: Should be done in the repository
        validStart = YearMonth.now()
            .minusMonths(1);
        validEnd = YearMonth.now()
            .minusMonths(1);
        return activeMemberRepository.isActive(fee.getMemberId(), validStart, validEnd);
    }

    private final FeeEntity toCurrentMonth(final FeeEntity fee) {
        return FeeEntity.builder()
            .memberId(fee.getMemberId())
            .date(YearMonth.now())
            .build();
    }

}
