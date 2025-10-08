
package com.bernardomg.association.library.gamesystem.usecase.validation;

import java.util.Objects;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bernardomg.association.library.gamesystem.domain.model.GameSystem;
import com.bernardomg.association.library.gamesystem.domain.repository.GameSystemRepository;
import com.bernardomg.validation.domain.model.FieldFailure;
import com.bernardomg.validation.validator.FieldRule;

/**
 * Checks the game system name doesn't exist for another game system.
 */
public final class GameSystemNameNotExistsForAnotherRule implements FieldRule<GameSystem> {

    /**
     * Logger for the class.
     */
    private static final Logger        log = LoggerFactory.getLogger(GameSystemNameNotExistsForAnotherRule.class);

    private final GameSystemRepository gameSystemRepository;

    public GameSystemNameNotExistsForAnotherRule(final GameSystemRepository gameSystemRepo) {
        super();

        gameSystemRepository = Objects.requireNonNull(gameSystemRepo);
    }

    @Override
    public final Optional<FieldFailure> check(final GameSystem gameSystem) {
        final Optional<FieldFailure> failure;
        final FieldFailure           fieldFailure;

        if ((!StringUtils.isBlank(gameSystem.name()))
                && (gameSystemRepository.existsByNameForAnother(gameSystem.name(), gameSystem.number()))) {
            log.error("Existing game system name {} for a game system distinct from {}", gameSystem.name(),
                gameSystem.number());
            fieldFailure = new FieldFailure("existing", "name", gameSystem.name());
            failure = Optional.of(fieldFailure);
        } else {
            failure = Optional.empty();
        }

        return failure;
    }

}
