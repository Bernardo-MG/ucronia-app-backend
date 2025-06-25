
package com.bernardomg.association.person.usecase.service;

import java.util.Optional;

import com.bernardomg.association.person.domain.model.ContactMode;
import com.bernardomg.data.domain.Pagination;
import com.bernardomg.data.domain.Sorting;

/**
 * Contact mode service. Supports all the CRUD operations.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
public interface ContactModeService {

    /**
     * Persists the received contact mode.
     *
     * @param contactMode
     *            contact mode to persist
     * @return the persisted contact mode
     */
    public ContactMode create(final ContactMode contactMode);

    /**
     * Deletes the contact mode with the received id.
     *
     * @param number
     *            number of the contact mode to delete
     */
    public void delete(final long number);

    /**
     * Returns all the contact modes.
     *
     * @param pagination
     *            pagination to apply
     * @param sorting
     *            sorting to apply
     * @return all the contact modes matching the sample
     */
    public Iterable<ContactMode> getAll(final Pagination pagination, final Sorting sorting);

    /**
     * Returns the contact mode for the received id, if it exists. Otherwise an empty {@code Optional} is returned.
     *
     * @param number
     *            number of the contact mode to acquire
     * @return an {@code Optional} with the contact mode, if it exists, of an empty {@code Optional} otherwise
     */
    public Optional<ContactMode> getOne(final long number);

    /**
     * Patches the contact mode for the received id with the received data.
     *
     * @param contactMode
     *            new data for the contact mode
     * @return the updated contact mode
     */
    public ContactMode patch(final ContactMode contactMode);

    /**
     * Updates the contact mode for the received id with the received data.
     *
     * @param contactMode
     *            new data for the contact mode
     * @return the updated contact mode
     */
    public ContactMode update(final ContactMode contactMode);

}
