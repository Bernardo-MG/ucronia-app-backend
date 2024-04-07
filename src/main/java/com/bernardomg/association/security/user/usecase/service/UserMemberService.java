
package com.bernardomg.association.security.user.usecase.service;

import java.util.Optional;

import com.bernardomg.association.member.domain.model.Member;
import com.bernardomg.association.security.user.domain.model.UserMember;

public interface UserMemberService {

    public UserMember assignMember(final String username, final long memberId);

    public void deleteMember(final String username);

    public Optional<Member> getMember(final String username);

    public UserMember updateMember(final String username, final long memberId);

}
