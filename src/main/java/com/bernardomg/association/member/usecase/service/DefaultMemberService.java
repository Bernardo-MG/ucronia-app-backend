
package com.bernardomg.association.member.usecase.service;

import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bernardomg.association.member.domain.exception.MissingMemberException;
import com.bernardomg.association.member.domain.model.Member;
import com.bernardomg.association.member.domain.repository.MemberRepository;
import com.bernardomg.data.domain.Pagination;
import com.bernardomg.data.domain.Sorting;

/**
 * Default implementation of the member service.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@Service
@Transactional
public final class DefaultMemberService implements MemberService {

    /**
     * Logger for the class.
     */
    private static final Logger    log = LoggerFactory.getLogger(DefaultMemberService.class);

    private final MemberRepository memberRepository;

    public DefaultMemberService(final MemberRepository memberRepo) {
        super();

        memberRepository = Objects.requireNonNull(memberRepo);
    }

    @Override
    public final Iterable<Member> getAll(final Pagination pagination, final Sorting sorting) {
        log.debug("Reading public members with pagination {} and sorting {}", pagination, sorting);

        return memberRepository.findAll(pagination, sorting);
    }

    @Override
    public final Optional<Member> getOne(final long number) {
        final Optional<Member> member;

        log.debug("Reading public member {}", number);

        member = memberRepository.findOne(number);
        if (member.isEmpty()) {
            log.error("Missing member {}", number);
            throw new MissingMemberException(number);
        }

        return member;
    }

}
