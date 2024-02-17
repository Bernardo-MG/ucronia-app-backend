
package com.bernardomg.association.library.domain.repository;

import java.util.Optional;

import org.springframework.data.domain.Pageable;

import com.bernardomg.association.library.domain.model.GameSystem;

public interface GameSystemRepository {

    public boolean exists(final String name);

    public Iterable<GameSystem> findAll(final Pageable pageable);

    public Optional<GameSystem> findOne(final String name);

    public GameSystem save(final GameSystem gameSystem);

}
