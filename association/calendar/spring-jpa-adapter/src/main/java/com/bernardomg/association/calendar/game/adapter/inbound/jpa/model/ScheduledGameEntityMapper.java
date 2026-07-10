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

package com.bernardomg.association.calendar.game.adapter.inbound.jpa.model;

import com.bernardomg.association.calendar.adapter.inbound.jpa.model.CalendarInfoRecurrence;
import com.bernardomg.association.calendar.game.domain.model.GameSessionType;
import com.bernardomg.association.calendar.game.domain.model.Recurrence;
import com.bernardomg.association.calendar.game.domain.model.ScheduledGame;

/**
 * Scheduled game repository mapper.
 */
public final class ScheduledGameEntityMapper {

    public static final ScheduledGame toDomain(final ScheduledGameEntity entity, final Boolean status) {
        final Recurrence      recurrence;
        final GameSessionType gameSessionType;

        recurrence = new Recurrence(entity.getRecurrence()
            .getInterval(),
            entity.getRecurrence()
                .getUnit());

        gameSessionType = (entity.getTypes() != null) && entity.getTypes()
            .stream()
            .anyMatch(t -> t.getNumber() == ScheduledGameEntityConstants.CAMPAIGN_TYPE) ? GameSessionType.CAMPAIGN
                    : GameSessionType.ONESHOT;

        return new ScheduledGame(entity.getNumber(), entity.getTitle(), entity.getDescription(), entity.getLocation(),
            entity.getMaster()
                .getNumber(),
            entity.getMaxPlayers(), entity.getImage(), entity.getStart(), recurrence, status, gameSessionType);
    }

    public static final ScheduledGameEntity toEntity(final ScheduledGame scheduledGame) {
        final ScheduledGameEntity    entity;
        final CalendarInfoRecurrence recurrence;

        entity = new ScheduledGameEntity();
        entity.setNumber(scheduledGame.number());
        entity.setTitle(scheduledGame.title());
        entity.setDescription(scheduledGame.description());
        entity.setLocation(scheduledGame.location());
        entity.setMaxPlayers(scheduledGame.maxPlayers());
        entity.setImage(scheduledGame.image());
        entity.setStart(scheduledGame.start());

        recurrence = new CalendarInfoRecurrence();
        recurrence.setInterval(scheduledGame.recurrence()
            .interval());
        recurrence.setUnit(scheduledGame.recurrence()
            .unit());
        entity.setRecurrence(recurrence);

        return entity;
    }

    private ScheduledGameEntityMapper() {
        super();
    }

}
