
package com.bernardomg.association.library.gamesystem.usecase.service;

import java.util.Objects;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public final void delete(final Long number) {

        log.debug("Deleting game system {}", number);

        if (!gameSystemRepository.exists(number)) {
            throw new MissingGameSystemException(number);
        }

        gameSystemRepository.delete(number);
    }

    @Override
    public final Iterable<GameSystem> getAll(final Pageable pageable) {
        log.debug("Reading game systems with pagination {}", pageable);

        return gameSystemRepository.findAll(pageable);
    }

    @Override
    public final Optional<GameSystem> getOne(final Long number) {
        final Optional<GameSystem> gameSystem;

        log.debug("Reading game system {}", number);

        gameSystem = gameSystemRepository.findOne(number);
        if (gameSystem.isEmpty()) {
            log.error("Missing game system {}", number);
            throw new MissingGameSystemException(number);
        }

        return gameSystem;
    }

    @Override
    public final GameSystem update(final GameSystem system) {
        log.debug("Updating game system {}", system);

        if (!gameSystemRepository.exists(system.number())) {
            throw new MissingGameSystemException(system.number());
        }

        // Set number
        createGameSystemValidator.validate(system);

        return gameSystemRepository.save(system);
    }

}
