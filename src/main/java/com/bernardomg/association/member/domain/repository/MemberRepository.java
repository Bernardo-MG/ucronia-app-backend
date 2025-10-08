
package com.bernardomg.association.member.domain.repository;

import java.util.Optional;

import com.bernardomg.association.member.domain.model.Member;
import com.bernardomg.data.domain.Page;
import com.bernardomg.data.domain.Pagination;
import com.bernardomg.data.domain.Sorting;

public interface MemberRepository {

    public Page<Member> findAll(final Pagination pagination, final Sorting sorting);

    public Optional<Member> findOne(final Long number);

}
