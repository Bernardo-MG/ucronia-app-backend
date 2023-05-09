
package com.bernardomg.security.data.service;

import java.util.Optional;

import org.springframework.data.domain.Pageable;

import com.bernardomg.security.data.model.Action;

public interface ActionService {

    /**
     * Returns all the action matching the sample. If the sample fields are empty, then all the action are returned.
     *
     * @param sample
     *            sample for filtering
     * @param pageable
     *            pagination to apply
     * @return all the action matching the sample
     */
    public Iterable<? extends Action> getAll(final Action sample, final Pageable pageable);

    /**
     * Returns the action for the received id, if it exists. Otherwise an empty {@code Optional} is returned.
     *
     * @param id
     *            id of the action to acquire
     * @return an {@code Optional} with the action, if it exists, of an empty {@code Optional} otherwise
     */
    public Optional<? extends Action> getOne(final Long id);

}
