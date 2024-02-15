
package com.bernardomg.association.library.domain.repository;

import com.bernardomg.association.library.domain.model.GameSystem;

public interface GameSystemRepository {

    public boolean exists(final String name);

    public GameSystem save(final GameSystem book);

}
