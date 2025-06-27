
package com.bernardomg.association.library.gamesystem.usecase.validation;

import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bernardomg.association.library.gamesystem.domain.model.GameSystem;
import com.bernardomg.validation.domain.model.FieldFailure;
import com.bernardomg.validation.validator.FieldRule;

/**
 * Checks the game system has a name.
 */
public final class GameSystemNameNotEmptyRule implements FieldRule<GameSystem> {

    /**
     * Logger for the class.
     */
    private static final Logger log = LoggerFactory.getLogger(GameSystemNameNotEmptyRule.class);

    public GameSystemNameNotEmptyRule() {
        super();
    }

    @Override
    public final Optional<FieldFailure> check(final GameSystem gameSystem) {
        final Optional<FieldFailure> failure;
        final FieldFailure           fieldFailure;

        if (StringUtils.isBlank(gameSystem.name())) {
            log.error("Empty game system name");
            fieldFailure = new FieldFailure("empty", "name", gameSystem.name());
            failure = Optional.of(fieldFailure);
        } else {
            failure = Optional.empty();
        }

        return failure;
    }

}
