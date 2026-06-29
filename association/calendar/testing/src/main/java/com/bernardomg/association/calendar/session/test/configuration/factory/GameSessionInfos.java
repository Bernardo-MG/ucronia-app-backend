
package com.bernardomg.association.calendar.session.test.configuration.factory;

import com.bernardomg.association.calendar.game.domain.model.GameSessionInfo;
import com.bernardomg.association.calendar.game.domain.model.GameSessionMember;
import com.bernardomg.association.calendar.game.domain.model.Recurrence;
import com.bernardomg.association.calendar.game.domain.model.Recurrence.RecurrenceUnit;

public final class GameSessionInfos {

    public static final GameSessionInfo weekly() {
        final GameSessionMember master;
        final Recurrence        recurrence;

        master = GameSessionMembers.master();
        recurrence = new Recurrence(1, RecurrenceUnit.WEEKLY);
        return new GameSessionInfo(GameSessionInfoConstants.NUMBER, GameSessionInfoConstants.TITLE,
            GameSessionInfoConstants.DESCRIPTION, GameSessionInfoConstants.LOCATION, master,
            GameSessionInfoConstants.MAX_PLAYERS, GameSessionInfoConstants.IMAGE, GameSessionInfoConstants.START,
            recurrence, false);
    }

    private GameSessionInfos() {
        super();
    }

}
