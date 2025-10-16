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

package com.bernardomg.association.library.gamesystem.adapter.inbound.jpa.repository;

import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bernardomg.association.library.gamesystem.adapter.inbound.jpa.model.GameSystemEntity;
import com.bernardomg.association.library.gamesystem.adapter.inbound.jpa.model.GameSystemEntityMapper;
import com.bernardomg.association.library.gamesystem.domain.model.GameSystem;
import com.bernardomg.association.library.gamesystem.domain.repository.GameSystemRepository;
import com.bernardomg.data.domain.Page;
import com.bernardomg.data.domain.Pagination;
import com.bernardomg.data.domain.Sorting;
import com.bernardomg.data.springframework.SpringPagination;

@Repository
@Transactional
public final class JpaGameSystemRepository implements GameSystemRepository {

    /**
     * Logger for the class.
     */
    private static final Logger              log = LoggerFactory.getLogger(JpaGameSystemRepository.class);

    private final GameSystemSpringRepository gameSystemSpringRepository;

    public JpaGameSystemRepository(final GameSystemSpringRepository gameSystemRepo) {
        super();

        gameSystemSpringRepository = Objects.requireNonNull(gameSystemRepo);
    }

    @Override
    public final void delete(final long number) {
        log.debug("Deleting game system {}", number);

        gameSystemSpringRepository.deleteByNumber(number);

        log.debug("Deleted game system {}", number);
    }

    @Override
    public final boolean exists(final long number) {
        final boolean exists;

        log.debug("Checking if game system {} exists", number);

        exists = gameSystemSpringRepository.existsByNumber(number);

        log.debug("Game system {} exists: {}", number, exists);

        return exists;
    }

    @Override
    public final boolean existsByName(final String name) {
        final boolean exists;

        log.debug("Checking if game system {} exists", name);

        exists = gameSystemSpringRepository.existsByName(name);

        log.debug("Game system {} exists: {}", name, exists);

        return exists;
    }

    @Override
    public final boolean existsByNameForAnother(final String name, final long number) {
        final boolean exists;

        log.debug("Checking if game system {} exists for a game system distinct from {}", name, number);

        exists = gameSystemSpringRepository.existsByNotNumberAndName(number, name);

        log.debug("Game system {} exists for a game system distinct from {}: {}", name, number, exists);

        return exists;
    }

    @Override
    public final Page<GameSystem> findAll(final Pagination pagination, final Sorting sorting) {
        final org.springframework.data.domain.Page<GameSystem> read;
        final Pageable                                         pageable;

        log.debug("Finding game systems with pagination {} and sorting {}", pagination, sorting);

        pageable = SpringPagination.toPageable(pagination, sorting);
        read = gameSystemSpringRepository.findAll(pageable)
            .map(GameSystemEntityMapper::toDomain);

        log.debug("Found game systems {}", read);

        return SpringPagination.toPage(read);
    }

    @Override
    public final long findNextNumber() {
        final long number;

        log.debug("Finding next number for the game systems");

        number = gameSystemSpringRepository.findNextNumber();

        log.debug("Found next number for the game systems: {}", number);

        return number;
    }

    @Override
    public final Optional<GameSystem> findOne(final long number) {
        final Optional<GameSystem> gameSystem;

        log.debug("Finding game system with name {}", number);

        gameSystem = gameSystemSpringRepository.findByNumber(number)
            .map(GameSystemEntityMapper::toDomain);

        log.debug("Found game system with name {}: {}", number, gameSystem);

        return gameSystem;
    }

    @Override
    public final GameSystem save(final GameSystem gameSystem) {
        final Optional<GameSystemEntity> existing;
        final GameSystemEntity           entity;
        final GameSystemEntity           created;
        final GameSystem                 saved;

        log.debug("Saving game system {}", gameSystem);

        entity = GameSystemEntityMapper.toEntity(gameSystem);

        existing = gameSystemSpringRepository.findByNumber(gameSystem.number());
        if (existing.isPresent()) {
            entity.setId(existing.get()
                .getId());
        }

        created = gameSystemSpringRepository.save(entity);
        saved = GameSystemEntityMapper.toDomain(created);

        log.debug("Saved game system {}", saved);

        return saved;
    }

}
