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

package com.bernardomg.association.calendar.game.adapter.inbound.jpa.repository;

import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;

import com.bernardomg.association.calendar.adapter.inbound.jpa.repository.GameTableSpringRepository;
import com.bernardomg.association.calendar.game.adapter.inbound.jpa.model.GameTableEntity;
import com.bernardomg.association.calendar.game.adapter.inbound.jpa.model.GameTableEntityMapper;
import com.bernardomg.association.calendar.game.domain.model.GameTable;
import com.bernardomg.association.calendar.game.domain.repository.GameTableRepository;
import com.bernardomg.pagination.domain.Page;
import com.bernardomg.pagination.domain.Pagination;
import com.bernardomg.pagination.domain.Sorting;
import com.bernardomg.pagination.springframework.SpringPagination;

public final class JpaGameTableRepository implements GameTableRepository {

    private static final Logger             log = LoggerFactory.getLogger(JpaGameTableRepository.class);

    private final GameTableSpringRepository gameTableSpringRepository;

    public JpaGameTableRepository(final GameTableSpringRepository gameTableSpringRepo) {
        super();

        gameTableSpringRepository = Objects.requireNonNull(gameTableSpringRepo);
    }

    @Override
    public final void delete(final long number) {
        final Optional<GameTableEntity> table;

        log.debug("Deleting game table {}", number);

        table = gameTableSpringRepository.findByNumber(number);
        if (table.isPresent()) {
            gameTableSpringRepository.deleteById(table.get()
                .getId());
            log.debug("Deleted game table {}", number);
        } else {
            log.debug("Couldn't delete game table {} as it doesn't exist", number);
        }
    }

    @Override
    public final boolean exists(final long number) {
        final boolean exists;

        log.debug("Checking if game table {} exists", number);
        exists = gameTableSpringRepository.existsByNumber(number);
        log.debug("Game table {} exists: {}", number, exists);

        return exists;
    }

    @Override
    public final Page<GameTable> findAll(final Pagination pagination, final Sorting sorting) {
        final org.springframework.data.domain.Page<GameTableEntity> page;
        final org.springframework.data.domain.Page<GameTable>       read;
        final Pageable                                              pageable;

        log.debug("Finding game tables with pagination {} and sorting {}", pagination, sorting);

        pageable = SpringPagination.toPageable(pagination, sorting);
        page = gameTableSpringRepository.findAll(pageable);
        read = page.map(GameTableEntityMapper::toDomain);

        log.debug("Found game tables {}", read);

        return SpringPagination.toPage(read);
    }

    @Override
    public final Optional<GameTable> findOne(final Long number) {
        final Optional<GameTable> table;

        log.debug("Finding game table with number {}", number);
        table = gameTableSpringRepository.findByNumber(number)
            .map(GameTableEntityMapper::toDomain);
        log.debug("Found game table with number {}: {}", number, table);

        return table;
    }

    @Override
    public final GameTable save(final GameTable gameTable) {
        final Optional<GameTableEntity> existing;
        final GameTableEntity           entity;
        final GameTableEntity           created;
        final GameTable                 saved;

        log.debug("Saving game table {}", gameTable);

        entity = GameTableEntityMapper.toEntity(gameTable);
        existing = gameTableSpringRepository.findByNumber(gameTable.number());
        if (existing.isPresent()) {
            entity.setId(existing.get()
                .getId());
        } else {
            entity.setNumber(gameTableSpringRepository.findNextNumber());
        }

        created = gameTableSpringRepository.save(entity);
        saved = GameTableEntityMapper.toDomain(created);

        log.debug("Saved game table {}", saved);

        return saved;
    }

}
