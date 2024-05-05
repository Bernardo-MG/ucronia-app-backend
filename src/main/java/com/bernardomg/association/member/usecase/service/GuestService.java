
package com.bernardomg.association.member.usecase.service;

import java.util.Optional;

import org.springframework.data.domain.Pageable;

import com.bernardomg.association.member.domain.model.Guest;

/**
 * Guest service. Supports all the CRUD operations.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
public interface GuestService {

    /**
     * Persists the received guest.
     *
     * @param guest
     *            guest to persist
     * @return the persisted guest
     */
    public Guest create(final Guest guest);

    /**
     * Deletes the guest with the received id.
     *
     * @param number
     *            number of the guest to delete
     */
    public void delete(final long number);

    /**
     * Returns all the guests matching the sample. If the sample fields are empty, then all the guests are returned.
     *
     * @param pageable
     *            pagination to apply
     * @return all the guests matching the sample
     */
    public Iterable<Guest> getAll(final Pageable pageable);

    /**
     * Returns the guest for the received id, if it exists. Otherwise an empty {@code Optional} is returned.
     *
     * @param number
     *            number of the guest to acquire
     * @return an {@code Optional} with the guest, if it exists, of an empty {@code Optional} otherwise
     */
    public Optional<Guest> getOne(final long number);

    /**
     * Updates the guest for the received id with the received data.
     *
     * @param guest
     *            new data for the guest
     * @return the updated guest
     */
    public Guest update(final Guest guest);

}
