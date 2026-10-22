/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2022-2025 Bernardo Martinez Garrido
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

package com.bernardomg.association.key.adapter.inbound.jpa.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bernardomg.association.key.adapter.inbound.jpa.model.KeyAssignmentEntity;

import jakarta.persistence.Tuple;

public interface KeyAssignmentSpringRepository extends JpaRepository<KeyAssignmentEntity, Long> {

    public void deleteByKeyNumber(final Long number);

    public void deleteByProfileNumber(final Long number);

    @Query("""
            SELECT k.number AS number, k.available AS available, k.description AS description,
                   p.number AS profileNumber, p.firstName AS firstName,
                   p.lastName AS lastName, p.nickname AS nickname
            FROM Key k
            LEFT JOIN KeyAssignment a ON a.key = k
            LEFT JOIN a.profile p
            WHERE k.number = :number
            """)
    public Optional<Tuple> findAssignmentByKeyNumber(@Param("number") final Long number);

    @Query(value = """
            SELECT k.number AS number, k.available AS available, k.description AS description,
                   p.number AS profileNumber, p.firstName AS firstName,
                   p.lastName AS lastName, p.nickname AS nickname
            FROM Key k
            LEFT JOIN KeyAssignment a ON a.key = k
            LEFT JOIN a.profile p
            """, countQuery = "SELECT COUNT(k) FROM Key k")
    public Page<Tuple> findAssignments(final Pageable pageable);

    public Optional<KeyAssignmentEntity> findByKeyNumber(final Long number);

    public Optional<KeyAssignmentEntity> findByProfileNumber(final Long number);

}
