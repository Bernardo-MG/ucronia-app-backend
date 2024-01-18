
package com.bernardomg.association.member.usecase;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.bernardomg.association.member.domain.model.Member;

public interface MemberRepository {

    public void delete(final long number);

    public boolean exists(final long number);

    public Page<Member> findActive(final Pageable pageable);

    public Page<Member> findAll(final Pageable pageable);

    public Page<Member> findInactive(final Pageable pageable);

    public long findNextNumber();

    public Optional<Member> findOne(final Long number);

    public Member save(final Member member);

}
