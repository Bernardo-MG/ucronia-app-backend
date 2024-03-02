
package com.bernardomg.association.library.test.config.factory;

import com.bernardomg.association.library.adapter.inbound.jpa.model.GameSystemEntity;

public final class GameSystemEntities {

    public static final GameSystemEntity valid() {
        return GameSystemEntity.builder()
            .withName(GameSystemConstants.NAME)
            .build();
    }

}
