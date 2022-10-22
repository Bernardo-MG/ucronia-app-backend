
package com.bernardomg.security.service;

import java.util.Optional;

import org.springframework.data.domain.Pageable;

import com.bernardomg.security.model.Role;

public interface RoleService {

    /**
     * Persists the received user.
     *
     * @param role
     *            fee to persist
     * @return the persisted fee
     */
    public Role create(final Role role);

    /**
     * Deletes the fee with the received id.
     *
     * @param id
     *            id of the fee to delete
     * @return {@code true} if it managed to delete, {@code false} otherwise
     */
    public Boolean delete(final Long id);

    /**
     * Returns all the fees matching the sample. If the sample fields are empty, then all the fees are returned.
     *
     * @param sample
     *            sample for filtering
     * @param pageable
     *            pagination to apply
     * @return all the fees matching the sample
     */
    public Iterable<? extends Role> getAll(final Role sample, final Pageable pageable);

    /**
     * Returns the fee for the received id, if it exists. Otherwise an empty {@code Optional} is returned.
     *
     * @param id
     *            id of the fee to acquire
     * @return an {@code Optional} with the fee, if it exists, of an empty {@code Optional} otherwise
     */
    public Optional<? extends Role> getOne(final Long id);

    /**
     * Updates the fee for the received id with the received data.
     *
     * @param id
     *            id of the fee to update
     * @param role
     *            new data for the fee
     * @return the updated fee
     */
    public Role update(final Long id, final Role role);

}
