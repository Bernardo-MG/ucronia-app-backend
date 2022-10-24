
package com.bernardomg.security.service;

import java.util.Optional;

import org.springframework.data.domain.Pageable;

import com.bernardomg.security.model.Privilege;

public interface PrivilegeService {

    /**
     * Returns all the privileges matching the sample. If the sample fields are empty, then all the privileges are
     * returned.
     *
     * @param sample
     *            sample for filtering
     * @param pageable
     *            pagination to apply
     * @return all the privileges matching the sample
     */
    public Iterable<? extends Privilege> getAll(final Privilege sample, final Pageable pageable);

    /**
     * Returns the privilege for the received id, if it exists. Otherwise an empty {@code Optional} is returned.
     *
     * @param id
     *            id of the privilege to acquire
     * @return an {@code Optional} with the privilege, if it exists, of an empty {@code Optional} otherwise
     */
    public Optional<? extends Privilege> getOne(final Long id);

}
