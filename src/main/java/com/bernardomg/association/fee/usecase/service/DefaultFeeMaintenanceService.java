
package com.bernardomg.association.fee.usecase.service;

import java.time.YearMonth;
import java.util.Collection;
import java.util.Objects;

import org.springframework.transaction.annotation.Transactional;

import com.bernardomg.association.fee.domain.model.Fee;
import com.bernardomg.association.fee.domain.model.FeeMember;
import com.bernardomg.association.fee.domain.repository.ActiveMemberRepository;
import com.bernardomg.association.fee.domain.repository.FeeRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Transactional
public final class DefaultFeeMaintenanceService implements FeeMaintenanceService {

    private final ActiveMemberRepository activeMemberRepository;

    private final FeeRepository          feeRepository;

    public DefaultFeeMaintenanceService(final FeeRepository feeRepo, final ActiveMemberRepository activeMemberRepo) {
        super();

        feeRepository = Objects.requireNonNull(feeRepo);
        activeMemberRepository = Objects.requireNonNull(activeMemberRepo);
    }

    @Override
    public final void registerMonthFees() {
        final Collection<Fee> feesToExtend;
        final Collection<Fee> feesToCreate;

        // Find fees to extend into the current month
        feesToExtend = feeRepository.findAllForPreviousMonth();

        feesToCreate = feesToExtend.stream()
            // Prepare for the current month
            .map(this::toCurrentMonth)
            // Make sure the user is active
            .filter(this::isActive)
            // Make sure it doesn't exist
            .filter(this::notExists)
            .toList();

        log.debug("Registering {} fees for this month", feesToCreate.size());
        feeRepository.save(feesToCreate);
    }

    private final boolean isActive(final Fee fee) {
        // TODO: aren't all members with fees in the previous month active?
        return activeMemberRepository.isActivePreviousMonth(fee.getMember()
            .getNumber());
    }

    private final boolean notExists(final Fee fee) {
        return !feeRepository.exists(fee.getMember()
            .getNumber(), fee.getDate());
    }

    private final Fee toCurrentMonth(final Fee fee) {
        final FeeMember member;

        member = FeeMember.builder()
            .withNumber(fee.getMember()
                .getNumber())
            .build();
        return Fee.builder()
            .withMember(member)
            .withDate(YearMonth.now())
            .build();
    }

}
