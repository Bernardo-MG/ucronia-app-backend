/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2022 the original author or authors.
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

package com.bernardomg.association.fee.repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.bernardomg.association.fee.model.DtoMemberFee;
import com.bernardomg.association.fee.model.FeeRequest;
import com.bernardomg.association.fee.model.MemberFee;
import com.bernardomg.association.fee.model.QPersistentFee;
import com.bernardomg.association.member.model.QPersistentMember;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.JPQLQueryFactory;

import lombok.AllArgsConstructor;

@Repository
@AllArgsConstructor
public final class DefaultMemberFeeRepository implements MemberFeeRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private final JPQLQueryFactory           jpqlQueryFactory;

    private final String                     queryAllCount = "SELECT COUNT(f.id) AS count FROM fees f JOIN members m ON f.member_id = m.id";

    @SuppressWarnings("unchecked")
    @Override
    public final Page<MemberFee> findAllWithMember(final FeeRequest request, final Pageable pageable) {
        final QPersistentFee        fee;
        final QPersistentMember     member;
        final Collection<Predicate> exprs;
        final List<MemberFee>       read;
        BooleanExpression           predicate;
        JPQLQuery<?>                query;

        fee = QPersistentFee.persistentFee;
        member = QPersistentMember.persistentMember;

        query = jpqlQueryFactory
            .select(Projections.constructor(DtoMemberFee.class, fee.id, member.id, member.name, member.surname,
                fee.date, fee.paid))
            .from(fee)
            .join(member)
            .on(fee.memberId.eq(member.id));

        exprs = new ArrayList<>();
        if (request.getDate() != null) {
            predicate = fee.date.eq(request.getDate());
            exprs.add(predicate);
        } else {
            if (request.getStartDate() != null) {
                predicate = fee.date.after(request.getStartDate())
                    .or(fee.date.eq(request.getStartDate()));
                exprs.add(predicate);
            }
            if (request.getEndDate() != null) {
                predicate = fee.date.before(request.getEndDate())
                    .or(fee.date.eq(request.getEndDate()));
                exprs.add(predicate);
            }
        }

        if (!exprs.isEmpty()) {
            query.where(exprs.toArray(new Predicate[exprs.size()]));
        }

        if (pageable.isPaged()) {
            query.limit(pageable.getPageSize())
                .offset(pageable.getOffset());
        }

        if (pageable.getSort()
            .isSorted()) {
            pageable.getSort()
                .stream()
                .forEach(o -> {
                    final OrderSpecifier<?> order;

                    if ("id".equals(o.getProperty())) {
                        if (o.isAscending()) {
                            order = fee.id.asc();
                        } else {
                            order = fee.id.desc();
                        }
                        query.orderBy(order);
                    } else if ("member_id".equals(o.getProperty())) {
                        if (o.isAscending()) {
                            order = member.id.asc();
                        } else {
                            order = member.id.desc();
                        }
                        query.orderBy(order);
                    } else if ("date".equals(o.getProperty())) {
                        if (o.isAscending()) {
                            order = fee.date.asc();
                        } else {
                            order = fee.date.desc();
                        }
                        query.orderBy(order);
                    } else if ("paid".equals(o.getProperty())) {
                        if (o.isAscending()) {
                            order = fee.paid.asc();
                        } else {
                            order = fee.paid.desc();
                        }
                        query.orderBy(order);
                    } else if ("name".equals(o.getProperty())) {
                        if (o.isAscending()) {
                            order = member.name.asc();
                        } else {
                            order = member.name.desc();
                        }
                        query.orderBy(order);
                    } else if ("surname".equals(o.getProperty())) {
                        if (o.isAscending()) {
                            order = member.surname.asc();
                        } else {
                            order = member.surname.desc();
                        }
                        query.orderBy(order);
                    }

                });
        }

        read = (List<MemberFee>) query.fetch();

        return PageableExecutionUtils.getPage(read, pageable, () -> countAll());
    }

    private final Long countAll() {
        return jdbcTemplate.queryForObject(queryAllCount, Collections.emptyMap(), Long.class);
    }

}
