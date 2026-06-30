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

import com.bernardomg.association.calendar.game.adapter.inbound.jpa.model.ScheduledGameEntity;
import com.bernardomg.association.calendar.game.adapter.inbound.jpa.model.ScheduledGameEntityMapper;
import com.bernardomg.association.calendar.game.adapter.inbound.jpa.model.ScheduledGameProfileEntity;
import com.bernardomg.association.calendar.game.domain.exception.MissingScheduledGameProfileException;
import com.bernardomg.association.calendar.game.domain.model.ScheduledGame;
import com.bernardomg.association.calendar.game.domain.repository.ScheduledGameRepository;
import com.bernardomg.pagination.domain.Page;
import com.bernardomg.pagination.domain.Pagination;
import com.bernardomg.pagination.domain.Sorting;
import com.bernardomg.pagination.springframework.SpringPagination;

import jakarta.transaction.Transactional;

@Transactional
public final class JpaScheduledGameRepository implements ScheduledGameRepository {

    /**
     * Logger for the class.
     */
    private static final Logger                        log = LoggerFactory.getLogger(JpaScheduledGameRepository.class);

    private final ScheduledGameProfileSpringRepository scheduledGameProfileSpringRepository;

    private final ScheduledGameSpringRepository        scheduledGameSpringRepository;

    public JpaScheduledGameRepository(final ScheduledGameSpringRepository scheduledGameSpringRepo,
            final ScheduledGameProfileSpringRepository scheduledGameProfileSpringRepo) {
        super();

        scheduledGameSpringRepository = Objects.requireNonNull(scheduledGameSpringRepo);
        scheduledGameProfileSpringRepository = Objects.requireNonNull(scheduledGameProfileSpringRepo);
    }

    @Override
    public final void delete(final long number) {
        final Optional<ScheduledGameEntity> scheduledGame;

        log.debug("Deleting activity {}", number);

        // TODO: check the date is deleted
        scheduledGame = scheduledGameSpringRepository.findByNumber(number);
        if (scheduledGame.isPresent()) {
            scheduledGameSpringRepository.deleteById(scheduledGame.get()
                .getId());

            log.debug("Deleted activity {}", number);
        } else {
            // TODO: shouldn't throw an exception?
            log.debug("Couldn't delete activity {} as it doesn't exist", number);
        }
    }

    @Override
    public final boolean exists(final long number) {
        final boolean exists;

        log.debug("Checking if activity {} exists", number);

        exists = scheduledGameSpringRepository.existsByNumber(number);

        log.debug("Activity {} exists: {}", number, exists);

        return exists;
    }

    @Override
    public final Page<ScheduledGame> findAll(final Pagination pagination, final Sorting sorting) {
        final org.springframework.data.domain.Page<ScheduledGameEntity> page;
        final org.springframework.data.domain.Page<ScheduledGame>       read;
        final Pageable                                                  pageable;

        log.debug("Finding activities with pagination {} and sorting {}", pagination, sorting);

        pageable = SpringPagination.toPageable(pagination, sorting);
        page = scheduledGameSpringRepository.findAll(pageable);

        read = page.map(ScheduledGameEntityMapper::toDomain);

        log.debug("Found activities {}", read);

        return SpringPagination.toPage(read);
    }

    @Override
    public final Optional<ScheduledGame> findOne(final Long number) {
        final Optional<ScheduledGame> activity;

        log.debug("Finding activity with number {}", number);

        activity = scheduledGameSpringRepository.findByNumber(number)
            .map(ScheduledGameEntityMapper::toDomain);

        // TODO: shouldn't throw an exception if there is no data?

        log.debug("Found activity with number {}: {}", number, activity);

        return activity;
    }

    @Override
    public final ScheduledGame save(final ScheduledGame scheduledGame) {
        final Optional<ScheduledGameEntity>        existing;
        final ScheduledGameEntity                  entity;
        final ScheduledGameEntity                  created;
        final ScheduledGame                        saved;
        final Long                                 number;
        final Optional<ScheduledGameProfileEntity> profile;

        log.debug("Saving activity {}", scheduledGame);

        entity = ScheduledGameEntityMapper.toEntity(scheduledGame);

        existing = scheduledGameSpringRepository.findByNumber(scheduledGame.number());
        if (existing.isPresent()) {
            entity.setId(existing.get()
                .getId());
        } else {
            number = scheduledGameSpringRepository.findNextNumber();
            entity.setNumber(number);
        }

        profile = scheduledGameProfileSpringRepository.getByNumber(scheduledGame.master()
            .number());

        if (profile.isEmpty()) {
            throw new MissingScheduledGameProfileException(scheduledGame.master()
                .number());
        }
        entity.setMaster(profile.get());

        created = scheduledGameSpringRepository.save(entity);
        saved = ScheduledGameEntityMapper.toDomain(created);

        log.debug("Saved activity {}", saved);

        return saved;
    }

}
