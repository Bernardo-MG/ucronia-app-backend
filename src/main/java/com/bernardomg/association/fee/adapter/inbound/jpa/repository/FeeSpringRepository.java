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

package com.bernardomg.association.fee.adapter.inbound.jpa.repository;

import java.time.Instant;
import java.util.Collection;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bernardomg.association.fee.adapter.inbound.jpa.model.FeeEntity;
import com.bernardomg.association.fee.domain.model.FeeBalance;

public interface FeeSpringRepository extends JpaRepository<FeeEntity, Long>, JpaSpecificationExecutor<FeeEntity> {

    public void deleteByMemberIdAndMonth(final Long memberId, final Instant month);

    @Query("""
               SELECT CASE WHEN COUNT(f) > 0 THEN TRUE ELSE FALSE END AS exists
               FROM Fee f
                 INNER JOIN f.member m
               WHERE m.number = :number
                 AND f.month = :month
            """)
    public boolean existsByMemberNumberAndMonth(@Param("number") final Long number,
            @Param("month") final Instant month);

    @Query("""
               SELECT CASE WHEN COUNT(f) > 0 THEN TRUE ELSE FALSE END AS exists
               FROM Fee f
                 INNER JOIN f.member m
               WHERE m.number = :number
                 AND f.month = :month
                 AND f.transaction IS NOT NULL
            """)
    public boolean existsByMemberNumberAndMonthAndPaid(@Param("number") final Long number,
            @Param("v") final Instant month);

    @Query("""
               SELECT f
               FROM Fee f
                 INNER JOIN f.member m
               WHERE m.number = :number
            """)
    public Page<FeeEntity> findAllByMemberNumber(@Param("number") final Long number, final Pageable pageable);

    @Query("""
               SELECT f
               FROM Member m
                 INNER JOIN Fee f ON m.id = f.memberId
                 LEFT JOIN Transaction t ON f.transaction.id = t.id
               WHERE m.number = :memberNumber
                 AND f.month in :feeMonths
            """)
    public Collection<FeeEntity> findAllFeesByMemberNumberAndDateIn(@Param("memberNumber") final Long memberNumber,
            @Param("feeMonths") final Collection<Instant> feeMonths);

    /**
     * Returns all member fees for the year.
     *
     * @param year
     *            year to filter by
     * @param sort
     *            sorting information
     * @return all member fees filtered by year
     */
    @Query("""
               SELECT f
               FROM Fee f
                  INNER JOIN f.member m
               WHERE EXTRACT(YEAR FROM f.month) = :year
            """)
    public Collection<FeeEntity> findAllForYear(@Param("year") int year, Sort sort);

    /**
     * Returns all member fees with any of the received ids, and which are in the year.
     *
     * @param year
     *            year to filter by
     * @param ids
     *            ids of the members to filter by
     * @param sort
     *            sorting information
     * @return all member fees filtered by id and year
     */
    @Query("""
            SELECT f
            FROM Fee f
               INNER JOIN f.member m
            WHERE EXTRACT(YEAR FROM f.month) = :year
              AND f.memberId IN :ids
            """)
    public Collection<FeeEntity> findAllForYearAndMembersIn(@Param("year") int year, @Param("ids") Collection<Long> ids,
            Sort sort);

    @Query("""
            SELECT f
            FROM Fee f
               INNER JOIN f.member m
            """)
    public Page<FeeEntity> findAllWithMember(final Pageable pageable);

    /**
     * Finds the fee for the member in a month.
     *
     * @param memberId
     *            member to filter by
     * @param month
     *            month to filter by
     * @return fee for the member in a month
     */
    public Optional<FeeEntity> findByMemberIdAndMonth(final Long memberId, final Instant month);

    /**
     * Finds the fee for the member in the month.
     *
     * @param number
     *            member to filter by
     * @param month
     *            month to filter by
     * @return fee for the member in the month
     */
    @Query("""
               SELECT f
               FROM Fee f
                 INNER JOIN f.member m
               WHERE m.number = :number
                 AND f.month = :month
            """)
    public Optional<FeeEntity> findByMemberNumberAndDate(@Param("number") final Long number,
            @Param("month") final Instant month);

    /**
     * Returns all the years based on the existing fees.
     *
     * @return all the years for the existing fees
     */
    @Query("""
            SELECT extract(year from f.month) AS feeYear
            FROM Fee f
             GROUP BY feeYear
             ORDER BY feeYear ASC
            """)
    public Collection<Integer> findYears();

    @Query("""
            SELECT new com.bernardomg.association.fee.domain.model.FeeBalance(
              COALESCE(SUM(CASE WHEN f.paid = TRUE THEN 1 ELSE 0 END), 0),
              COALESCE(SUM(CASE WHEN f.paid = FALSE THEN 1 ELSE 0 END), 0)
            )
            FROM Fee f
            WHERE f.month = :monthStart
            """)
    FeeBalance findBalanceForMonth(@Param("monthStart") Instant monthStart);

}
