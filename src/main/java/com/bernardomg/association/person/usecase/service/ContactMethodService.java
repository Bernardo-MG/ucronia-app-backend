
package com.bernardomg.association.person.usecase.service;

import java.util.Optional;

import com.bernardomg.association.person.domain.model.ContactMethod;
import com.bernardomg.data.domain.Pagination;
import com.bernardomg.data.domain.Sorting;

/**
 * Contact method service. Supports all the CRUD operations.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
public interface ContactMethodService {

    /**
     * Persists the received contact method.
     *
     * @param ContactMethod
     *            contact method to persist
     * @return the persisted contact method
     */
    public ContactMethod create(final ContactMethod ContactMethod);

    /**
     * Deletes the contact method with the received id.
     *
     * @param number
     *            number of the contact method to delete
     */
    public void delete(final long number);

    /**
     * Returns all the contact methods.
     *
     * @param pagination
     *            pagination to apply
     * @param sorting
     *            sorting to apply
     * @return all the contact methods matching the sample
     */
    public Iterable<ContactMethod> getAll(final Pagination pagination, final Sorting sorting);

    /**
     * Returns the contact method for the received id, if it exists. Otherwise an empty {@code Optional} is returned.
     *
     * @param number
     *            number of the contact method to acquire
     * @return an {@code Optional} with the contact method, if it exists, of an empty {@code Optional} otherwise
     */
    public Optional<ContactMethod> getOne(final long number);

    /**
     * Updates the contact method for the received id with the received data.
     *
     * @param ContactMethod
     *            new data for the contact method
     * @return the updated contact method
     */
    public ContactMethod update(final ContactMethod ContactMethod);

}
