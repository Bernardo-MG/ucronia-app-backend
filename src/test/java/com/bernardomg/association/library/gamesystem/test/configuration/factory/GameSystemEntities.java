
package com.bernardomg.association.library.gamesystem.test.configuration.factory;

import com.bernardomg.association.library.gamesystem.adapter.inbound.jpa.model.GameSystemEntity;

public final class GameSystemEntities {

    public static final GameSystemEntity valid() {
        return GameSystemEntity.builder()
            .withName(GameSystemConstants.NAME)
            .build();
    }

}
