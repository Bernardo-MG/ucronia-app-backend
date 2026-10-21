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

package com.bernardomg.association.key.usecase.service;

import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bernardomg.association.key.domain.exception.KeyAssignmentConflictException;
import com.bernardomg.association.key.domain.exception.MissingKeyAssignmentException;
import com.bernardomg.association.key.domain.exception.MissingKeyException;
import com.bernardomg.association.key.domain.model.KeyAssignment;
import com.bernardomg.association.key.domain.repository.KeyAssignmentRepository;
import com.bernardomg.pagination.domain.Page;
import com.bernardomg.pagination.domain.Pagination;
import com.bernardomg.pagination.domain.Sorting;

import jakarta.transaction.Transactional;

/**
 * Default key assignment service.
 */
@Transactional
public final class DefaultKeyAssignmentService implements KeyAssignmentService {

    private static final Logger           log = LoggerFactory.getLogger(DefaultKeyAssignmentService.class);

    private final KeyAssignmentRepository repository;

    public DefaultKeyAssignmentService(final KeyAssignmentRepository repository) {
        super();

        this.repository = Objects.requireNonNull(repository);
    }

    @Override
    public final KeyAssignment create(final long number, final long profileNumber) {
        final KeyAssignment existing;
        final KeyAssignment created;

        log.debug("Assigning key {} to member profile {}", number, profileNumber);

        existing = findKey(number);
        if (existing.member()
            .isPresent()) {
            throw new KeyAssignmentConflictException(number);
        }
        checkMemberKey(number, profileNumber);

        created = repository.save(number, profileNumber);

        log.debug("Created key assignment {}", created);

        return created;
    }

    @Override
    public final KeyAssignment delete(final long number) {
        final KeyAssignment existing;

        log.debug("Deleting assignment for key {}", number);

        existing = findKey(number);
        if (existing.member()
            .isEmpty()) {
            throw new MissingKeyAssignmentException(number);
        }

        repository.delete(number);

        log.debug("Deleted assignment for key {}", number);

        return existing;
    }

    @Override
    public final Page<KeyAssignment> getAssignments(final Pagination pagination, final Sorting sorting) {
        final Page<KeyAssignment> assignments;

        log.debug("Getting key assignments");

        assignments = repository.findAll(pagination, sorting);

        log.debug("Got key assignments: {}", assignments);

        return assignments;
    }

    @Override
    public final KeyAssignment update(final long number, final long profileNumber) {
        final KeyAssignment existing;
        final KeyAssignment updated;

        log.debug("Reassigning key {} to member profile {}", number, profileNumber);

        existing = findKey(number);
        if (existing.member()
            .isEmpty()) {
            throw new MissingKeyAssignmentException(number);
        }
        checkMemberKey(number, profileNumber);

        updated = repository.save(number, profileNumber);

        log.debug("Updated key assignment {}", updated);

        return updated;
    }

    private final void checkMemberKey(final long number, final long profileNumber) {
        final Optional<Long> existing;

        existing = repository.findMemberKey(profileNumber);
        if (existing.filter(key -> !Objects.equals(key, number))
            .isPresent()) {
            throw new KeyAssignmentConflictException(number);
        }
    }

    private final KeyAssignment findKey(final long number) {
        return repository.findOne(number)
            .orElseThrow(() -> new MissingKeyException(number));
    }

}
