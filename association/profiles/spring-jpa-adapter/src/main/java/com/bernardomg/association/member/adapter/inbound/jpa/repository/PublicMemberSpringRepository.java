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

import java.util.Collection;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bernardomg.association.member.adapter.inbound.jpa.model.PaidMembership;
import com.bernardomg.association.member.adapter.inbound.jpa.model.PublicMemberEntity;

public interface PublicMemberSpringRepository
        extends JpaRepository<PublicMemberEntity, Long>, JpaSpecificationExecutor<PublicMemberEntity> {

    public long countByActiveTrue();

    public long countByActiveTrueAndRenewTrue();

    public Optional<PublicMemberEntity> findByNumber(final Long number);

    public Optional<PublicMemberEntity> findByNumberAndActiveTrue(final Long number);

    // The fee schema permits only one fee per member and calendar month.
    @Query("""
            SELECT f.member.number AS number, MIN(f.month) AS joined, COUNT(f) AS totalMonths
            FROM Fee f
            WHERE f.member.number IN :numbers AND f.paid = TRUE
            GROUP BY f.member.number
            """)
    public Collection<PaidMembership> findPaidMembership(@Param("numbers") final Collection<Long> numbers);

}
