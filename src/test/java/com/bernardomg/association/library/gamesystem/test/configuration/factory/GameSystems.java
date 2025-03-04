
package com.bernardomg.association.library.gamesystem.test.configuration.factory;

import com.bernardomg.association.library.gamesystem.domain.model.GameSystem;

public final class GameSystems {

    public static final GameSystem emptyName() {
        return new GameSystem(GameSystemConstants.NUMBER, " ");
    }

    public static final GameSystem padded() {
        return new GameSystem(-1L, " " + GameSystemConstants.NAME + " ");
    }

    public static final GameSystem toCreate() {
        return new GameSystem(-1L, GameSystemConstants.NAME);
    }

    public static final GameSystem valid() {
        return new GameSystem(GameSystemConstants.NUMBER, GameSystemConstants.NAME);
    }

}
