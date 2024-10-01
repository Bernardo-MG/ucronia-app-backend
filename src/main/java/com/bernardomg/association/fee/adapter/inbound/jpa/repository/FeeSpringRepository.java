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
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bernardomg.association.fee.adapter.inbound.jpa.model.FeeEntity;
import com.bernardomg.association.fee.adapter.inbound.jpa.model.MemberFee;

public interface FeeSpringRepository extends JpaRepository<FeeEntity, Long> {

    public void deleteByPersonIdAndDate(final Long personId, final YearMonth date);

    public boolean existsByPersonIdAndDate(final Long personId, final YearMonth date);

    @Query("""
               SELECT f.id AS id, m.id AS personId, m.person.number AS personNumber,
                 m.person.firstName AS personFirstName,
                 m.person.lastName AS personLastName,
                 f.date AS date,
                 t.index AS transactionIndex, t.date AS paymentDate, CASE WHEN p.feeId IS NOT NULL THEN true ELSE false END AS paid
               FROM Member m
                 INNER JOIN Fee f ON m.id = f.personId
                 LEFT JOIN FeePayment p ON f.id = p.feeId
                 LEFT JOIN Transaction t ON p.transactionId = t.id
               WHERE m.person.number = :memberNumber
                 AND f.date in :feeDates
            """)
    public Collection<MemberFee> findAllByPersonNumberAndDateIn(@Param("memberNumber") final Long memberNumber,
            @Param("feeDates") final Collection<YearMonth> feeDates);

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

}
