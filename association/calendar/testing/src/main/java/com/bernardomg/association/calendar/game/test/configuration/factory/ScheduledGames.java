
package com.bernardomg.association.calendar.game.test.configuration.factory;

import java.util.Optional;

import com.bernardomg.association.calendar.domain.model.Recurrence;
import com.bernardomg.association.calendar.domain.model.Recurrence.RecurrenceStatus;
import com.bernardomg.association.calendar.domain.model.Recurrence.RecurrenceUnit;
import com.bernardomg.association.calendar.game.domain.model.GameSessionType;
import com.bernardomg.association.calendar.game.domain.model.ScheduledGame;

public final class ScheduledGames {

    public static final ScheduledGame negativeMaxPlayers() {
        return new ScheduledGame(ScheduledGameConstants.NUMBER, ScheduledGameConstants.TITLE,
            ScheduledGameConstants.DESCRIPTION, ScheduledGameConstants.LOCATION, ScheduledGameConstants.NUMBER_MASTER,
            -1, ScheduledGameConstants.IMAGE, ScheduledGameConstants.START, Optional.empty(), false,
            GameSessionType.ONESHOT);
    }

    public static final ScheduledGame negativeRecurrence() {
        return new ScheduledGame(ScheduledGameConstants.NUMBER, ScheduledGameConstants.TITLE,
            ScheduledGameConstants.DESCRIPTION, ScheduledGameConstants.LOCATION, ScheduledGameConstants.NUMBER_MASTER,
            ScheduledGameConstants.MAX_PLAYERS, ScheduledGameConstants.IMAGE, ScheduledGameConstants.START,
            Optional.empty(), false, GameSessionType.ONESHOT);
    }

    public static final ScheduledGame titleChange() {
        return new ScheduledGame(ScheduledGameConstants.NUMBER, ScheduledGameConstants.ALTERNATIVE_TITLE,
            ScheduledGameConstants.DESCRIPTION, ScheduledGameConstants.LOCATION, ScheduledGameConstants.NUMBER_MASTER,
            ScheduledGameConstants.MAX_PLAYERS, ScheduledGameConstants.IMAGE, ScheduledGameConstants.START,
            Optional.empty(), true, GameSessionType.ONESHOT);
    }

    public static final ScheduledGame weeklyCampaign() {
        final Recurrence recurrence;

        recurrence = new Recurrence(1, RecurrenceUnit.WEEKLY, RecurrenceStatus.ACTIVE);
        return new ScheduledGame(ScheduledGameConstants.NUMBER, ScheduledGameConstants.TITLE,
            ScheduledGameConstants.DESCRIPTION, ScheduledGameConstants.LOCATION, ScheduledGameConstants.NUMBER_MASTER,
            ScheduledGameConstants.MAX_PLAYERS, ScheduledGameConstants.IMAGE, ScheduledGameConstants.START,
            Optional.of(recurrence), false, GameSessionType.CAMPAIGN);
    }

    public static final ScheduledGame weeklyOneshot() {
        final Recurrence recurrence;

        recurrence = new Recurrence(1, RecurrenceUnit.WEEKLY, RecurrenceStatus.ACTIVE);
        return new ScheduledGame(ScheduledGameConstants.NUMBER, ScheduledGameConstants.TITLE,
            ScheduledGameConstants.DESCRIPTION, ScheduledGameConstants.LOCATION, ScheduledGameConstants.NUMBER_MASTER,
            ScheduledGameConstants.MAX_PLAYERS, ScheduledGameConstants.IMAGE, ScheduledGameConstants.START,
            Optional.of(recurrence), false, GameSessionType.ONESHOT);
    }

    public static final ScheduledGame weeklyOneshotPublished() {
        final Recurrence recurrence;

        recurrence = new Recurrence(1, RecurrenceUnit.WEEKLY, RecurrenceStatus.ACTIVE);
        return new ScheduledGame(ScheduledGameConstants.NUMBER, ScheduledGameConstants.TITLE,
            ScheduledGameConstants.DESCRIPTION, ScheduledGameConstants.LOCATION, ScheduledGameConstants.NUMBER_MASTER,
            ScheduledGameConstants.MAX_PLAYERS, ScheduledGameConstants.IMAGE, ScheduledGameConstants.START,
            Optional.of(recurrence), true, GameSessionType.ONESHOT);
    }

    public static final ScheduledGame zeroMaxPlayers() {
        return new ScheduledGame(ScheduledGameConstants.NUMBER, ScheduledGameConstants.TITLE,
            ScheduledGameConstants.DESCRIPTION, ScheduledGameConstants.LOCATION, ScheduledGameConstants.NUMBER_MASTER,
            0, ScheduledGameConstants.IMAGE, ScheduledGameConstants.START, Optional.empty(), false,
            GameSessionType.ONESHOT);
    }

    public static final ScheduledGame zeroRecurrence() {
        return new ScheduledGame(ScheduledGameConstants.NUMBER, ScheduledGameConstants.TITLE,
            ScheduledGameConstants.DESCRIPTION, ScheduledGameConstants.LOCATION, ScheduledGameConstants.NUMBER_MASTER,
            ScheduledGameConstants.MAX_PLAYERS, ScheduledGameConstants.IMAGE, ScheduledGameConstants.START,
            Optional.empty(), false, GameSessionType.ONESHOT);
    }

    private ScheduledGames() {
        super();
    }

}
