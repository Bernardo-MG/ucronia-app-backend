
package com.bernardomg.association.library.gamesystem.domain.repository;

import java.util.Optional;

import com.bernardomg.association.library.gamesystem.domain.model.GameSystem;
import com.bernardomg.data.domain.Page;
import com.bernardomg.data.domain.Pagination;
import com.bernardomg.data.domain.Sorting;

public interface GameSystemRepository {

    public void delete(final long number);

    public boolean exists(final long number);

    public boolean existsByName(final String name);

    public boolean existsByNameForAnother(final String name, final long number);

    public Page<GameSystem> findAll(final Pagination pagination, final Sorting sorting);

    public long findNextNumber();

    public Optional<GameSystem> findOne(final long number);

    public GameSystem save(final GameSystem gameSystem);

}
