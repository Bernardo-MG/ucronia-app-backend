
package com.bernardomg.association.library.usecase.service;

import java.util.Optional;

import org.springframework.data.domain.Pageable;

import com.bernardomg.association.library.domain.model.GameSystem;

public interface GameSystemService {

    public GameSystem createGameSystem(final GameSystem system);

    public void deleteGameSystem(final String name);

    public Iterable<GameSystem> getAllGameSystems(final Pageable pageable);

    public Optional<GameSystem> getOneGameSystem(final String name);

}
