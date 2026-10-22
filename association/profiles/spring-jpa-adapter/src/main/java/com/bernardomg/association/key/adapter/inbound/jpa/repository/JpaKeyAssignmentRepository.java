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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.bernardomg.association.key.adapter.inbound.jpa.model.KeyAssignmentEntity;
import com.bernardomg.association.key.adapter.inbound.jpa.model.KeyAssignmentEntityMapper;
import com.bernardomg.association.key.adapter.inbound.jpa.model.KeyEntity;
import com.bernardomg.association.key.domain.exception.MissingKeyException;
import com.bernardomg.association.key.domain.model.KeyAssignment;
import com.bernardomg.association.key.domain.repository.KeyAssignmentRepository;
import com.bernardomg.association.profile.adapter.inbound.jpa.model.ProfileEntity;
import com.bernardomg.association.profile.adapter.inbound.jpa.repository.ProfileSpringRepository;
import com.bernardomg.association.profile.domain.exception.MissingProfileException;
import com.bernardomg.pagination.domain.Page;
import com.bernardomg.pagination.domain.Pagination;
import com.bernardomg.pagination.domain.Sorting;
import com.bernardomg.pagination.springframework.SpringPagination;

@Transactional
public final class JpaKeyAssignmentRepository implements KeyAssignmentRepository {

    /**
     * Logger for the class.
     */
    private static final Logger                 log = LoggerFactory.getLogger(JpaKeyAssignmentRepository.class);

    private final KeyAssignmentSpringRepository keyAssignmentSpringRepository;

    private final KeySpringRepository           keySpringRepository;

    private final ProfileSpringRepository       profileSpringRepository;

    public JpaKeyAssignmentRepository(final KeyAssignmentSpringRepository keyAssignmentSpringRepo,
            final KeySpringRepository keySpringRepo, final ProfileSpringRepository profileSpringRepo) {
        super();

        keyAssignmentSpringRepository = Objects.requireNonNull(keyAssignmentSpringRepo);
        keySpringRepository = Objects.requireNonNull(keySpringRepo);
        profileSpringRepository = Objects.requireNonNull(profileSpringRepo);
    }

    @Override
    public final void delete(final Long number) {
        log.debug("Deleting key assignment {}", number);

        keyAssignmentSpringRepository.deleteByKeyNumber(number);

        log.debug("Deleted key assignment {}", number);
    }

    @Override
    public final Page<KeyAssignment> findAll(final Pagination pagination, final Sorting sorting) {
        final org.springframework.data.domain.Page<KeyAssignment> read;

        log.debug("Finding all the key assignments");

        read = keyAssignmentSpringRepository.findAll(SpringPagination.toPageable(pagination, sorting))
            .map(KeyAssignmentEntityMapper::toDomain);

        log.debug("Found all the key assignments");

        return SpringPagination.toPage(read);
    }

    @Override
    public final Optional<KeyAssignment> findOne(final Long number) {
        final Optional<KeyAssignment> keyAssignment;

        log.trace("Finding key assignment for key {}", number);

        keyAssignment = keyAssignmentSpringRepository.findByKeyNumber(number)
            .map(KeyAssignmentEntityMapper::toDomain);

        log.trace("Found key assignment for key {}", number);

        return keyAssignment;
    }

    @Override
    public final Optional<Long> findProfileKey(final Long profileNumber) {
        final Optional<Long> key;

        log.debug("Finding profile key for profile {}", profileNumber);

        if (!profileSpringRepository.existsByNumber(profileNumber)) {
            log.error("Missing profile {}", profileNumber);
            throw new MissingProfileException(profileNumber);
        }

        key = keyAssignmentSpringRepository.findByProfileNumber(profileNumber)
            .map(KeyAssignmentEntity::getKey)
            .map(KeyEntity::getNumber);

        log.debug("Found profile key for profile {}", profileNumber);

        return key;
    }

    @Override
    public final KeyAssignment save(final Long number, final Long profileNumber) {
        final KeyEntity           key;
        final ProfileEntity       profile;
        final KeyAssignmentEntity entity;
        final KeyAssignment       keyAssignment;

        log.debug("Saving key asignment for key {} and profile {}", number, profileNumber);

        key = keySpringRepository.findByNumber(number)
            .orElseThrow(() -> {
                log.error("Missing key {}", number);
                return new MissingKeyException(number);
            });
        profile = profileSpringRepository.findByNumber(profileNumber)
            .orElseThrow(() -> {
                log.error("Missing profile {}", number);
                return new MissingProfileException(number);
            });

        entity = keyAssignmentSpringRepository.findByKeyNumber(number)
            .orElseGet(KeyAssignmentEntity::new);

        entity.setId(key.getId());
        entity.setKey(key);
        entity.setProfile(profile);

        keyAssignment = KeyAssignmentEntityMapper.toDomain(keyAssignmentSpringRepository.save(entity));

        log.debug("Saved key asignment for key {} and profile {}", number, profileNumber);

        return keyAssignment;
    }

}
