
package com.bernardomg.association.calendar.game.test.factory;

import com.bernardomg.association.calendar.game.adapter.inbound.jpa.model.ScheduledGameProfileEntity;
import com.bernardomg.association.calendar.game.test.configuration.factory.ScheduledGameMemberConstants;

public final class ScheduledGameProfileEntities {

    public static final ScheduledGameProfileEntity master() {
        final ScheduledGameProfileEntity entity;

        entity = new ScheduledGameProfileEntity();
        entity.setNumber(ScheduledGameMemberConstants.NUMBER_MASTER);
        entity.setFirstName(ScheduledGameMemberConstants.NAME_MASTER);
        entity.setLastName(ScheduledGameMemberConstants.SURNAME_MASTER);

        return entity;
    }

    private ScheduledGameProfileEntities() {
        super();
    }

}
