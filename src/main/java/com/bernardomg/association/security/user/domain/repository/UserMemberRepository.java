
package com.bernardomg.association.security.user.domain.repository;

import java.util.Collection;
import java.util.Optional;

import org.springframework.data.domain.Pageable;

import com.bernardomg.association.member.domain.model.Member;

public interface UserMemberRepository {

    public void delete(final String username);

    public boolean existsByMemberForAnotherUser(final String username, final long number);

    public Collection<Member> findAllNotAssigned(final Pageable page);

    public Optional<Member> findByUsername(final String username);

    public Member save(final String username, final long number);

}
