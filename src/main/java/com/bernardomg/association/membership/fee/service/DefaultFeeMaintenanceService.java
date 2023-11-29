
package com.bernardomg.association.membership.fee.service;

import java.time.YearMonth;
import java.util.Collection;
import java.util.Objects;

import com.bernardomg.association.membership.fee.persistence.model.PersistentFee;
import com.bernardomg.association.membership.fee.persistence.repository.FeeRepository;
import com.bernardomg.association.membership.member.persistence.repository.MemberRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class DefaultFeeMaintenanceService implements FeeMaintenanceService {

    private final FeeRepository    feeRepository;

    private final MemberRepository memberRepository;

    public DefaultFeeMaintenanceService(final FeeRepository feeRepo, final MemberRepository memberRepo) {
        super();

        feeRepository = Objects.requireNonNull(feeRepo);
        memberRepository = Objects.requireNonNull(memberRepo);
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
            // Make sure the user is active
            .filter(this::notInactive)
            // Make sure it doesn't exist
            .filter(this::notExists)
            .toList();

        log.debug("Registering {} fees for this month", feesToCreate.size());
        feeRepository.saveAll(feesToCreate);
    }

    private final boolean notExists(final PersistentFee fee) {
        return !feeRepository.existsByMemberIdAndDate(fee.getMemberId(), fee.getDate());
    }

    private final boolean notInactive(final PersistentFee fee) {
        return memberRepository.existsActive(fee.getMemberId());
    }

    private final PersistentFee toCurrentMonth(final PersistentFee fee) {
        return PersistentFee.builder()
            .memberId(fee.getMemberId())
            .date(YearMonth.now())
            .paid(false)
            .build();
    }

}
