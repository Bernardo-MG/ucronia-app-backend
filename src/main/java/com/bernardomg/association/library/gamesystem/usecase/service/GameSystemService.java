
package com.bernardomg.association.library.gamesystem.usecase.service;

import java.util.Optional;

import org.springframework.data.domain.Pageable;

import com.bernardomg.association.library.gamesystem.domain.model.GameSystem;

public interface GameSystemService {

    public GameSystem create(final GameSystem system);

    public void delete(final String name);

    public Iterable<GameSystem> getAll(final Pageable pageable);

    public Optional<GameSystem> getOne(final String name);

}
