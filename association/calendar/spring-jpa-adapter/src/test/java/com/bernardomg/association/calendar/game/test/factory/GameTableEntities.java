
package com.bernardomg.association.calendar.game.test.factory;

import com.bernardomg.association.calendar.game.adapter.inbound.jpa.model.GameTableEntity;
import com.bernardomg.association.calendar.game.test.configuration.factory.GameTableConstants;

public final class GameTableEntities {

    public static final GameTableEntity valid() {
        final GameTableEntity entity;

        entity = new GameTableEntity();
        entity.setNumber(GameTableConstants.NUMBER);
        entity.setName(GameTableConstants.NAME);
        entity.setDescription(GameTableConstants.DESCRIPTION);

        return entity;
    }

    private GameTableEntities() {
        super();
    }

}
