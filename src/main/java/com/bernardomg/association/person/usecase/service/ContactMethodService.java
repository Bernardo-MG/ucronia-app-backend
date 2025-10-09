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

import com.bernardomg.association.person.domain.model.ContactMethod;
import com.bernardomg.data.domain.Page;
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
     * @return the deleted contact method
     */
    public ContactMethod delete(final long number);

    /**
     * Returns all the contact methods.
     *
     * @param pagination
     *            pagination to apply
     * @param sorting
     *            sorting to apply
     * @return all the contact methods matching the sample
     */
    public Page<ContactMethod> getAll(final Pagination pagination, final Sorting sorting);

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
