
package com.bernardomg.association.member.domain.repository;

import java.util.Optional;

import com.bernardomg.association.member.domain.model.Member;
import com.bernardomg.data.domain.Pagination;
import com.bernardomg.data.domain.Sorting;

public interface MemberRepository {

    public Iterable<Member> findActive(final Pagination pagination, final Sorting sorting);

    public Iterable<Member> findAll(final Pagination pagination, final Sorting sorting);

    public Iterable<Member> findInactive(final Pagination pagination, final Sorting sorting);

    public Optional<Member> findOne(final Long number);

}
