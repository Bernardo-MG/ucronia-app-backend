
package com.bernardomg.association.fee.adapter.inbound.jpa.repository;

import org.springframework.transaction.annotation.Transactional;

import com.bernardomg.association.fee.domain.repository.ActiveMemberRepository;
import com.bernardomg.association.member.adapter.inbound.jpa.repository.MemberSpringRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Transactional
public final class JpaActiveMemberRepository implements ActiveMemberRepository {

    private final MemberSpringRepository memberRepository;

    public JpaActiveMemberRepository(final MemberSpringRepository memberRepo) {
        super();

        memberRepository = memberRepo;
    }

    @Override
    public final boolean isActivePreviousMonth(final long number) {
        final boolean active;

        log.debug("Checking if member {} is active in the previous month", number);

        active = memberRepository.isActive(number);

        log.debug("Member {} is active in the previous month: {}", number, active);

        return active;
    }

}
