
package com.bernardomg.association.member.domain.repository;

import java.util.Optional;

import org.springframework.data.domain.Pageable;

import com.bernardomg.association.member.domain.model.PublicMember;

public interface PublicMemberRepository {

    public Iterable<PublicMember> findActive(final Pageable pageable);

    public Iterable<PublicMember> findAll(final Pageable pageable);

    public Iterable<PublicMember> findInactive(final Pageable pageable);

    public Optional<PublicMember> findOne(final Long number);

}
