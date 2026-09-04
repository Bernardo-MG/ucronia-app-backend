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

package com.bernardomg.association.calendar.game.usecase.service;

import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bernardomg.association.calendar.domain.exception.MissingGameTableException;
import com.bernardomg.association.calendar.game.domain.model.GameTable;
import com.bernardomg.association.calendar.game.domain.repository.GameTableRepository;
import com.bernardomg.pagination.domain.Page;
import com.bernardomg.pagination.domain.Pagination;
import com.bernardomg.pagination.domain.Sorting;

import jakarta.transaction.Transactional;

/**
 * Default implementation of the game table service.
 */
@Transactional
public final class DefaultGameTableService implements GameTableService {

    private static final Logger       log = LoggerFactory.getLogger(DefaultGameTableService.class);

    private final GameTableRepository gameTableRepository;

    public DefaultGameTableService(final GameTableRepository gameTableRepo) {
        super();

        gameTableRepository = Objects.requireNonNull(gameTableRepo);
    }

    @Override
    public final GameTable create(final GameTable gameTable) {
        final GameTable saved;

        log.debug("Creating game table {}", gameTable);

        saved = gameTableRepository.save(gameTable);

        log.debug("Created game table {}", saved);

        return saved;
    }

    @Override
    public final GameTable delete(final long number) {
        final GameTable gameTable;

        log.debug("Deleting game table {}", number);

        gameTable = gameTableRepository.findOne(number)
            .orElseThrow(() -> {
                log.error("Missing game table {}", number);
                return new MissingGameTableException(number);
            });

        gameTableRepository.delete(number);

        log.debug("Deleted game table {}", number);

        return gameTable;
    }

    @Override
    public final Page<GameTable> getAll(final Pagination pagination, final Sorting sorting) {
        final Page<GameTable> gameTables;

        log.info("Getting all game tables with pagination {} and sorting {}", pagination, sorting);

        gameTables = gameTableRepository.findAll(pagination, sorting);

        log.debug("Got all game tables with pagination {} and sorting {}: {}", pagination, sorting, gameTables);

        return gameTables;
    }

    @Override
    public final Optional<GameTable> getOne(final long number) {
        final Optional<GameTable> gameTable;

        log.debug("Reading game table with number {}", number);

        gameTable = gameTableRepository.findOne(number);
        if (gameTable.isEmpty()) {
            log.error("Missing game table {}", number);
            throw new MissingGameTableException(number);
        }

        log.debug("Read game table with number {}: {}", number, gameTable);

        return gameTable;
    }

    @Override
    public final GameTable update(final GameTable gameTable) {
        final boolean   exists;
        final GameTable updated;

        log.debug("Updating game table with number {} using data {}", gameTable.number(), gameTable);

        exists = gameTableRepository.exists(gameTable.number());
        if (!exists) {
            log.error("Missing game table {}", gameTable.number());
            throw new MissingGameTableException(gameTable.number());
        }

        updated = gameTableRepository.save(gameTable);

        log.debug("Updated game table with number {}: {}", gameTable.number(), updated);

        return updated;
    }

}
