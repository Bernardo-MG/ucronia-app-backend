
package com.bernardomg.association.calendar.session.test.configuration.factory;

import com.bernardomg.association.calendar.game.domain.model.GameSessionMember;
import com.bernardomg.association.calendar.game.domain.model.GameSessionMember.Name;

public final class GameSessionMembers {

    public static final GameSessionMember master() {
        final Name name;

        name = new Name(GameSessionMemberConstants.NAME_MASTER, GameSessionMemberConstants.SURNAME_MASTER);
        return new GameSessionMember(GameSessionMemberConstants.NUMBER_MASTER, name);
    }

    private GameSessionMembers() {
        super();
    }

}
