
package com.bernardomg.association.library.usecase.service;

import java.util.Objects;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import com.bernardomg.association.library.domain.exception.GameSystemHasRelationshipsException;
import com.bernardomg.association.library.domain.exception.MissingGameSystemException;
import com.bernardomg.association.library.domain.model.GameSystem;
import com.bernardomg.association.library.domain.repository.GameSystemRepository;
import com.bernardomg.association.library.usecase.validation.GameSystemNameNotEmptyRule;
import com.bernardomg.association.library.usecase.validation.GameSystemNameNotExistingRule;
import com.bernardomg.validation.validator.FieldRuleValidator;
import com.bernardomg.validation.validator.Validator;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Transactional
public final class DefaultGameSystemService implements GameSystemService {

    private final Validator<GameSystem> createGameSystemValidator;

    private final GameSystemRepository  gameSystemRepository;

    public DefaultGameSystemService(final GameSystemRepository gameSystemRepo) {
        super();

        gameSystemRepository = Objects.requireNonNull(gameSystemRepo);

        createGameSystemValidator = new FieldRuleValidator<>(new GameSystemNameNotEmptyRule(),
            new GameSystemNameNotExistingRule(gameSystemRepository));
    }

    @Override
    public final GameSystem create(final GameSystem system) {
        log.debug("Creating game system {}", system);

        createGameSystemValidator.validate(system);

        return gameSystemRepository.save(system);
    }

    @Override
    public final void delete(final String name) {

        log.debug("Deleting game system {}", name);

        if (!gameSystemRepository.exists(name)) {
            throw new MissingGameSystemException(name);
        }

        if (gameSystemRepository.hasRelationships(name)) {
            throw new GameSystemHasRelationshipsException(name);
        }

        gameSystemRepository.delete(name);
    }

    @Override
    public final Iterable<GameSystem> getAll(final Pageable pageable) {
        return gameSystemRepository.getAll(pageable);
    }

    @Override
    public final Optional<GameSystem> getOne(final String name) {
        final Optional<GameSystem> gameSystem;

        log.debug("Reading game system {}", name);

        gameSystem = gameSystemRepository.findOne(name);
        if (gameSystem.isEmpty()) {
            throw new MissingGameSystemException(name);
        }

        return gameSystem;
    }

}
