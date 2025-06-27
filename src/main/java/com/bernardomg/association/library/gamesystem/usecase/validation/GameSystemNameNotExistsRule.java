
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
 * Checks the game system name doesn't exist.
 */
public final class GameSystemNameNotExistsRule implements FieldRule<GameSystem> {

    /**
     * Logger for the class.
     */
    private static final Logger        log = LoggerFactory.getLogger(GameSystemNameNotExistsRule.class);

    private final GameSystemRepository gameSystemRepository;

    public GameSystemNameNotExistsRule(final GameSystemRepository gameSystemRepo) {
        super();

        gameSystemRepository = Objects.requireNonNull(gameSystemRepo);
    }

    @Override
    public final Optional<FieldFailure> check(final GameSystem gameSystem) {
        final Optional<FieldFailure> failure;
        final FieldFailure           fieldFailure;

        if ((!StringUtils.isBlank(gameSystem.name())) && (gameSystemRepository.existsByName(gameSystem.name()))) {
            log.error("Existing game system name {}", gameSystem.name());
            fieldFailure = new FieldFailure("existing", "name", gameSystem.name());
            failure = Optional.of(fieldFailure);
        } else {
            failure = Optional.empty();
        }

        return failure;
    }

}
