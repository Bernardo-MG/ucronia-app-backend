
package com.bernardomg.association.member.service;

import java.util.Optional;

import com.bernardomg.association.member.model.Member;

public interface MemberService {

    public Member create(final Member member);

    public Boolean delete(final Long id);

    public Iterable<? extends Member> getAll(final Member sample);

    public Optional<? extends Member> getOne(final Long id);

    public Member update(final Long id, final Member member);

}
