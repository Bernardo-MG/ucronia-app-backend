
package com.bernardomg.association.security.user.usecase.service;

import java.util.Collection;
import java.util.Optional;

import org.springframework.data.domain.Pageable;

import com.bernardomg.association.member.domain.model.Member;

public interface UserMemberService {

    public Member assignMember(final String username, final long memberId);

    public Collection<Member> getAvailableMembers(final String username, final Pageable page);

    public Optional<Member> getMember(final String username);

    public void unassignMember(final String username);

}
