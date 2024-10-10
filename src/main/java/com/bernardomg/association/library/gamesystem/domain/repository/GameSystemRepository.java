
package com.bernardomg.association.library.gamesystem.domain.repository;

import java.util.Optional;

import org.springframework.data.domain.Pageable;

import com.bernardomg.association.library.gamesystem.domain.model.GameSystem;

public interface GameSystemRepository {

    public void delete(final Long number);

    public boolean exists(final Long number);

    public boolean existsByName(final String name);

    public boolean existsByNameForAnother(final String name, final Long number);

    public Iterable<GameSystem> findAll(final Pageable pageable);

    public long findNextNumber();

    public Optional<GameSystem> findOne(final Long number);

    public GameSystem save(final GameSystem gameSystem);

}
