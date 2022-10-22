
package com.bernardomg.security.profile.service;

import java.util.Optional;

import org.springframework.data.domain.Pageable;

import com.bernardomg.security.profile.model.SecurityProfile;

public interface SecurityProfileService {

    /**
     * Persists the received user.
     *
     * @param fee
     *            fee to persist
     * @return the persisted fee
     */
    public SecurityProfile create(final SecurityProfile fee);

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
    public Iterable<? extends SecurityProfile> getAll(final SecurityProfile sample, final Pageable pageable);

    /**
     * Returns the fee for the received id, if it exists. Otherwise an empty {@code Optional} is returned.
     *
     * @param id
     *            id of the fee to acquire
     * @return an {@code Optional} with the fee, if it exists, of an empty {@code Optional} otherwise
     */
    public Optional<? extends SecurityProfile> getOne(final Long id);

    /**
     * Updates the fee for the received id with the received data.
     *
     * @param id
     *            id of the fee to update
     * @param fee
     *            new data for the fee
     * @return the updated fee
     */
    public SecurityProfile update(final Long id, final SecurityProfile fee);

}
