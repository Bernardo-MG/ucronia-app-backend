/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2022-2025 Bernardo Martínez Garrido
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.bernardomg.association.member.adapter.inbound.jpa.repository;

import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bernardomg.association.member.adapter.inbound.jpa.model.QueryMemberEntity;
import com.bernardomg.association.member.adapter.inbound.jpa.model.QueryMemberEntityMapper;
import com.bernardomg.association.member.adapter.inbound.jpa.specification.MemberSpecifications;
import com.bernardomg.association.member.domain.filter.MemberFilter;
import com.bernardomg.association.member.domain.model.Member;
import com.bernardomg.association.member.domain.repository.MemberRepository;
import com.bernardomg.data.domain.Page;
import com.bernardomg.data.domain.Pagination;
import com.bernardomg.data.domain.Sorting;
import com.bernardomg.data.springframework.SpringPagination;

@Repository
@Transactional
public final class JpaMemberRepository implements MemberRepository {

    /**
     * Logger for the class.
     */
    private static final Logger               log = LoggerFactory.getLogger(JpaMemberRepository.class);

    private final QueryMemberSpringRepository queryMemberSpringRepository;

    public JpaMemberRepository(final QueryMemberSpringRepository queryMemberSpringRepo) {
        super();

        queryMemberSpringRepository = Objects.requireNonNull(queryMemberSpringRepo);
    }

    @Override
    public final Page<Member> findAll(final MemberFilter filter, final Pagination pagination, final Sorting sorting) {
        final org.springframework.data.domain.Page<Member> read;
        final Pageable                                     pageable;
        final Optional<Specification<QueryMemberEntity>>   spec;

        log.debug("Finding all the members with filter {}, pagination {} and sorting {}", filter, pagination, sorting);

        pageable = SpringPagination.toPageable(pagination, sorting);
        spec = MemberSpecifications.query(filter);
        if (spec.isEmpty()) {
            read = queryMemberSpringRepository.findAllByActiveTrue(pageable)
                .map(QueryMemberEntityMapper::toDomain);
        } else {
            read = queryMemberSpringRepository.findAll(spec.get(), pageable)
                .map(QueryMemberEntityMapper::toDomain);
        }

        log.debug("Found all the members with filter {}, pagination {} and sorting {}: {}", filter, pagination, sorting,
            read);

        return SpringPagination.toPage(read);
    }

    @Override
    public final Optional<Member> findOne(final Long number) {
        final Optional<Member> member;

        log.trace("Finding member with number {}", number);

        member = queryMemberSpringRepository.findByNumberAndActiveTrue(number)
            .map(QueryMemberEntityMapper::toDomain);

        log.trace("Found member with number {}: {}", number, member);

        return member;
    }

}
