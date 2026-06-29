
package com.bernardomg.association.calendar.game.test.configuration.factory;

import com.bernardomg.association.calendar.game.domain.model.ScheduledGameMember;
import com.bernardomg.association.calendar.game.domain.model.ScheduledGameMember.Name;

public final class ScheduledGameMembers {

    public static final ScheduledGameMember master() {
        final Name name;

        name = new Name(ScheduledGameMemberConstants.NAME_MASTER, ScheduledGameMemberConstants.SURNAME_MASTER);
        return new ScheduledGameMember(ScheduledGameMemberConstants.NUMBER_MASTER, name);
    }

    private ScheduledGameMembers() {
        super();
    }

}
