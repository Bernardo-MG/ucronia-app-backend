
package com.bernardomg.association.auth.user.domain.repository;

import java.util.Optional;

import com.bernardomg.association.auth.user.domain.model.UserMember;
import com.bernardomg.association.member.domain.model.Member;

public interface UserMemberRepository {

    public void delete(final String username);

    public Optional<Member> findByUsername(final String username);

    public UserMember save(final String username, final Long number);

}
