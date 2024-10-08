
package com.bernardomg.association.member.usecase.service;

import java.time.YearMonth;
import java.util.Objects;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bernardomg.association.member.domain.repository.MemberRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public final class DefaultMemberStatusService implements MemberStatusService {

    private final MemberRepository memberRepository;

    public DefaultMemberStatusService(final MemberRepository memberRepo) {
        super();

        memberRepository = Objects.requireNonNull(memberRepo);
    }

    @Override
    public final void activate(final YearMonth date, final Long personNumber) {
        if (YearMonth.now()
            .equals(date)) {
            log.debug("Activating member status for person {}", personNumber);
            // If paying for the current month, the user is set to active
            memberRepository.activate(personNumber);
        }

    }

    @Override
    public final void deactivate(final YearMonth date, final Long personNumber) {
        if (YearMonth.now()
            .equals(date)) {
            log.debug("Deactivating member status for person {}", personNumber);
            // If deleting at the current month, the user is set to inactive
            memberRepository.deactivate(personNumber);
        }

    }

}
