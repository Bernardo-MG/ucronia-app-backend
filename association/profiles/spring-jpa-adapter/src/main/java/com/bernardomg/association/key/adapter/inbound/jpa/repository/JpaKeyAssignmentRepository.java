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

import java.util.Objects;
import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;

import com.bernardomg.association.key.adapter.inbound.jpa.model.KeyEntity;
import com.bernardomg.association.key.adapter.inbound.jpa.model.KeyEntityMapper;
import com.bernardomg.association.key.domain.exception.MissingKeyException;
import com.bernardomg.association.key.domain.model.KeyAssignment;
import com.bernardomg.association.key.domain.repository.KeyAssignmentRepository;
import com.bernardomg.association.member.adapter.inbound.jpa.model.MemberEntity;
import com.bernardomg.association.member.adapter.inbound.jpa.repository.MemberSpringRepository;
import com.bernardomg.association.member.domain.exception.MissingMemberException;
import com.bernardomg.pagination.domain.Page;
import com.bernardomg.pagination.domain.Pagination;
import com.bernardomg.pagination.domain.Sorting;
import com.bernardomg.pagination.springframework.SpringPagination;

@Transactional
public final class JpaKeyAssignmentRepository implements KeyAssignmentRepository {

    private final KeySpringRepository    keySpringRepository;

    private final MemberSpringRepository memberSpringRepository;

    public JpaKeyAssignmentRepository(final KeySpringRepository keySpringRepo,
            final MemberSpringRepository memberSpringRepo) {
        super();

        keySpringRepository = Objects.requireNonNull(keySpringRepo);
        memberSpringRepository = Objects.requireNonNull(memberSpringRepo);
    }

    @Override
    public final void delete(final Long number) {
        release(number);
    }

    @Override
    public final Page<KeyAssignment> findAll(final Pagination pagination, final Sorting sorting) {
        final org.springframework.data.domain.Page<KeyAssignment> assignments;

        assignments = keySpringRepository.findAssignments(SpringPagination.toPageable(pagination, sorting))
            .map(KeyEntityMapper::toDomain);

        return SpringPagination.toPage(assignments);
    }

    @Override
    public final Optional<Long> findMemberKey(final Long profileNumber) {
        final MemberEntity member;

        member = memberSpringRepository.findByNumber(profileNumber)
            .orElseThrow(() -> new MissingMemberException(profileNumber));

        return Optional.ofNullable(member.getKey())
            .map(KeyEntity::getNumber);
    }

    @Override
    public final Optional<KeyAssignment> findOne(final Long number) {
        // Serialize changes to an assignment, including the unassigned state.
        return keySpringRepository.findByNumber(number)
            .map(key -> read(number));
    }

    @Override
    public final KeyAssignment save(final Long number, final Long profileNumber) {
        final KeyEntity    key;
        final MemberEntity member;

        key = keySpringRepository.findByNumber(number)
            .orElseThrow(() -> new MissingKeyException(number));
        member = memberSpringRepository.findByNumber(profileNumber)
            .orElseThrow(() -> new MissingMemberException(profileNumber));

        // Release and flush before reassigning the unique key relationship.
        release(number);
        member.setKey(key);
        memberSpringRepository.saveAndFlush(member);

        return read(number);
    }

    private final KeyAssignment read(final Long number) {
        return keySpringRepository.findAssignmentByNumber(number)
            .map(KeyEntityMapper::toDomain)
            .orElseThrow(() -> new MissingKeyException(number));
    }

    private final void release(final Long number) {
        final Optional<MemberEntity> assigned;

        assigned = memberSpringRepository.findByKeyNumber(number);
        if (assigned.isPresent()) {
            assigned.get()
                .setKey(null);
            memberSpringRepository.saveAndFlush(assigned.get());
        }
    }

}
