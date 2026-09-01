
package com.bernardomg.association.calendar.game.test.configuration.factory;

import java.util.Optional;

import com.bernardomg.association.calendar.domain.model.CalendarStatus;
import com.bernardomg.association.calendar.domain.model.Recurrence;
import com.bernardomg.association.calendar.domain.model.Recurrence.RecurrenceStatus;
import com.bernardomg.association.calendar.domain.model.Recurrence.RecurrenceUnit;
import com.bernardomg.association.calendar.game.domain.model.GameSessionType;
import com.bernardomg.association.calendar.game.domain.model.ScheduledGame;

public final class ScheduledGames {

    public static final ScheduledGame cancelled() {
        return new ScheduledGame(ScheduledGameConstants.NUMBER, ScheduledGameConstants.TITLE,
            ScheduledGameConstants.DESCRIPTION, ScheduledGameConstants.LOCATION, GameTables.valid(),
            ScheduledGameConstants.NUMBER_MASTER, ScheduledGameConstants.MAX_PLAYERS, ScheduledGameConstants.IMAGE,
            ScheduledGameConstants.START, Optional.empty(), CalendarStatus.CANCELLED, GameSessionType.ONESHOT);
    }

    public static final ScheduledGame draft() {
        return new ScheduledGame(ScheduledGameConstants.NUMBER, ScheduledGameConstants.TITLE,
            ScheduledGameConstants.DESCRIPTION, ScheduledGameConstants.LOCATION, GameTables.valid(),
            ScheduledGameConstants.NUMBER_MASTER, ScheduledGameConstants.MAX_PLAYERS, ScheduledGameConstants.IMAGE,
            ScheduledGameConstants.START, Optional.empty(), CalendarStatus.DRAFT, GameSessionType.ONESHOT);
    }

    public static final ScheduledGame negativeMaxPlayers() {
        return new ScheduledGame(ScheduledGameConstants.NUMBER, ScheduledGameConstants.TITLE,
            ScheduledGameConstants.DESCRIPTION, ScheduledGameConstants.LOCATION, GameTables.valid(),
            ScheduledGameConstants.NUMBER_MASTER, -1, ScheduledGameConstants.IMAGE, ScheduledGameConstants.START,
            Optional.empty(), CalendarStatus.DRAFT, GameSessionType.ONESHOT);
    }

    public static final ScheduledGame negativeRecurrence() {
        final Recurrence recurrence;

        recurrence = new Recurrence(-1, RecurrenceUnit.WEEKLY, RecurrenceStatus.ACTIVE);
        return new ScheduledGame(ScheduledGameConstants.NUMBER, ScheduledGameConstants.TITLE,
            ScheduledGameConstants.DESCRIPTION, ScheduledGameConstants.LOCATION, GameTables.valid(),
            ScheduledGameConstants.NUMBER_MASTER, ScheduledGameConstants.MAX_PLAYERS, ScheduledGameConstants.IMAGE,
            ScheduledGameConstants.START, Optional.of(recurrence), CalendarStatus.DRAFT, GameSessionType.CAMPAIGN);
    }

    public static final ScheduledGame published() {
        return new ScheduledGame(ScheduledGameConstants.NUMBER, ScheduledGameConstants.TITLE,
            ScheduledGameConstants.DESCRIPTION, ScheduledGameConstants.LOCATION, GameTables.valid(),
            ScheduledGameConstants.NUMBER_MASTER, ScheduledGameConstants.MAX_PLAYERS, ScheduledGameConstants.IMAGE,
            ScheduledGameConstants.START, Optional.empty(), CalendarStatus.PUBLISHED, GameSessionType.ONESHOT);
    }

    public static final ScheduledGame rejected() {
        return new ScheduledGame(ScheduledGameConstants.NUMBER, ScheduledGameConstants.TITLE,
            ScheduledGameConstants.DESCRIPTION, ScheduledGameConstants.LOCATION, GameTables.valid(),
            ScheduledGameConstants.NUMBER_MASTER, ScheduledGameConstants.MAX_PLAYERS, ScheduledGameConstants.IMAGE,
            ScheduledGameConstants.START, Optional.empty(), CalendarStatus.REJECTED, GameSessionType.ONESHOT);
    }

