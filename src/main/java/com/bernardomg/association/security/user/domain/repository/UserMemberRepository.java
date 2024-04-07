
package com.bernardomg.association.security.user.domain.repository;

import java.util.Optional;

import com.bernardomg.association.member.domain.model.Member;
import com.bernardomg.association.security.user.domain.model.UserMember;

public interface UserMemberRepository {

    public void delete(final String username);

    public Optional<Member> findByUsername(final String username);

    public UserMember save(final String username, final Long number);

}
