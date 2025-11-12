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

package com.bernardomg.association.contact.usecase.service;

import java.util.Optional;

import com.bernardomg.association.contact.domain.filter.ContactQuery;
import com.bernardomg.association.contact.domain.model.Contact;
import com.bernardomg.data.domain.Page;
import com.bernardomg.data.domain.Pagination;
import com.bernardomg.data.domain.Sorting;

/**
 * Contact service.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
public interface ContactService {

    /**
     * Persists the received contact.
     *
     * @param contact
     *            contact to persist
     * @return the persisted contact
     */
    public Contact create(final Contact contact);

    /**
     * Deletes the contact with the received id.
     *
     * @param number
     *            number of the contact to delete
     */
    public Contact delete(final long number);

    /**
     * Returns all the contacts matching the query. If the query fields are empty, then all the contacts are returned.
     *
     * @param query
     *            query to search for
     * @param pagination
     *            pagination to apply
     * @param sorting
     *            sorting to apply
     * @return all the contacts matching the sample
     */
    public Page<Contact> getAll(final ContactQuery query, final Pagination pagination, final Sorting sorting);

    /**
     * Returns the contact for the received id, if it exists. Otherwise an empty {@code Optional} is returned.
     *
     * @param number
     *            number of the contact to acquire
     * @return an {@code Optional} with the contact, if it exists, of an empty {@code Optional} otherwise
     */
    public Optional<Contact> getOne(final long number);

    /**
     * Patches the contact for the received id with the received data.
     *
     * @param contact
     *            new data for the contact
     * @return the updated contact
     */
    public Contact patch(final Contact contact);

    /**
     * Updates the contact for the received id with the received data.
     *
     * @param contact
     *            new data for the contact
     * @return the updated contact
     */
    public Contact update(final Contact contact);

}
