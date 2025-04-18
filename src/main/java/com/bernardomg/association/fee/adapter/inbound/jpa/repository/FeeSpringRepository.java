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

package com.bernardomg.association.fee.adapter.inbound.jpa.repository;

import java.time.YearMonth;
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

    public void deleteByPersonIdAndDate(final Long personId, final YearMonth date);

    @Query("""
               SELECT CASE WHEN COUNT(f) > 0 THEN TRUE ELSE FALSE END AS exists
               FROM Fee f
                 INNER JOIN Person p ON p.id = f.personId
               WHERE p.number = :number
                 AND f.date = :date
            """)
    public boolean existsByPersonNumberAndDate(@Param("number") final Long number, @Param("date") final YearMonth date);

    @Query("""
               SELECT CASE WHEN COUNT(f) > 0 THEN TRUE ELSE FALSE END AS exists
               FROM Fee f
                 INNER JOIN Person p ON p.id = f.personId
               WHERE p.number = :number
                 AND f.date = :date
                 AND f.transactionId IS NOT NULL
            """)
    public boolean existsByPersonNumberAndDateAndPaid(@Param("number") final Long number,
            @Param("date") final YearMonth date);

    /**
     * Returns all the fees in the received date.
     *
     * @param date
     *            date to filter by
     * @return all the fees in the date
     */
    public List<FeeEntity> findAllByDate(final YearMonth date);

    @Query("""
            SELECT f
            FROM Fee f
               INNER JOIN Person p ON p.id = f.personId
            WHERE p.number = :number
            """)
    public Page<FeeEntity> findAllByPersonNumber(@Param("number") final Long number, final Pageable pageable);

    @Query("""
               SELECT f
               FROM Person p
                 INNER JOIN Fee f ON p.id = f.personId
                 LEFT JOIN Transaction t ON f.transactionId = t.id
               WHERE p.number = :memberNumber
                 AND f.date in :feeMonths
            """)
    public Collection<FeeEntity> findAllFeesByPersonNumberAndDateIn(@Param("memberNumber") final Long memberNumber,
            @Param("feeMonths") final Collection<YearMonth> feeMonths);

    /**
     * Returns all member fees inside the received range.
     *
     * @param start
     *            starting date to search in
     * @param end
     *            end date to search in
     * @param sort
     *            sorting information
     * @return all member fees filtered by date range
     */
    @Query("""
            SELECT f
            FROM Fee f
               INNER JOIN Person p ON p.id = f.personId
            WHERE f.date >= :start
              AND f.date <= :end
            """)
    public Collection<FeeEntity> findAllInRange(@Param("start") final YearMonth start,
            @Param("end") final YearMonth end, final Sort sort);

    /**
     * Returns all member fees with any of the received ids, and which are inside the received range.
     *
     * @param start
     *            starting date to search in
     * @param end
     *            end date to search in
     * @param ids
     *            ids of the members to filter by
     * @param sort
     *            sorting information
     * @return all member fees filtered by id and date range
     */
    @Query("""
            SELECT f
            FROM Fee f
               INNER JOIN Person p ON p.id = f.personId
            WHERE f.date >= :start
              AND f.date <= :end
              AND f.personId IN :ids
            """)
    public Collection<FeeEntity> findAllInRangeForPersonsIn(@Param("start") final YearMonth start,
            @Param("end") final YearMonth end, @Param("ids") final Collection<Long> ids, final Sort sort);

    @Query("""
            SELECT f
            FROM Fee f
               INNER JOIN Person p ON p.id = f.personId
            """)
    public Page<FeeEntity> findAllWithPerson(final Pageable pageable);

    /**
     * Finds the fee for the member in the date.
     *
     * @param personId
     *            person to filter by
     * @param date
     *            date to filter by
     * @return fee for the member in the date
     */
    public Optional<FeeEntity> findByPersonIdAndDate(final Long personId, final YearMonth date);

    /**
     * Finds the fee for the member in the date.
     *
     * @param memberNumber
     *            member to filter by
     * @param date
     *            date to filter by
     * @return fee for the member in the date
     */
    public Optional<FeeEntity> findByPersonNumberAndDate(final Long memberNumber, final YearMonth date);

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
