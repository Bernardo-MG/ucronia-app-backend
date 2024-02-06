
package com.bernardomg.association.auth.user.domain.repository;

import java.util.Optional;

import com.bernardomg.association.auth.user.domain.model.UserMember;

public interface UserMemberRepository {

    public void delete(final String username);

    public Optional<UserMember> findByUsername(final String username);

    public UserMember save(final String username, final Long number);

}
