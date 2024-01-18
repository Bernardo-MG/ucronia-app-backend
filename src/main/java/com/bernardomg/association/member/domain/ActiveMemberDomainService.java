
package com.bernardomg.association.member.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.bernardomg.association.member.model.Member;

public interface ActiveMemberDomainService {

    public Page<Member> findAll(final Pageable pageable);

    public Page<Member> findActive(final Pageable pageable);

    public Page<Member> findInactive(final Pageable pageable);

    public Member findOne(final Long id);

}
