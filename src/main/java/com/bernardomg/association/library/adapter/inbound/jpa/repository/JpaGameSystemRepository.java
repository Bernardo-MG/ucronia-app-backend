
package com.bernardomg.association.library.adapter.inbound.jpa.repository;

import org.springframework.data.domain.Pageable;

import com.bernardomg.association.library.adapter.inbound.jpa.model.GameSystemEntity;
import com.bernardomg.association.library.domain.model.GameSystem;
import com.bernardomg.association.library.domain.repository.GameSystemRepository;

public final class JpaGameSystemRepository implements GameSystemRepository {

    private final GameSystemSpringRepository gameSystemRepository;

    public JpaGameSystemRepository(final GameSystemSpringRepository gameSystemRepo) {
        super();

        gameSystemRepository = gameSystemRepo;
    }

    @Override
    public final boolean exists(final String name) {
        return gameSystemRepository.existsByName(name);
    }

    @Override
    public final Iterable<GameSystem> findAll(final Pageable pageable) {
        return gameSystemRepository.findAll(pageable)
            .stream()
            .map(this::toDomain)
            .toList();
    }

    @Override
    public final GameSystem save(final GameSystem book) {
        final GameSystemEntity toCreate;
        final GameSystemEntity created;

        toCreate = toEntity(book);
        created = gameSystemRepository.save(toCreate);

        return toDomain(created);
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
