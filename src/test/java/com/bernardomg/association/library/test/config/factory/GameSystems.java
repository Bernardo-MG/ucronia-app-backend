
package com.bernardomg.association.library.test.config.factory;

import com.bernardomg.association.library.domain.model.GameSystem;

public final class GameSystems {

    public static final GameSystem emptyName() {
        return GameSystem.builder()
            .withName(" ")
            .build();
    }

    public static final GameSystem valid() {
        return GameSystem.builder()
            .withName(GameSystemConstants.NAME)
            .build();
    }

}
