
package com.bernardomg.association.auth.user.service;

import java.util.Optional;

import com.bernardomg.association.auth.user.model.UserMember;

public interface UserMemberService {

    public UserMember assignMember(final long userId, final long memberId);

    public void deleteMember(final long userId, final long memberId);

    public Optional<UserMember> readMember(final long userId);

    public UserMember updateMember(final long userId, final long memberId);

}
