
package com.bernardomg.association.fee.adapter.inbound.jpa.repository;

import java.time.YearMonth;

import com.bernardomg.association.fee.domain.repository.ActiveMemberRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class JpaActiveMemberRepository implements ActiveMemberRepository {

    private final ActiveMemberSpringRepository activeMemberRepository;

    public JpaActiveMemberRepository(final ActiveMemberSpringRepository activeMemberRepo) {
        super();

        activeMemberRepository = activeMemberRepo;
    }

    @Override
    public final boolean isActive(final long number) {
        final YearMonth validStart;
        final YearMonth validEnd;
        final boolean   active;

        log.debug("Checking if member {} is active", number);

        validStart = YearMonth.now();
        validEnd = YearMonth.now();
        active = activeMemberRepository.isActive(number, validStart, validEnd);

        log.debug("Member {} is active: {}", number, active);

        return active;
    }

    @Override
    public final boolean isActivePreviousMonth(final long number) {
        final YearMonth validStart;
        final YearMonth validEnd;
        final boolean   active;

        log.debug("Checking if member {} is active in the previous month", number);

        validStart = YearMonth.now()
            .minusMonths(1);
        validEnd = YearMonth.now()
            .minusMonths(1);
        active = activeMemberRepository.isActive(number, validStart, validEnd);

        log.debug("Member {} is active in the previous month: {}", number, active);

        return active;
    }

}
