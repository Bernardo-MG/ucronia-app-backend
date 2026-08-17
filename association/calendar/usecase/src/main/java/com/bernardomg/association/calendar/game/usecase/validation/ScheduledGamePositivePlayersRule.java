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

package com.bernardomg.association.calendar.game.usecase.validation;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bernardomg.association.calendar.game.domain.model.ScheduledGame;
import com.bernardomg.validation.domain.model.FieldFailure;
import com.bernardomg.validation.validator.FieldRule;

/**
 * Checks the scheduled game has a positive maximum number of players.
 */
public final class ScheduledGamePositivePlayersRule implements FieldRule<ScheduledGame> {

    /**
     * Logger for the class.
     */
    private static final Logger log = LoggerFactory.getLogger(ScheduledGamePositivePlayersRule.class);

    public ScheduledGamePositivePlayersRule() {
        super();
    }

    @Override
    public final Optional<FieldFailure> check(final ScheduledGame scheduledGame) {
        final Optional<FieldFailure> failure;
        final FieldFailure           fieldFailure;

        if (scheduledGame.maxPlayers() > 0) {
            failure = Optional.empty();
        } else {
            log.error("Negative number of players: {}", scheduledGame.maxPlayers());
            fieldFailure = new FieldFailure("negative", "maxPlayers", scheduledGame.maxPlayers());
            failure = Optional.of(fieldFailure);
        }

        return failure;
    }

}
