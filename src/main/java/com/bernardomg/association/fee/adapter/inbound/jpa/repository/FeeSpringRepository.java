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
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bernardomg.association.fee.adapter.inbound.jpa.model.FeeEntity;

public interface FeeSpringRepository extends JpaRepository<FeeEntity, Long>, JpaSpecificationExecutor<FeeEntity> {

    public void deleteByMemberIdAndDate(final Long memberId, final Instant date);

    @Query("""
               SELECT CASE WHEN COUNT(f) > 0 THEN TRUE ELSE FALSE END AS exists
               FROM Fee f
                 INNER JOIN f.member m
                 INNER JOIN m.contact c
               WHERE c.number = :number
                 AND f.date = :date
            """)
    public boolean existsByMemberNumberAndDate(@Param("number") final Long number, @Param("date") final Instant date);

    @Query("""
               SELECT CASE WHEN COUNT(f) > 0 THEN TRUE ELSE FALSE END AS exists
               FROM Fee f
                 INNER JOIN f.member m
                 INNER JOIN m.contact c
               WHERE c.number = :number
                 AND f.date = :date
                 AND f.transaction IS NOT NULL
            """)
    public boolean existsByMemberNumberAndDateAndPaid(@Param("number") final Long number,
            @Param("date") final Instant date);

    /**
     * Returns all the fees in the received date.
     *
     * @param date
     *            date to filter by
     * @return all the fees in the date
     */
    public List<FeeEntity> findAllByDate(final Instant date);

    @Query("""
               SELECT f
               FROM Fee f
                 INNER JOIN f.member m
                 INNER JOIN m.contact c
               WHERE c.number = :number
            """)
    public Page<FeeEntity> findAllByMemberNumber(@Param("number") final Long number, final Pageable pageable);

    @Query("""
               SELECT f
               FROM MemberContact m
                 INNER JOIN m.contact c
                 INNER JOIN Fee f ON m.id = f.memberId
                 LEFT JOIN Transaction t ON f.transaction.id = t.id
               WHERE c.number = :memberNumber
                 AND f.date in :feeMonths
            """)
    public Collection<FeeEntity> findAllFeesByMemberNumberAndDateIn(@Param("memberNumber") final Long memberNumber,
            @Param("feeMonths") final Collection<Instant> feeMonths);

    /**
     * Returns all member fees inside the received range.
     *
     * @param year
     *            year to filter by
     * @param sort
     *            sorting information
     * @return all member fees filtered by date range
     */
    @Query("""
               SELECT f
               FROM Fee f
                  INNER JOIN f.member m
               WHERE EXTRACT(YEAR FROM f.date) = :year
            """)
    public Collection<FeeEntity> findAllForYear(@Param("year") int year, Sort sort);

    /**
     * Returns all member fees with any of the received ids, and which are inside the received range.
     *
     * @param year
     *            year to filter by
     * @param ids
     *            ids of the members to filter by
     * @param sort
     *            sorting information
     * @return all member fees filtered by id and date range
     */
    @Query("""
            SELECT f
            FROM Fee f
               INNER JOIN f.member m
            WHERE EXTRACT(YEAR FROM f.date) = :year
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
     * Finds the fee for the contact in the date.
     *
     * @param memberId
     *            member to filter by
     * @param date
     *            date to filter by
     * @return fee for the contact in the date
     */
    public Optional<FeeEntity> findByMemberIdAndDate(final Long memberId, final Instant date);

    /**
     * Finds the fee for the member in the date.
     *
     * @param memberNumber
     *            member to filter by
     * @param date
     *            date to filter by
     * @return fee for the member in the date
     */
    @Query("""
               SELECT f
               FROM Fee f
                 INNER JOIN f.member m
                 INNER JOIN m.contact c
               WHERE c.number = :number
                 AND f.date = :date
            """)
    public Optional<FeeEntity> findByMemberNumberAndDate(@Param("number") final Long number,
            @Param("date") final Instant date);

    /**
     * Returns all the years based on the existing fees.
     *
     * @return all the years for the existing fees
     */
    @Query("""
            SELECT extract(year from f.date) AS feeYear
            FROM Fee f
             GROUP BY feeYear
             ORDER BY feeYear ASC
            """)
    public Collection<Integer> findYears();

}
