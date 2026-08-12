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

package com.bernardomg.association.member.usecase.service;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bernardomg.association.member.domain.exception.MissingKeyException;
import com.bernardomg.association.member.domain.model.Key;
import com.bernardomg.association.member.domain.repository.KeyRepository;

import jakarta.transaction.Transactional;

/**
 * Default key CRUD service.
 */
@Transactional
public final class DefaultKeyService implements KeyService {

    private static final Logger log = LoggerFactory.getLogger(DefaultKeyService.class);

    private final KeyRepository repository;

    public DefaultKeyService(final KeyRepository repository) {
        super();

        this.repository = Objects.requireNonNull(repository);
    }

    @Override
    public Key create(final Key key) {
        final Key toCreate;
        final Key saved;

        log.debug("Creating key {}", key);

        toCreate = new Key(key.number(), key.missing(), key.description());
        saved = repository.save(toCreate);

        log.debug("Created key {}", saved);

        return saved;
    }

    @Override
    public Key delete(final long number) {
        final Key key;

        log.debug("Deleting key {}", number);

        key = repository.findOne(number)
            .orElseThrow(() -> {
                log.error("Missing key {}", number);
                throw new MissingKeyException(number);
            });

        repository.delete(number);

        log.debug("Deleted key {}", number);

        return key;
    }

    @Override
    public Collection<Key> getAll() {
        final Collection<Key> keys;

        log.debug("Getting all keys");

        keys = repository.findAll();

        log.debug("Got all keys: {}", keys);

        return keys;
    }

    @Override
    public Optional<Key> getOne(final long number) {
        final Optional<Key> key;

        log.debug("Reading key {}", number);

        key = repository.findOne(number);
        if (key.isEmpty()) {
            log.error("Missing key {}", number);
            throw new MissingKeyException(number);
        }

        log.debug("Read key {}: {}", number, key);

        return key;
    }

    @Override
    public Key update(final Key key) {
        final boolean exists;
        final Key     toUpdate;
        final Key     updated;

        log.debug("Updating key with number {} using data {}", key.number(), key);

        exists = repository.exists(key.number());
        if (!exists) {
            log.error("Missing key {}", key.number());
            throw new MissingKeyException(key.number());
        }

        toUpdate = new Key(key.number(), key.missing(), key.description());
        updated = repository.save(toUpdate);

        log.debug("Updated key with number {}: {}", key.number(), updated);

        return updated;
    }

}
