
package com.bernardomg.association.auth.user.usecase.service;

import java.util.Optional;

import com.bernardomg.association.auth.user.domain.model.UserMember;
import com.bernardomg.association.member.domain.model.Member;

public interface UserMemberService {

    public UserMember assignMember(final String username, final long memberId);

    public void deleteMember(final String username);

    public Optional<Member> getMember(final String username);

    public UserMember updateMember(final String username, final long memberId);

}
