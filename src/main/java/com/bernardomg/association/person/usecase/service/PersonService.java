
package com.bernardomg.association.person.usecase.service;

import java.util.Optional;

import com.bernardomg.association.person.domain.model.Person;
import com.bernardomg.association.person.domain.query.PersonQuery;
import com.bernardomg.data.domain.Pagination;
import com.bernardomg.data.domain.Sorting;

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
     * Returns all the persons matching the query. If the sample fields are empty, then all the persons are returned.
     *
     * @param query
     *            query to filter by
     * @param pagination
     *            pagination to apply
     * @param sorting
     *            sorting to apply
     * @return all the persons matching the sample
     */
    public Iterable<Person> getAll(final PersonQuery query, final Pagination pagination, final Sorting sorting);

    /**
     * Returns the person for the received id, if it exists. Otherwise an empty {@code Optional} is returned.
     *
     * @param number
     *            number of the person to acquire
     * @return an {@code Optional} with the person, if it exists, of an empty {@code Optional} otherwise
     */
    public Optional<Person> getOne(final long number);

    /**
     * Patches the person for the received id with the received data.
     *
     * @param person
     *            new data for the person
     * @return the updated member
     */
    public Person patch(final Person person);

    /**
     * Updates the person for the received id with the received data.
     *
     * @param person
     *            new data for the person
     * @return the updated person
     */
    public Person update(final Person person);

}
