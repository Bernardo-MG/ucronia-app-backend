
package com.bernardomg.association.auth.user.service;

import java.util.Optional;

import com.bernardomg.association.auth.user.model.UserMember;

public interface UserMemberService {

    public UserMember assignMember(final String username, final long memberId);

    public void deleteMember(final String username, final long memberId);

    public Optional<UserMember> getMember(final String username);

    public UserMember updateMember(final String username, final long memberId);

}
