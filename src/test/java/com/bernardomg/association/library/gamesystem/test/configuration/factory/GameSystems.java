
package com.bernardomg.association.library.gamesystem.test.configuration.factory;

import com.bernardomg.association.library.gamesystem.domain.model.GameSystem;

public final class GameSystems {

    public static final GameSystem emptyName() {
        return new GameSystem(" ");
    }

    public static final GameSystem valid() {
        return new GameSystem(GameSystemConstants.NAME);
    }

}
