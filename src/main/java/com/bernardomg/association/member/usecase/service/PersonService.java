
package com.bernardomg.association.member.usecase.service;

import java.util.Optional;

import org.springframework.data.domain.Pageable;

import com.bernardomg.association.member.domain.model.Person;

/**
 * Person service. Supports all the CRUD operations.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
public interface PersonService {

    /**
     * Persists the received person.
     *
     * @param person
     *            person to persist
     * @return the persisted person
     */
    public Person create(final Person person);

    /**
     * Deletes the person with the received id.
     *
     * @param number
     *            number of the person to delete
     */
    public void delete(final long number);

    /**
     * Returns all the persons matching the sample. If the sample fields are empty, then all the persons are returned.
     *
     * @param pageable
     *            pagination to apply
     * @return all the persons matching the sample
     */
    public Iterable<Person> getAll(final Pageable pageable);

    /**
     * Returns the person for the received id, if it exists. Otherwise an empty {@code Optional} is returned.
     *
     * @param number
     *            number of the person to acquire
     * @return an {@code Optional} with the person, if it exists, of an empty {@code Optional} otherwise
     */
    public Optional<Person> getOne(final long number);

    /**
     * Updates the person for the received id with the received data.
     *
     * @param person
     *            new data for the person
     * @return the updated person
     */
    public Person update(final Person person);

}
