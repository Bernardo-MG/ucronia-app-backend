/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2023 the original author or authors.
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

package com.bernardomg.association.membership.member.persistence.repository;

import java.time.YearMonth;
import java.util.Collection;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bernardomg.association.membership.member.persistence.model.MemberEntity;

public interface MemberRepository extends JpaRepository<MemberEntity, Long> {

    @Query("SELECT case when (count(*) > 0) then true else false end FROM Member m INNER JOIN Fee f ON m.id = f.memberId WHERE m.id = :id")
    public boolean existsActive(@Param("id") final Long id);

    @Query("SELECT m FROM Member m INNER JOIN Fee f ON m.id = f.memberId WHERE f.date >= :start AND f.date <= :end GROUP BY m.id")
    public Page<MemberEntity> findAllActive(final Pageable pageable, @Param("start") final YearMonth start,
            @Param("end") final YearMonth end);

    @Query("SELECT m.id FROM Member m INNER JOIN Fee f ON m.id = f.memberId WHERE f.date >= :start AND f.date <= :end")
    public Collection<Long> findAllActiveIds(@Param("start") final YearMonth start, @Param("end") final YearMonth end);

    @Query("SELECT m FROM Member m LEFT JOIN Fee f ON m.id = f.memberId AND f.date >= :start AND f.date <= :end WHERE f.id IS NULL GROUP BY m.id")
    public Page<MemberEntity> findAllInactive(final Pageable pageable, @Param("start") final YearMonth start,
            @Param("end") final YearMonth end);

    @Query("SELECT m.id FROM Member m LEFT JOIN Fee f ON m.id = f.memberId AND f.date >= :start AND f.date <= :end WHERE f.id IS NULL")
    public Collection<Long> findAllInactiveIds(@Param("start") final YearMonth start,
            @Param("end") final YearMonth end);

}
