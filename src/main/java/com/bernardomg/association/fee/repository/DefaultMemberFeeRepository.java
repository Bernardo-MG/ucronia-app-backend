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

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.bernardomg.association.fee.model.MemberFee;
import com.bernardomg.association.fee.model.PersistentFee;

import lombok.AllArgsConstructor;

@Repository
@AllArgsConstructor
public final class DefaultMemberFeeRepository implements MemberFeeRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private final String                     queryAll      = "SELECT f.id AS id, m.name AS name, m.surname AS surname, m.id AS member_id, f.date AS date, f.paid AS paid FROM fees f JOIN members m ON f.member_id = m.id";

    private final String                     queryAllCount = "SELECT COUNT(f.id) AS count FROM fees f JOIN members m ON f.member_id = m.id";

    private final String                     queryOne      = "SELECT f.id AS id, m.name AS name, m.surname AS surname, m.id AS member_id, f.date AS date, f.paid AS paid FROM fees f JOIN members m ON f.member_id = m.id WHERE f.id = :id";

    @Override
    public final Page<MemberFee> findAllWithMember(final Example<PersistentFee> example, final Pageable pageable) {
        final List<MemberFee> data;

        data = findAll(pageable);

        // TODO: Test pagination
        return PageableExecutionUtils.getPage(data, pageable, () -> countAll());
    }

    @Override
    public final Optional<MemberFee> findOneByIdWithMember(final Long id) {
        final SqlParameterSource namedParameters;
        MemberFee                fee;

        namedParameters = new MapSqlParameterSource().addValue("id", id);
        try {
            fee = jdbcTemplate.queryForObject(queryOne, namedParameters, new MemberFeeRowMapper());
        } catch (final EmptyResultDataAccessException e) {
            fee = null;
        }

        return Optional.ofNullable(fee);
    }

    private final Long countAll() {
        return jdbcTemplate.queryForObject(queryAllCount, Collections.emptyMap(), Long.class);
    }

    private final List<MemberFee> findAll(final Pageable pageable) {
        final String sorting;
        final String paging;

        if (pageable.getSort()
            .isSorted()) {
            sorting = " ORDER BY " + pageable.getSort()
                .get()
                .map(this::toSorting)
                .collect(Collectors.joining(" ,"));
        } else {
            sorting = "";
        }

        if (pageable.isPaged()) {
            paging = String.format(" LIMIT %d OFFSET %d", pageable.getPageSize(), pageable.getOffset());
        } else {
            paging = "";
        }

        return jdbcTemplate.query(queryAll + sorting + paging, new MemberFeeRowMapper());
    }

    private final String toSorting(final Order order) {
        final String direction;
        // TODO: Duplicated in other repositories

        if (order.isAscending()) {
            direction = "ASC";
        } else {
            direction = "DESC";
        }

        return order.getProperty() + " " + direction;
    }

}
