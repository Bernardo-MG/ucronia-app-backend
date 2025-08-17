
package com.bernardomg.association.library.gamesystem.usecase.service;

import java.util.Optional;

import com.bernardomg.association.library.gamesystem.domain.model.GameSystem;
import com.bernardomg.data.domain.Page;
import com.bernardomg.data.domain.Pagination;
import com.bernardomg.data.domain.Sorting;

public interface GameSystemService {

    public GameSystem create(final GameSystem system);

    public void delete(final Long number);

    public Page<GameSystem> getAll(final Pagination pagination, final Sorting sorting);

    public Optional<GameSystem> getOne(final Long number);

    public GameSystem update(final GameSystem system);

}
