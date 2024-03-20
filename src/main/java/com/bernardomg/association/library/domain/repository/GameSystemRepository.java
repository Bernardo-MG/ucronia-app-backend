
package com.bernardomg.association.library.domain.repository;

import java.util.Optional;

import org.springframework.data.domain.Pageable;

import com.bernardomg.association.library.domain.model.GameSystem;

public interface GameSystemRepository {

    public void delete(final String name);

    public boolean exists(final String name);

    public Optional<GameSystem> findOne(final String name);

    public Iterable<GameSystem> getAll(final Pageable pageable);

    public GameSystem save(final GameSystem gameSystem);

}
