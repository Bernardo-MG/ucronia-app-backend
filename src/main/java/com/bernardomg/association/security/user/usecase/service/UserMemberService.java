
package com.bernardomg.association.security.user.usecase.service;

import java.util.Optional;

import com.bernardomg.association.member.domain.model.Member;

public interface UserMemberService {

    public Member assignMember(final String username, final long memberId);

    public void deleteMember(final String username);

    public Optional<Member> getMember(final String username);

    public Member updateMember(final String username, final long memberId);

}
