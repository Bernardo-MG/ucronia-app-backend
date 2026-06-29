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

import com.bernardomg.association.calendar.game.domain.exception.MissingGameSessionInfoException;
import com.bernardomg.association.calendar.game.domain.model.GameSessionInfo;
import com.bernardomg.association.calendar.game.domain.repository.GameSessionInfoRepository;
import com.bernardomg.pagination.domain.Page;
import com.bernardomg.pagination.domain.Pagination;
import com.bernardomg.pagination.domain.Sorting;
import com.bernardomg.validation.validator.FieldRuleValidator;
import com.bernardomg.validation.validator.Validator;

import jakarta.transaction.Transactional;

/**
 * Default implementation of the game session info service.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@Transactional
public final class DefaultGameSessionInfoService implements GameSessionInfoService {

    /**
     * Logger for the class.
     */
    private static final Logger              log = LoggerFactory.getLogger(DefaultGameSessionInfoService.class);

    private final GameSessionInfoRepository  gameSessionInfoRepository;

    private final Validator<GameSessionInfo> validatorCreate;

    private final Validator<GameSessionInfo> validatorUpdate;

    public DefaultGameSessionInfoService(final GameSessionInfoRepository gameSessionInfoRepo) {
        super();

        gameSessionInfoRepository = Objects.requireNonNull(gameSessionInfoRepo);

        validatorCreate = new FieldRuleValidator<>();
        validatorUpdate = new FieldRuleValidator<>();
    }

    @Override
    public final GameSessionInfo create(final GameSessionInfo gameSessionInfo) {
        final GameSessionInfo saved;

        log.debug("Creating game session info {}", gameSessionInfo);

        validatorCreate.validate(gameSessionInfo);

        saved = gameSessionInfoRepository.save(gameSessionInfo);

        log.debug("Created game session info {}", saved);

        return saved;
    }

    @Override
    public final GameSessionInfo delete(final long number) {
        final GameSessionInfo gameSession;

        log.debug("Deleting game session info {}", number);

        gameSession = gameSessionInfoRepository.findOne(number)
            .orElseThrow(() -> {
                log.error("Missing game session info {}", number);
                throw new MissingGameSessionInfoException(number);
            });

        gameSessionInfoRepository.delete(number);

        log.debug("Deleted game session info {}", number);

        return gameSession;
    }

    @Override
    public final Page<GameSessionInfo> getAll(final Pagination pagination, final Sorting sorting) {
        final Page<GameSessionInfo> gameSessions;

        log.info("Getting all game sessions info with pagination {} and sorting {}", pagination, sorting);

        gameSessions = gameSessionInfoRepository.findAll(pagination, sorting);

        log.debug("Got all game sessions info with pagination {} and sorting {}: {}", pagination, sorting,
            gameSessions);

        return gameSessions;
    }

    @Override
    public final Optional<GameSessionInfo> getOne(final long number) {
        final Optional<GameSessionInfo> gameSession;

        log.debug("Reading game session info with number {}", number);

        gameSession = gameSessionInfoRepository.findOne(number);
        if (gameSession.isEmpty()) {
            log.error("Missing game session info {}", number);
            throw new MissingGameSessionInfoException(number);
        }

        log.debug("Read game session info with number {}: {}", number, gameSession);

        return gameSession;
    }

    @Override
    public final GameSessionInfo update(final GameSessionInfo gameSessionInfo) {
        final boolean         exists;
        final GameSessionInfo updated;

        log.debug("Updating game session info with number {} using data {}", gameSessionInfo.number(), gameSessionInfo);

        exists = gameSessionInfoRepository.exists(gameSessionInfo.number());
        if (!exists) {
            log.error("Missing game session info {}", gameSessionInfo.number());
            throw new MissingGameSessionInfoException(gameSessionInfo.number());
        }

        validatorUpdate.validate(gameSessionInfo);

        updated = gameSessionInfoRepository.save(gameSessionInfo);

        log.debug("Updated game session info with number {}: {}", gameSessionInfo.number(), updated);

        return updated;
    }

}
