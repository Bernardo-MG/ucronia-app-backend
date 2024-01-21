
package com.bernardomg.association.fee.infra.inbound.jpa.repository;

import java.time.YearMonth;

import com.bernardomg.association.fee.domain.repository.ActiveMemberRepository;

public final class JpaActiveMemberRepository implements ActiveMemberRepository {

    private final ActiveMemberSpringRepository activeMemberRepository;

    public JpaActiveMemberRepository(final ActiveMemberSpringRepository activeMemberRepo) {
        super();

        activeMemberRepository = activeMemberRepo;
    }

    @Override
    public boolean isActive(final long number) {
        final YearMonth validStart;
        final YearMonth validEnd;

        validStart = YearMonth.now();
        validEnd = YearMonth.now();
        return activeMemberRepository.isActive(number, validStart, validEnd);
    }

    @Override
    public boolean isActivePreviousMonth(final long number) {
        final YearMonth validStart;
        final YearMonth validEnd;

        validStart = YearMonth.now()
            .minusMonths(1);
        validEnd = YearMonth.now()
            .minusMonths(1);
        return activeMemberRepository.isActive(number, validStart, validEnd);
    }

}
