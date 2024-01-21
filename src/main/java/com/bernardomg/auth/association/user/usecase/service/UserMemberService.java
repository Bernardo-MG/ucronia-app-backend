
package com.bernardomg.auth.association.user.usecase.service;

import java.util.Optional;

import com.bernardomg.auth.association.user.domain.model.UserMember;

public interface UserMemberService {

    public UserMember assignMember(final String username, final long memberId);

    public void deleteMember(final String username);

    public Optional<UserMember> getMember(final String username);

    public UserMember updateMember(final String username, final long memberId);

}
