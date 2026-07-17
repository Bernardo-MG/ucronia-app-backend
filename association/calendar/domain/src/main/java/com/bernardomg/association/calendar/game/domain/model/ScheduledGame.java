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

package com.bernardomg.association.calendar.game.domain.model;

import java.time.Instant;
import java.util.Objects;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;

import com.bernardomg.association.calendar.domain.model.CalendarStatus;
import com.bernardomg.association.calendar.domain.model.Recurrence;

public record ScheduledGame(long number, String title, String description, String location, Long master, int maxPlayers,
        String image, Instant start, Optional<Recurrence> recurrence, CalendarStatus status,
        GameSessionType gameSessionType) {

    public ScheduledGame(final long number, final String title, final String description, final String location,
            final Long master, final int maxPlayers, final String image, final Instant start,
            final Optional<Recurrence> recurrence, final CalendarStatus status, final GameSessionType gameSessionType) {
        Objects.requireNonNull(number, "Number can't be null");
        Objects.requireNonNull(title, "Title can't be null");
        Objects.requireNonNull(description, "Description can't be null");
        Objects.requireNonNull(location, "Location can't be null");
        Objects.requireNonNull(image, "Image can't be null");
        Objects.requireNonNull(master, "Master can't be null");
        Objects.requireNonNull(maxPlayers, "Max players can't be null");
        Objects.requireNonNull(start, "Start can't be null");
        Objects.requireNonNull(recurrence, "Recurrence can't be null");
        Objects.requireNonNull(status, "Status can't be null");
        Objects.requireNonNull(gameSessionType, "Game session type can't be null");

        this.number = number;
        this.title = StringUtils.trim(title);
        this.description = StringUtils.trim(description);
        this.location = StringUtils.trim(location);
        this.master = master;
        this.maxPlayers = maxPlayers;
        this.image = StringUtils.trim(image);
        this.start = start;
        this.recurrence = recurrence;
        this.status = status;
        this.gameSessionType = gameSessionType;
    }

    public ScheduledGame publish() {
        return new ScheduledGame(number, title, description, location, master, maxPlayers, image, start, recurrence,
            CalendarStatus.PUBLISHED, gameSessionType);
    }

    public ScheduledGame draft() {
        return new ScheduledGame(number, title, description, location, master, maxPlayers, image, start, recurrence,
            CalendarStatus.DRAFT, gameSessionType);
    }

}
