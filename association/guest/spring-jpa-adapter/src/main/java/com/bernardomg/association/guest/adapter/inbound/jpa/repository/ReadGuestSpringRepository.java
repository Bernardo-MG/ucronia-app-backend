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

package com.bernardomg.association.guest.adapter.inbound.jpa.repository;

import java.util.Collection;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bernardomg.association.guest.adapter.inbound.jpa.model.ReadGuestEntity;

public interface ReadGuestSpringRepository
        extends JpaRepository<ReadGuestEntity, Long>, JpaSpecificationExecutor<ReadGuestEntity> {

    @Modifying
    @Query("""
            DELETE FROM ReadGuestEntity g
            WHERE g.number = :number
            """)
    public void deleteByNumber(@Param("number") final Long number);

    public boolean existsByIdentifier(final String identifier);

    @Query("""
            SELECT CASE WHEN COUNT(m) > 0 THEN TRUE ELSE FALSE END AS exists
            FROM ReadGuestEntity g
            WHERE g.number != :number
              AND g.identifier = :identifier
            """)
    public boolean existsByIdentifierForAnother(@Param("number") final Long number,
            @Param("identifier") final String identifier);

    @Query("""
            SELECT CASE WHEN COUNT(m) > 0 THEN TRUE ELSE FALSE END AS exists
            FROM ReadGuestEntity g
            WHERE g.number = :number
            """)
    public boolean existsByNumber(@Param("number") final Long number);

    public Collection<ReadGuestEntity> findAllByRenewTrue();

    @Query("""
            SELECT m
            FROM ReadGuestEntity g
            WHERE g.active != g.renew
            """)
    public Collection<ReadGuestEntity> findAllWithRenewalMismatch();

    @Query("""
            SELECT m
            FROM ReadGuestEntity g
            WHERE g.number = :number
            """)
    public Optional<ReadGuestEntity> findByNumber(@Param("number") final Long number);

    @Query("""
            SELECT m
            FROM ReadGuestEntity g
            WHERE g.id = :id
            """)
    public Optional<ReadGuestEntity> findByProfileId(@Param("id") final Long id);

    @Query("SELECT COALESCE(MAX(m.number), 0) + 1 FROM ReadGuestEntity g")
    public Long findNextNumber();

    @Query("""
            SELECT g.active
            FROM ReadGuestEntity g
            WHERE g.number = :number
            """)
    public Boolean isActive(@Param("number") final Long number);

}
