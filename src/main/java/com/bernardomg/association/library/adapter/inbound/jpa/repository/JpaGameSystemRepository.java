
package com.bernardomg.association.library.adapter.inbound.jpa.repository;

import java.util.Objects;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import com.bernardomg.association.library.adapter.inbound.jpa.model.GameSystemEntity;
import com.bernardomg.association.library.domain.model.GameSystem;
import com.bernardomg.association.library.domain.repository.GameSystemRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Transactional
public final class JpaGameSystemRepository implements GameSystemRepository {

    private final GameSystemSpringRepository gameSystemRepository;

    public JpaGameSystemRepository(final GameSystemSpringRepository gameSystemRepo) {
        super();

        gameSystemRepository = Objects.requireNonNull(gameSystemRepo);
    }

    @Override
    public final void delete(final String name) {
        log.debug("Deleting game system {}", name);

        gameSystemRepository.deleteByName(name);

        log.debug("Deleted game system {}", name);
    }

    @Override
    public final boolean exists(final String name) {
        final boolean exists;

        log.debug("Checking if game system {} exists", name);

        exists = gameSystemRepository.existsByName(name);

        log.debug("Game system {} exists: {}", name, exists);

        return exists;
    }

    @Override
    public final Optional<GameSystem> findOne(final String name) {
        final Optional<GameSystem> gameSystem;

        log.debug("Finding game system with name {}", name);

        gameSystem = gameSystemRepository.findByName(name)
            .map(this::toDomain);

        log.debug("Found game system with name {}: {}", name, gameSystem);

        return gameSystem;
    }

    @Override
    public final Iterable<GameSystem> getAll(final Pageable pageable) {
        final Page<GameSystemEntity> page;
        final Iterable<GameSystem>   read;

        log.debug("Finding game systems with pagination {}", pageable);

        page = gameSystemRepository.findAll(pageable);

        read = page.map(this::toDomain);

        log.debug("Found game systems {}", read);

        return read;
    }

    @Override
    public final boolean hasRelationships(final String name) {
        final boolean exists;

        log.debug("Checking if game system {} has relationships", name);

        exists = gameSystemRepository.existsInBook(name);

        log.debug("Game system {} has relationships: {}", name, exists);

        return exists;
    }

    @Override
    public final GameSystem save(final GameSystem gameSystem) {
        final GameSystemEntity toCreate;
        final GameSystemEntity created;
        final GameSystem       saved;

        log.debug("Saving game system {}", gameSystem);

        toCreate = toEntity(gameSystem);

        created = gameSystemRepository.save(toCreate);
        saved = toDomain(created);

        log.debug("Saved game system {}", saved);

        return saved;
    }

    private final GameSystem toDomain(final GameSystemEntity entity) {
        return GameSystem.builder()
            .withName(entity.getName())
            .build();
    }

    private final GameSystemEntity toEntity(final GameSystem domain) {
        return GameSystemEntity.builder()
            .withName(domain.getName())
            .build();
    }

}
