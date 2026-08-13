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

package com.bernardomg.association.member.adapter.inbound.jpa.repository;

import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import com.bernardomg.association.member.adapter.inbound.jpa.model.KeyEntity;
import com.bernardomg.association.member.adapter.inbound.jpa.model.KeyEntityMapper;
import com.bernardomg.association.member.domain.model.Key;
import com.bernardomg.association.member.domain.repository.KeyRepository;
import com.bernardomg.pagination.domain.Page;
import com.bernardomg.pagination.domain.Pagination;
import com.bernardomg.pagination.domain.Sorting;
import com.bernardomg.pagination.springframework.SpringPagination;

@Transactional
public final class JpaKeyRepository implements KeyRepository {

    /**
     * Logger for the class.
     */
    private static final Logger       log = LoggerFactory.getLogger(JpaKeyRepository.class);

    private final KeySpringRepository keySpringRepository;

    public JpaKeyRepository(final KeySpringRepository keySpringRepository) {
        super();

        this.keySpringRepository = Objects.requireNonNull(keySpringRepository);
    }

    @Override
    public final void delete(final Long number) {
        log.debug("Deleting key {}", number);

        keySpringRepository.deleteByNumber(number);

        log.debug("Deleted member profile {}", number);
    }

    @Override
    public final boolean exists(final Long number) {
        final boolean exists;

        log.debug("Checking if key {} exists", number);

        exists = keySpringRepository.existsByNumber(number);

        log.debug("Key {} exists: {}", number, exists);

        return exists;
    }

    @Override
    public final Page<Key> findAll(final Pagination pagination, final Sorting sorting) {
        final org.springframework.data.domain.Page<Key> read;
        final Pageable                                  pageable;

        log.debug("Finding all the keys");

        pageable = SpringPagination.toPageable(pagination, sorting);
        read = keySpringRepository.findAll(pageable)
            .map(KeyEntityMapper::toDomain);

        log.debug("Found all keys");

        return SpringPagination.toPage(read);
    }

    @Override
    public final Optional<Key> findOne(final Long number) {
        final Optional<Key> key;

        log.trace("Finding key with number {}", number);

        key = keySpringRepository.findByNumber(number)
            .map(KeyEntityMapper::toDomain);

        log.trace("Found key with number {}: {}", number, key);

        return key;
    }

    @Override
    public final Key save(final Key key) {
        final Optional<KeyEntity> existing;
        final KeyEntity           entity;
        final Key                 created;

        log.debug("Saving key {}", key);

        existing = keySpringRepository.findByNumber(key.number());

        if (existing.isPresent()) {
            entity = existing.get();
        } else {
            entity = new KeyEntity();
            entity.setNumber(keySpringRepository.findNextNumber());
        }

        entity.setAvailable(key.available());
        entity.setDescription(key.description());

        created = KeyEntityMapper.toDomain(keySpringRepository.save(entity));

        log.debug("Saved key {}", key);

        return created;
    }

}
