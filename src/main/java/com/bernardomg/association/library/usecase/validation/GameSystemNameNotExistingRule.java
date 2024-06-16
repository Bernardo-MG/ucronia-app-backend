
package com.bernardomg.association.library.usecase.validation;

import java.util.Objects;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;

import com.bernardomg.association.library.domain.model.GameSystem;
import com.bernardomg.association.library.domain.repository.GameSystemRepository;
import com.bernardomg.validation.domain.model.FieldFailure;
import com.bernardomg.validation.validator.FieldRule;

import lombok.extern.slf4j.Slf4j;

/**
 * Checks the donor has a name.
 */
@Slf4j
public final class GameSystemNameNotExistingRule implements FieldRule<GameSystem> {

    private final GameSystemRepository gameSystemRepository;

    public GameSystemNameNotExistingRule(final GameSystemRepository gameSystemRepo) {
        super();

        gameSystemRepository = Objects.requireNonNull(gameSystemRepo);
    }

    @Override
    public final Optional<FieldFailure> check(final GameSystem gameSystem) {
        final Optional<FieldFailure> failure;
        final FieldFailure           fieldFailure;

        if ((!StringUtils.isBlank(gameSystem.getName())) && (gameSystemRepository.exists(gameSystem.getName()))) {
            log.error("Existing name {}", gameSystem.getName());
            fieldFailure = FieldFailure.of("name", "existing", gameSystem.getName());
            failure = Optional.of(fieldFailure);
        } else {
            failure = Optional.empty();
        }

        return failure;
    }

}
