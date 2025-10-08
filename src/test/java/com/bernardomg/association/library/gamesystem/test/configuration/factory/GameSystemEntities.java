
package com.bernardomg.association.library.gamesystem.test.configuration.factory;

import com.bernardomg.association.library.gamesystem.adapter.inbound.jpa.model.GameSystemEntity;

public final class GameSystemEntities {

    public static final GameSystemEntity valid() {
        final GameSystemEntity entity;

        entity = new GameSystemEntity();
        entity.setNumber(GameSystemConstants.NUMBER);
        entity.setName(GameSystemConstants.NAME);

        return entity;
    }

    private GameSystemEntities() {
        super();
    }
}
