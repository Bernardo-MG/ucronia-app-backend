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

package com.bernardomg.association.sponsor.adapter.inbound.jpa.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bernardomg.association.sponsor.adapter.inbound.jpa.model.SponsorEntity;

public interface SponsorSpringRepository
        extends JpaRepository<SponsorEntity, Long>, JpaSpecificationExecutor<SponsorEntity> {

    @Modifying
    @Query("""
            DELETE FROM Sponsor s
            WHERE s.id IN (
                SELECT s2.id
                FROM Sponsor s2
                JOIN s2.profile p
                WHERE p.number = :number
            )
            """)
    public void deleteByNumber(@Param("number") final Long number);

    @Query("""
            SELECT CASE WHEN COUNT(s) > 0 THEN TRUE ELSE FALSE END AS exists
            FROM Sponsor s
              JOIN s.profile p
            WHERE p.number = :number
            """)
    public boolean existsByNumber(@Param("number") final Long number);

    @Query("""
            SELECT s
            FROM Sponsor s
              JOIN s.profile p
            WHERE p.number = :number
            """)
    public Optional<SponsorEntity> findByNumber(@Param("number") final Long number);

    @Query("SELECT COALESCE(MAX(p.number), 0) + 1 FROM Profile p")
    public Long findNextNumber();

}
