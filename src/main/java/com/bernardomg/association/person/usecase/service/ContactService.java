/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2022-2025 Bernardo Martínez Garrido
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.bernardomg.association.person.usecase.service;

import java.util.Optional;

import com.bernardomg.association.person.domain.filter.ContactFilter;
import com.bernardomg.association.person.domain.model.Contact;
import com.bernardomg.data.domain.Page;
import com.bernardomg.data.domain.Pagination;
import com.bernardomg.data.domain.Sorting;

/**
 * Contact service. Supports all the CRUD operations.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
public interface ContactService {

    /**
     * Persists the received person.
     *
     * @param person
     *            person to persist
     * @return the persisted person
     */
    public Contact create(final Contact contact);

    /**
     * Deletes the person with the received id.
     *
     * @param number
     *            number of the person to delete
     */
    public Contact delete(final long number);

    /**
     * Returns all the persons matching the query. If the sample fields are empty, then all the persons are returned.
     *
     * @param filter
     *            filter to apply
     * @param pagination
     *            pagination to apply
     * @param sorting
     *            sorting to apply
     * @return all the persons matching the sample
     */
    public Page<Contact> getAll(final ContactFilter filter, final Pagination pagination, final Sorting sorting);

    /**
     * Returns the person for the received id, if it exists. Otherwise an empty {@code Optional} is returned.
     *
     * @param number
     *            number of the person to acquire
     * @return an {@code Optional} with the person, if it exists, of an empty {@code Optional} otherwise
     */
    public Optional<Contact> getOne(final long number);

    /**
     * Patches the person for the received id with the received data.
     *
     * @param person
     *            new data for the person
     * @return the updated person
     */
    public Contact patch(final Contact contact);

    /**
     * Updates the person for the received id with the received data.
     *
     * @param person
     *            new data for the person
     * @return the updated person
     */
    public Contact update(final Contact contact);

}
