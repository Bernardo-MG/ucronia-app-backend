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

import com.bernardomg.association.calendar.domain.event.CalendarInfoPublishedEvent;
import com.bernardomg.association.calendar.game.domain.exception.MissingScheduledGameException;
import com.bernardomg.association.calendar.game.domain.model.ScheduledGame;
import com.bernardomg.association.calendar.game.domain.repository.ScheduledGameRepository;
import com.bernardomg.association.calendar.game.usecase.validation.ScheduledGamePositivePlayersRule;
import com.bernardomg.association.calendar.game.usecase.validation.ScheduledGamePositiveRecurrenceRule;
import com.bernardomg.event.emitter.EventEmitter;
import com.bernardomg.pagination.domain.Page;
import com.bernardomg.pagination.domain.Pagination;
import com.bernardomg.pagination.domain.Sorting;
import com.bernardomg.validation.validator.FieldRuleValidator;
import com.bernardomg.validation.validator.Validator;

import jakarta.transaction.Transactional;

/**
 * Default implementation of the scheduled game service.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@Transactional
public final class DefaultScheduledGameService implements ScheduledGameService {

    /**
     * Logger for the class.
     */
    private static final Logger            log = LoggerFactory.getLogger(DefaultScheduledGameService.class);

    private final EventEmitter             eventEmitter;

    private final ScheduledGameRepository  scheduledGameRepository;

    private final Validator<ScheduledGame> validatorCreate;

    private final Validator<ScheduledGame> validatorUpdate;

    public DefaultScheduledGameService(final ScheduledGameRepository scheduledGameRepo,
            final EventEmitter evntEmitter) {
        super();

        scheduledGameRepository = Objects.requireNonNull(scheduledGameRepo);
        eventEmitter = Objects.requireNonNull(evntEmitter);

        validatorCreate = new FieldRuleValidator<>(new ScheduledGamePositivePlayersRule(),
            new ScheduledGamePositiveRecurrenceRule());
        validatorUpdate = new FieldRuleValidator<>(new ScheduledGamePositivePlayersRule(),
            new ScheduledGamePositiveRecurrenceRule());
    }

    @Override
    public final ScheduledGame create(final ScheduledGame scheduledGame) {
        final ScheduledGame saved;

        log.debug("Creating scheduled game {}", scheduledGame);

        validatorCreate.validate(scheduledGame);

        saved = scheduledGameRepository.save(scheduledGame);

        log.debug("Created scheduled game {}", saved);

        return saved;
    }

    @Override
    public final ScheduledGame delete(final long number) {
        final ScheduledGame scheduledGame;

        log.debug("Deleting scheduled game {}", number);

        scheduledGame = scheduledGameRepository.findOne(number)
            .orElseThrow(() -> {
                log.error("Missing scheduled game {}", number);
                throw new MissingScheduledGameException(number);
            });

        scheduledGameRepository.delete(number);

        log.debug("Deleted scheduled game {}", number);

        return scheduledGame;
    }

    @Override
    public final Page<ScheduledGame> getAll(final Pagination pagination, final Sorting sorting) {
        final Page<ScheduledGame> scheduledGames;

        log.info("Getting all scheduled games with pagination {} and sorting {}", pagination, sorting);

        scheduledGames = scheduledGameRepository.findAll(pagination, sorting);

        log.debug("Got all scheduled games with pagination {} and sorting {}: {}", pagination, sorting, scheduledGames);

        return scheduledGames;
    }

    @Override
    public final Optional<ScheduledGame> getOne(final long number) {
        final Optional<ScheduledGame> scheduledGame;

        log.debug("Reading scheduled game with number {}", number);

        scheduledGame = scheduledGameRepository.findOne(number);
        if (scheduledGame.isEmpty()) {
            log.error("Missing scheduled game {}", number);
            throw new MissingScheduledGameException(number);
        }

        log.debug("Read scheduled game with number {}: {}", number, scheduledGame);

        return scheduledGame;
    }

    @Override
    public final ScheduledGame publish(final long number) {
        final Optional<ScheduledGame> scheduledGame;
        final ScheduledGame           toPublish;
        final ScheduledGame           published;

        log.debug("Publishing scheduled game with number {}", number);

        scheduledGame = scheduledGameRepository.findOne(number);
        if (scheduledGame.isEmpty()) {
            log.error("Missing scheduled game {}", number);
            throw new MissingScheduledGameException(number);
        }

        toPublish = scheduledGame.map(ScheduledGame::publish)
            .get();
        published = scheduledGameRepository.save(toPublish);

        // TODO: send a source
        eventEmitter.emit(new CalendarInfoPublishedEvent(null, published.number()));

        return published;
    }

    @Override
    public final ScheduledGame update(final ScheduledGame scheduledGame) {
        final boolean       exists;
        final ScheduledGame updated;

        log.debug("Updating scheduled game with number {} using data {}", scheduledGame.number(), scheduledGame);

        exists = scheduledGameRepository.exists(scheduledGame.number());
        if (!exists) {
            log.error("Missing scheduled game {}", scheduledGame.number());
            throw new MissingScheduledGameException(scheduledGame.number());
        }

        validatorUpdate.validate(scheduledGame);

        updated = scheduledGameRepository.save(scheduledGame);

        log.debug("Updated scheduled game with number {}: {}", scheduledGame.number(), updated);

        return updated;
    }

}
