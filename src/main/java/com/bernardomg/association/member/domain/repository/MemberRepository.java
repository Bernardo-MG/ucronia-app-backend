
package com.bernardomg.association.member.domain.repository;

import java.util.Optional;

import org.springframework.data.domain.Pageable;

import com.bernardomg.association.member.domain.model.Member;

public interface MemberRepository {

    public void activate(final Iterable<Long> numbers);

    public void activate(final long number);

    public void deactivate(final long number);

    public void delete(final long number);

    public boolean exists(final long number);

    public Iterable<Member> findActive(final Pageable pageable);

    public Iterable<Member> findAll(final Pageable pageable);

    public Iterable<Member> findInactive(final Pageable pageable);

    public long findNextNumber();

    public Optional<Member> findOne(final Long number);

    public Member save(final Member member);

}
