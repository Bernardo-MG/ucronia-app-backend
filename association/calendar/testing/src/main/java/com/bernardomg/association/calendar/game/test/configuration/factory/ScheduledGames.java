
package com.bernardomg.association.calendar.game.test.configuration.factory;

import com.bernardomg.association.calendar.game.domain.model.Recurrence;
import com.bernardomg.association.calendar.game.domain.model.Recurrence.RecurrenceUnit;
import com.bernardomg.association.calendar.game.domain.model.ScheduledGame;

public final class ScheduledGames {

    public static final ScheduledGame negativeMaxPlayers() {
        final Recurrence recurrence;

        recurrence = new Recurrence(1, RecurrenceUnit.WEEKLY);
        return new ScheduledGame(ScheduledGameConstants.NUMBER, ScheduledGameConstants.TITLE,
            ScheduledGameConstants.DESCRIPTION, ScheduledGameConstants.LOCATION, ScheduledGameConstants.NUMBER_MASTER,
            -1, ScheduledGameConstants.IMAGE, ScheduledGameConstants.START, recurrence, false);
    }

    public static final ScheduledGame negativeRecurrence() {
        final Recurrence recurrence;

        recurrence = new Recurrence(-1, RecurrenceUnit.WEEKLY);
        return new ScheduledGame(ScheduledGameConstants.NUMBER, ScheduledGameConstants.TITLE,
            ScheduledGameConstants.DESCRIPTION, ScheduledGameConstants.LOCATION, ScheduledGameConstants.NUMBER_MASTER,
            ScheduledGameConstants.MAX_PLAYERS, ScheduledGameConstants.IMAGE, ScheduledGameConstants.START, recurrence,
            false);
    }

    public static final ScheduledGame titleChange() {
        final Recurrence recurrence;

        recurrence = new Recurrence(1, RecurrenceUnit.WEEKLY);
        return new ScheduledGame(ScheduledGameConstants.NUMBER, ScheduledGameConstants.ALTERNATIVE_TITLE,
            ScheduledGameConstants.DESCRIPTION, ScheduledGameConstants.LOCATION, ScheduledGameConstants.NUMBER_MASTER,
            ScheduledGameConstants.MAX_PLAYERS, ScheduledGameConstants.IMAGE, ScheduledGameConstants.START, recurrence,
            false);
    }

    public static final ScheduledGame weekly() {
        final Recurrence recurrence;

        recurrence = new Recurrence(1, RecurrenceUnit.WEEKLY);
        return new ScheduledGame(ScheduledGameConstants.NUMBER, ScheduledGameConstants.TITLE,
            ScheduledGameConstants.DESCRIPTION, ScheduledGameConstants.LOCATION, ScheduledGameConstants.NUMBER_MASTER,
            ScheduledGameConstants.MAX_PLAYERS, ScheduledGameConstants.IMAGE, ScheduledGameConstants.START, recurrence,
            false);
    }

    public static final ScheduledGame zeroMaxPlayers() {
        final Recurrence recurrence;

        recurrence = new Recurrence(1, RecurrenceUnit.WEEKLY);
        return new ScheduledGame(ScheduledGameConstants.NUMBER, ScheduledGameConstants.TITLE,
            ScheduledGameConstants.DESCRIPTION, ScheduledGameConstants.LOCATION, ScheduledGameConstants.NUMBER_MASTER,
            0, ScheduledGameConstants.IMAGE, ScheduledGameConstants.START, recurrence, false);
    }

    public static final ScheduledGame zeroRecurrence() {
        final Recurrence recurrence;

        recurrence = new Recurrence(0, RecurrenceUnit.WEEKLY);
        return new ScheduledGame(ScheduledGameConstants.NUMBER, ScheduledGameConstants.TITLE,
            ScheduledGameConstants.DESCRIPTION, ScheduledGameConstants.LOCATION, ScheduledGameConstants.NUMBER_MASTER,
            ScheduledGameConstants.MAX_PLAYERS, ScheduledGameConstants.IMAGE, ScheduledGameConstants.START, recurrence,
            false);
    }

    private ScheduledGames() {
        super();
    }

}
