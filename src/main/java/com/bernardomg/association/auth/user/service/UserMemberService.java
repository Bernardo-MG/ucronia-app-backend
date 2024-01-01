
package com.bernardomg.association.auth.user.service;

import com.bernardomg.association.auth.user.model.UserMember;

public interface UserMemberService {

    public UserMember assignMember(final long userId, final long memberId);

    public void deleteMember(final long userId, final long memberId);

    public UserMember readMember(final long userId);

    public UserMember updateMember(final long userId, final long memberId);

}