    public static final ScheduledGame titleChange() {
        return new ScheduledGame(ScheduledGameConstants.NUMBER, ScheduledGameConstants.ALTERNATIVE_TITLE,
            ScheduledGameConstants.DESCRIPTION, ScheduledGameConstants.LOCATION, GameTables.valid(),
            ScheduledGameConstants.NUMBER_MASTER, ScheduledGameConstants.MAX_PLAYERS, ScheduledGameConstants.IMAGE,
            ScheduledGameConstants.START, Optional.empty(), CalendarStatus.PUBLISHED, GameSessionType.ONESHOT);
    }

    public static final ScheduledGame weeklyCampaign() {
        final Recurrence recurrence;

        recurrence = new Recurrence(1, RecurrenceUnit.WEEKLY, RecurrenceStatus.ACTIVE);
        return new ScheduledGame(ScheduledGameConstants.NUMBER, ScheduledGameConstants.TITLE,
            ScheduledGameConstants.DESCRIPTION, ScheduledGameConstants.LOCATION, GameTables.valid(),
            ScheduledGameConstants.NUMBER_MASTER, ScheduledGameConstants.MAX_PLAYERS, ScheduledGameConstants.IMAGE,
            ScheduledGameConstants.START, Optional.of(recurrence), CalendarStatus.DRAFT, GameSessionType.CAMPAIGN);
    }

    public static final ScheduledGame weeklyOneshot() {
        final Recurrence recurrence;

        recurrence = new Recurrence(1, RecurrenceUnit.WEEKLY, RecurrenceStatus.ACTIVE);
        return new ScheduledGame(ScheduledGameConstants.NUMBER, ScheduledGameConstants.TITLE,
            ScheduledGameConstants.DESCRIPTION, ScheduledGameConstants.LOCATION, GameTables.valid(),
            ScheduledGameConstants.NUMBER_MASTER, ScheduledGameConstants.MAX_PLAYERS, ScheduledGameConstants.IMAGE,
            ScheduledGameConstants.START, Optional.of(recurrence), CalendarStatus.DRAFT, GameSessionType.ONESHOT);
    }

    public static final ScheduledGame weeklyOneshotPublished() {
        final Recurrence recurrence;

        recurrence = new Recurrence(1, RecurrenceUnit.WEEKLY, RecurrenceStatus.ACTIVE);
        return new ScheduledGame(ScheduledGameConstants.NUMBER, ScheduledGameConstants.TITLE,
            ScheduledGameConstants.DESCRIPTION, ScheduledGameConstants.LOCATION, GameTables.valid(),
            ScheduledGameConstants.NUMBER_MASTER, ScheduledGameConstants.MAX_PLAYERS, ScheduledGameConstants.IMAGE,
            ScheduledGameConstants.START, Optional.of(recurrence), CalendarStatus.PUBLISHED, GameSessionType.ONESHOT);
    }

    public static final ScheduledGame zeroMaxPlayers() {
        return new ScheduledGame(ScheduledGameConstants.NUMBER, ScheduledGameConstants.TITLE,
            ScheduledGameConstants.DESCRIPTION, ScheduledGameConstants.LOCATION, GameTables.valid(),
            ScheduledGameConstants.NUMBER_MASTER, 0, ScheduledGameConstants.IMAGE, ScheduledGameConstants.START,
            Optional.empty(), CalendarStatus.DRAFT, GameSessionType.ONESHOT);
    }

    public static final ScheduledGame zeroRecurrence() {
        final Recurrence recurrence;

        recurrence = new Recurrence(0, RecurrenceUnit.WEEKLY, RecurrenceStatus.ACTIVE);
        return new ScheduledGame(ScheduledGameConstants.NUMBER, ScheduledGameConstants.TITLE,
            ScheduledGameConstants.DESCRIPTION, ScheduledGameConstants.LOCATION, GameTables.valid(),
            ScheduledGameConstants.NUMBER_MASTER, ScheduledGameConstants.MAX_PLAYERS, ScheduledGameConstants.IMAGE,
            ScheduledGameConstants.START, Optional.of(recurrence), CalendarStatus.DRAFT, GameSessionType.CAMPAIGN);
    }

    private ScheduledGames() {
        super();
    }

}
