
package com.bernardomg.association.library.gamesystem.usecase.service;

import java.util.Objects;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bernardomg.association.library.gamesystem.domain.exception.GameSystemHasRelationshipsException;
import com.bernardomg.association.library.gamesystem.domain.exception.MissingGameSystemException;
import com.bernardomg.association.library.gamesystem.domain.model.GameSystem;
import com.bernardomg.association.library.gamesystem.domain.repository.GameSystemRepository;
import com.bernardomg.association.library.gamesystem.usecase.validation.GameSystemNameNotEmptyRule;
import com.bernardomg.association.library.gamesystem.usecase.validation.GameSystemNameNotExistingRule;
import com.bernardomg.validation.validator.FieldRuleValidator;
import com.bernardomg.validation.validator.Validator;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
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
        final GameSystem toCreate;
        final Long       number;

        log.debug("Creating game system {}", system);

        // Set number
        number = gameSystemRepository.findNextNumber();
        toCreate = new GameSystem(number, system.name());

        createGameSystemValidator.validate(toCreate);

        return gameSystemRepository.save(toCreate);
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
        return gameSystemRepository.findAll(pageable);
    }

    @Override
    public final Optional<GameSystem> getOne(final String name) {
        final Optional<GameSystem> gameSystem;

        log.debug("Reading game system {}", name);

        gameSystem = gameSystemRepository.findOne(name);
        if (gameSystem.isEmpty()) {
            log.error("Missing game system {}", name);
            throw new MissingGameSystemException(name);
        }

        return gameSystem;
    }

}
