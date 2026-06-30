
package com.bernardomg.association.calendar.game.test.configuration.factory;

import com.bernardomg.association.calendar.game.domain.model.Recurrence;
import com.bernardomg.association.calendar.game.domain.model.Recurrence.RecurrenceUnit;
import com.bernardomg.association.calendar.game.domain.model.ScheduledGame;
import com.bernardomg.association.calendar.game.domain.model.ScheduledGameMember;

public final class ScheduledGames {

    public static final ScheduledGame titleChange() {
        final ScheduledGameMember master;
        final Recurrence          recurrence;

        master = ScheduledGameMembers.master();
        recurrence = new Recurrence(1, RecurrenceUnit.WEEKLY);
        return new ScheduledGame(ScheduledGameConstants.NUMBER, ScheduledGameConstants.ALTERNATIVE_TITLE,
            ScheduledGameConstants.DESCRIPTION, ScheduledGameConstants.LOCATION, master,
            ScheduledGameConstants.MAX_PLAYERS, ScheduledGameConstants.IMAGE, ScheduledGameConstants.START, recurrence,
            false);
    }

    public static final ScheduledGame weekly() {
        final ScheduledGameMember master;
        final Recurrence          recurrence;

        master = ScheduledGameMembers.master();
        recurrence = new Recurrence(1, RecurrenceUnit.WEEKLY);
        return new ScheduledGame(ScheduledGameConstants.NUMBER, ScheduledGameConstants.TITLE,
            ScheduledGameConstants.DESCRIPTION, ScheduledGameConstants.LOCATION, master,
            ScheduledGameConstants.MAX_PLAYERS, ScheduledGameConstants.IMAGE, ScheduledGameConstants.START, recurrence,
            false);
    }

    private ScheduledGames() {
        super();
    }

}
