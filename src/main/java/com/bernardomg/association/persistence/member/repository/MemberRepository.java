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

package com.bernardomg.association.persistence.member.repository;

import java.time.YearMonth;
import java.util.Collection;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bernardomg.association.persistence.member.model.MemberEntity;

public interface MemberRepository extends JpaRepository<MemberEntity, Long> {

    @Query("SELECT m FROM Member m INNER JOIN Fee f ON m.id = f.memberId WHERE f.date >= :start AND f.date <= :end GROUP BY m.id")
    public Page<MemberEntity> findAllActive(final Pageable pageable, @Param("start") final YearMonth start,
            @Param("end") final YearMonth end);

    /**
     * Returns the ids for all the members active in the received date range. This means, any member which has fees
     * inside the range, both extremes included.
     *
     * @param start
     *            starting date to search in
     * @param end
     *            end date to search in
     * @return all the ids for the members active in the range
     */
    @Query("SELECT m.id FROM Member m INNER JOIN Fee f ON m.id = f.memberId WHERE f.date >= :start AND f.date <= :end")
    public Collection<Long> findAllActiveIdsInRange(@Param("start") final YearMonth start,
            @Param("end") final YearMonth end);

    /**
     * Returns the numbers for all the members active in the received date range. This means, any member which has fees
     * inside the range, both extremes included.
     *
     * @param start
     *            starting date to search in
     * @param end
     *            end date to search in
     * @return all the ids for the members active in the range
     */
    @Query("SELECT m.number FROM Member m INNER JOIN Fee f ON m.id = f.memberId WHERE f.date >= :start AND f.date <= :end")
    public Collection<Long> findAllActiveNumbersInRange(@Param("start") final YearMonth start,
            @Param("end") final YearMonth end);

    @Query("SELECT m FROM Member m LEFT JOIN Fee f ON m.id = f.memberId AND f.date >= :start AND f.date <= :end WHERE f.id IS NULL GROUP BY m.id")
    public Page<MemberEntity> findAllInactive(final Pageable pageable, @Param("start") final YearMonth start,
            @Param("end") final YearMonth end);

    @Query("SELECT m.id FROM Member m LEFT JOIN Fee f ON m.id = f.memberId AND f.date >= :start AND f.date <= :end WHERE f.id IS NULL")
    public Collection<Long> findAllInactiveIds(@Param("start") final YearMonth start,
            @Param("end") final YearMonth end);

    public Optional<MemberEntity> findByNumber(final Long number);

    @Query("SELECT COALESCE(MAX(m.number), 0) + 1 FROM Member m")
    public Long findNextNumber();

    /**
     * Returns if the member is active in the received range. This means if the member has fees inside the range, both
     * extremes included.
     *
     * @param id
     *            id of the member to search for
     * @param start
     *            starting date to search in
     * @param end
     *            end date to search in
     * @return {@code true} if the member is active, {@code false} otherwise
     */
    @Query("SELECT CASE WHEN COUNT(f) > 0 THEN TRUE ELSE FALSE END AS active FROM Member m LEFT JOIN Fee f ON m.id = f.memberId WHERE f.date >= :start AND f.date <= :end AND m.id = :id")
    public boolean isActive(@Param("id") final Long id, @Param("start") final YearMonth start,
            @Param("end") final YearMonth end);

}
