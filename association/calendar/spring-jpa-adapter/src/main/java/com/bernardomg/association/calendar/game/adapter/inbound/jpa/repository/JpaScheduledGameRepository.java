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

import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;

import com.bernardomg.association.calendar.activity.adapter.inbound.jpa.model.ActivityEntityConstants;
import com.bernardomg.association.calendar.adapter.inbound.jpa.model.CalendarInfoEntity;
import com.bernardomg.association.calendar.adapter.inbound.jpa.model.CalendarInfoRecurrence;
import com.bernardomg.association.calendar.adapter.inbound.jpa.model.CalendarStatusEntity;
import com.bernardomg.association.calendar.adapter.inbound.jpa.model.CalendarTypeEntity;
import com.bernardomg.association.calendar.adapter.inbound.jpa.model.RecurrenceStatusEntity;
import com.bernardomg.association.calendar.adapter.inbound.jpa.repository.CalendarStatusSpringRepository;
import com.bernardomg.association.calendar.adapter.inbound.jpa.repository.CalendarTypeSpringRepository;
import com.bernardomg.association.calendar.adapter.inbound.jpa.repository.RecurrenceStatusSpringRepository;
import com.bernardomg.association.calendar.domain.exception.MissingCalendarRecurrenceStatusException;
import com.bernardomg.association.calendar.domain.exception.MissingCalendarStatusException;
import com.bernardomg.association.calendar.domain.model.CalendarStatus;
import com.bernardomg.association.calendar.domain.model.Recurrence.RecurrenceStatus;
import com.bernardomg.association.calendar.game.adapter.inbound.jpa.model.ScheduledGameEntity;
import com.bernardomg.association.calendar.game.adapter.inbound.jpa.model.ScheduledGameEntityConstants;
import com.bernardomg.association.calendar.game.adapter.inbound.jpa.model.ScheduledGameEntityMapper;
import com.bernardomg.association.calendar.game.adapter.inbound.jpa.model.ScheduledGameProfileEntity;
import com.bernardomg.association.calendar.game.domain.exception.MissingScheduledGameProfileException;
import com.bernardomg.association.calendar.game.domain.exception.MissingScheduledGameSessionTypeException;
import com.bernardomg.association.calendar.game.domain.model.GameSessionType;
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

    private final CalendarStatusSpringRepository       calendarStatusSpringRepository;

    private final CalendarTypeSpringRepository         calendarTypeSpringRepository;

    private final RecurrenceStatusSpringRepository     recurrenceStatusSpringRepository;

    private final ScheduledGameProfileSpringRepository scheduledGameProfileSpringRepository;

    private final ScheduledGameSpringRepository        scheduledGameSpringRepository;

    public JpaScheduledGameRepository(final ScheduledGameSpringRepository scheduledGameSpringRepo,
            final ScheduledGameProfileSpringRepository scheduledGameProfileSpringRepo,
            final CalendarTypeSpringRepository calendarTypeSpringRepo,
            final CalendarStatusSpringRepository calendarStatusSpringRepo,
            final RecurrenceStatusSpringRepository recurrenceStatusSpringRepo) {
        super();

        scheduledGameSpringRepository = Objects.requireNonNull(scheduledGameSpringRepo);
        scheduledGameProfileSpringRepository = Objects.requireNonNull(scheduledGameProfileSpringRepo);
        calendarTypeSpringRepository = Objects.requireNonNull(calendarTypeSpringRepo);
        calendarStatusSpringRepository = Objects.requireNonNull(calendarStatusSpringRepo);
        recurrenceStatusSpringRepository = Objects.requireNonNull(recurrenceStatusSpringRepo);
    }

    @Override
    public final void delete(final long number) {
        final Optional<ScheduledGameEntity> scheduledGame;

        log.debug("Deleting scheduled game {}", number);

        // TODO: check the date is deleted
        scheduledGame = scheduledGameSpringRepository.findByNumber(number);
        if (scheduledGame.isPresent()) {
            scheduledGameSpringRepository.deleteById(scheduledGame.get()
                .getId());

            log.debug("Deleted activity {}", number);
        } else {
            // TODO: shouldn't throw an exception?
            log.debug("Couldn't delete scheduled game {} as it doesn't exist", number);
        }
    }

    @Override
    public final boolean exists(final long number) {
        final boolean exists;

        log.debug("Checking if scheduled game {} exists", number);

        exists = scheduledGameSpringRepository.existsByNumber(number);

        log.debug("Scheduled game {} exists: {}", number, exists);

        return exists;
    }

    @Override
    public final Page<ScheduledGame> findAll(final Pagination pagination, final Sorting sorting) {
        final org.springframework.data.domain.Page<ScheduledGameEntity> page;
        final org.springframework.data.domain.Page<ScheduledGame>       read;
        final Pageable                                                  pageable;

        log.debug("Finding scheduled games with pagination {} and sorting {}", pagination, sorting);

        pageable = SpringPagination.toPageable(pagination, sorting);
        page = scheduledGameSpringRepository.findAll(pageable);

        read = page.map(ScheduledGameEntityMapper::toDomain);

        log.debug("Found scheduled games {}", read);

        return SpringPagination.toPage(read);
    }

    @Override
    public final Optional<ScheduledGame> findOne(final Long number) {
        final Optional<ScheduledGame> activity;

        log.debug("Finding scheduled game with number {}", number);

        activity = scheduledGameSpringRepository.findByNumber(number)
            .map(ScheduledGameEntityMapper::toDomain);

        // TODO: shouldn't throw an exception if there is no data?

        log.debug("Found scheduled game with number {}: {}", number, activity);

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

        log.debug("Saving scheduled game {}", scheduledGame);

        entity = ScheduledGameEntityMapper.toEntity(scheduledGame);

        existing = scheduledGameSpringRepository.findByNumber(scheduledGame.number());
        if (existing.isPresent()) {
            entity.setId(existing.get()
                .getId());
        } else {
            number = scheduledGameSpringRepository.findNextNumber();
            entity.setNumber(number);
        }

        profile = scheduledGameProfileSpringRepository.getByNumber(scheduledGame.master());

        if (profile.isEmpty()) {
            log.error("Missing master {}", scheduledGame.master());
            throw new MissingScheduledGameProfileException(scheduledGame.master());
        }
        entity.setMaster(profile.get());

        setType(entity, scheduledGame.gameSessionType());
        setStatus(entity, CalendarStatus.PUBLISHED);
        if (entity.getRecurrence() != null) {
            setRecurrenceStatus(entity.getRecurrence(), RecurrenceStatus.ACTIVE);
        }

        created = scheduledGameSpringRepository.save(entity);

        saved = ScheduledGameEntityMapper.toDomain(created);

        log.debug("Saved scheduled game {}", saved);

        return saved;
    }

    private final void setRecurrenceStatus(final CalendarInfoRecurrence entity, final RecurrenceStatus status) {
        final RecurrenceStatusEntity statusEntity;

        statusEntity = recurrenceStatusSpringRepository.findByName(status)
            // TODO: use correct id
            .orElseThrow(() -> {
                log.error("Missing calendar recurrence status {}", status);
                return new MissingCalendarRecurrenceStatusException(ActivityEntityConstants.TYPE);
            });

        entity.setStatus(statusEntity);
    }

    private final void setStatus(final CalendarInfoEntity entity, final CalendarStatus status) {
        final CalendarStatusEntity statusEntity;

        statusEntity = calendarStatusSpringRepository.findByName(status)
            // TODO: use correct id
            .orElseThrow(() -> {
                log.error("Missing calendar status {}", status);
                return new MissingCalendarStatusException(ActivityEntityConstants.TYPE);
            });

        entity.setStatus(statusEntity);
    }

    private final void setType(final CalendarInfoEntity entity, final GameSessionType gameSessionType) {
        final long               typeNumber;
        final CalendarTypeEntity profileType;

        typeNumber = switch (gameSessionType) {
            case ONESHOT -> ScheduledGameEntityConstants.ONESHOT_TYPE;
            case CAMPAIGN -> ScheduledGameEntityConstants.CAMPAIGN_TYPE;
        };

        profileType = calendarTypeSpringRepository.findByNumber(typeNumber)
            .orElseThrow(() -> {
                log.error("Missing scheduled game session type {}", typeNumber);
                return new MissingScheduledGameSessionTypeException(typeNumber);
            });

        if (entity.getTypes() == null) {
            entity.setTypes(new HashSet<>());
        }

        entity.getTypes()
            .add(profileType);
    }

}
