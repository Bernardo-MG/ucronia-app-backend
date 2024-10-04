
package com.bernardomg.association.member.usecase.service;

import java.time.YearMonth;
import java.util.Objects;

import org.springframework.transaction.annotation.Transactional;

import com.bernardomg.association.member.domain.repository.MemberRepository;

@Transactional
public final class DefaultMemberFeeService implements MemberStatusService {

    private final MemberRepository memberRepository;

    public DefaultMemberFeeService(final MemberRepository memberRepo) {
        super();

        memberRepository = Objects.requireNonNull(memberRepo);
    }

    @Override
    public final void activate(final YearMonth date, final Long personNumber) {
        if (YearMonth.now()
            .equals(date)) {
            // If paying for the current month, the user is set to active
            memberRepository.activate(personNumber);
        }

    }

}
