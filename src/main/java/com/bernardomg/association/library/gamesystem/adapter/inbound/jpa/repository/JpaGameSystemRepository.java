
package com.bernardomg.association.library.gamesystem.adapter.inbound.jpa.repository;

import java.util.Objects;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bernardomg.association.library.gamesystem.adapter.inbound.jpa.model.GameSystemEntity;
import com.bernardomg.association.library.gamesystem.domain.model.GameSystem;
import com.bernardomg.association.library.gamesystem.domain.repository.GameSystemRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
@Transactional
public final class JpaGameSystemRepository implements GameSystemRepository {

    private final GameSystemSpringRepository gameSystemSpringRepository;

    public JpaGameSystemRepository(final GameSystemSpringRepository gameSystemRepo) {
        super();

        gameSystemSpringRepository = Objects.requireNonNull(gameSystemRepo);
    }

    @Override
    public final void delete(final Long number) {
        log.debug("Deleting game system {}", number);

        gameSystemSpringRepository.deleteByNumber(number);

        log.debug("Deleted game system {}", number);
    }

    @Override
    public final boolean exists(final Long number) {
        final boolean exists;

        log.debug("Checking if game system {} exists", number);

        exists = gameSystemSpringRepository.existsByNumber(number);

        log.debug("Game system {} exists: {}", number, exists);

        return exists;
    }

    @Override
    public final boolean existsByName(final String name) {
        final boolean exists;

        log.debug("Checking if game system {} exists", name);

        exists = gameSystemSpringRepository.existsByName(name);

        log.debug("Game system {} exists: {}", name, exists);

        return exists;
    }

    @Override
    public final boolean existsByNameForAnother(final String name, final Long number) {
        final boolean exists;

        log.debug("Checking if game system {} exists for a game system distinct from {}", name, number);

        exists = gameSystemSpringRepository.existsByNotNumberAndName(number, name);

        log.debug("Game system {} exists for a game system distinct from {}: {}", name, number, exists);

        return exists;
    }

    @Override
    public final Iterable<GameSystem> findAll(final Pageable pageable) {
        final Page<GameSystemEntity> page;
        final Iterable<GameSystem>   read;

        log.debug("Finding game systems with pagination {}", pageable);

        page = gameSystemSpringRepository.findAll(pageable);

        read = page.map(this::toDomain);

        log.debug("Found game systems {}", read);

        return read;
    }

    @Override
    public final long findNextNumber() {
        final long number;

        log.debug("Finding next number for the game systems");

        number = gameSystemSpringRepository.findNextNumber();

        log.debug("Found next number for the game systems: {}", number);

        return number;
    }

    @Override
    public final Optional<GameSystem> findOne(final Long number) {
        final Optional<GameSystem> gameSystem;

        log.debug("Finding game system with name {}", number);

        gameSystem = gameSystemSpringRepository.findByNumber(number)
            .map(this::toDomain);

        log.debug("Found game system with name {}: {}", number, gameSystem);

        return gameSystem;
    }

    @Override
    public final GameSystem save(final GameSystem gameSystem) {
        final Optional<GameSystemEntity> existing;
        final GameSystemEntity           entity;
        final GameSystemEntity           created;
        final GameSystem                 saved;

        log.debug("Saving game system {}", gameSystem);

        entity = toEntity(gameSystem);

        existing = gameSystemSpringRepository.findByNumber(gameSystem.number());
        if (existing.isPresent()) {
            entity.setId(existing.get()
                .getId());
        }

        created = gameSystemSpringRepository.save(entity);
        saved = toDomain(created);

        log.debug("Saved game system {}", saved);

        return saved;
    }

    private final GameSystem toDomain(final GameSystemEntity entity) {
        return new GameSystem(entity.getNumber(), entity.getName());
    }

    private final GameSystemEntity toEntity(final GameSystem domain) {
        return GameSystemEntity.builder()
            .withNumber(domain.number())
            .withName(domain.name())
            .build();
    }

}
