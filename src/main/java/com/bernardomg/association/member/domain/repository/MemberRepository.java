
package com.bernardomg.association.member.domain.repository;

import java.util.Optional;

import org.springframework.data.domain.Pageable;

import com.bernardomg.association.member.domain.model.Member;

public interface MemberRepository {

    public Iterable<Member> findActive(final Pageable pageable);

    public Iterable<Member> findAll(final Pageable pageable);

    public Iterable<Member> findInactive(final Pageable pageable);

    public Optional<Member> findOne(final Long number);

}
