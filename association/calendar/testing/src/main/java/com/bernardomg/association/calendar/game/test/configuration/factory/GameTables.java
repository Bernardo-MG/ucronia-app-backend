
package com.bernardomg.association.calendar.game.test.configuration.factory;

import com.bernardomg.association.calendar.game.domain.model.GameTable;

public final class GameTables {

    public static final GameTable nameChange() {
        return new GameTable(GameTableConstants.NUMBER, GameTableConstants.ALTERNATIVE_NAME,
            GameTableConstants.DESCRIPTION);
    }

    public static final GameTable valid() {
        return new GameTable(GameTableConstants.NUMBER, GameTableConstants.NAME, GameTableConstants.DESCRIPTION);
    }

    private GameTables() {
        super();
    }

}
