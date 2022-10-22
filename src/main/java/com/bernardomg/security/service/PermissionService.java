
package com.bernardomg.security.service;

import java.util.Optional;

import org.springframework.data.domain.Pageable;

import com.bernardomg.security.model.Permission;

public interface PermissionService {

    /**
     * Returns all the fees matching the sample. If the sample fields are empty, then all the fees are returned.
     *
     * @param sample
     *            sample for filtering
     * @param pageable
     *            pagination to apply
     * @return all the fees matching the sample
     */
    public Iterable<? extends Permission> getAll(final Permission sample, final Pageable pageable);

    /**
     * Returns the fee for the received id, if it exists. Otherwise an empty {@code Optional} is returned.
     *
     * @param id
     *            id of the fee to acquire
     * @return an {@code Optional} with the fee, if it exists, of an empty {@code Optional} otherwise
     */
    public Optional<? extends Permission> getOne(final Long id);

}
