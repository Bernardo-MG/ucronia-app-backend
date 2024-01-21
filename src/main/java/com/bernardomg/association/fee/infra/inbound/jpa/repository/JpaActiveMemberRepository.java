
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
    public boolean isActivePreviousMonth(final Long number) {
        final YearMonth validStart;
        final YearMonth validEnd;

        // TODO: Should be done in the repository
        validStart = YearMonth.now()
            .minusMonths(1);
        validEnd = YearMonth.now()
            .minusMonths(1);
        return activeMemberRepository.isActive(number, validStart, validEnd);
    }

}
