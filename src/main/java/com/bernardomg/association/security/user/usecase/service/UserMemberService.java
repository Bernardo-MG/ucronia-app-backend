
package com.bernardomg.association.security.user.usecase.service;

import java.util.Optional;

import com.bernardomg.association.member.domain.model.Member;

public interface UserMemberService {

    public Member assignMember(final String username, final long memberId);

    public Optional<Member> getMember(final String username);

    public void unassignMember(final String username);

}
