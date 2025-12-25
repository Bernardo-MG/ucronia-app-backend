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

package com.bernardomg.association.member.usecase.service;

import java.util.Optional;

import com.bernardomg.association.member.domain.filter.MemberFilter;
import com.bernardomg.association.member.domain.model.MemberContact;
import com.bernardomg.data.domain.Page;
import com.bernardomg.data.domain.Pagination;
import com.bernardomg.data.domain.Sorting;

/**
 * MemberContact service. Without sensitive information.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
public interface MemberContactService {

    /**
     * Persists the received memberContact.
     *
     * @param memberContact
     *            memberContact to persist
     * @return the persisted memberContact
     */
    public MemberContact create(final MemberContact memberContact);

    /**
     * Deletes the memberContact with the received id.
     *
     * @param number
     *            number of the memberContact to delete
     */
    public MemberContact delete(final long number);

    /**
     * Returns all the public memberContacts.
     *
     * @param filter
     *            query to filter by
     * @param pagination
     *            pagination to apply
     * @param sorting
     *            sorting to apply
     * @return all the memberContacts matching the sample
     */
    public Page<MemberContact> getAll(final MemberFilter filter, final Pagination pagination, final Sorting sorting);

    /**
     * Returns the memberContact for the received id, if it exists. Otherwise an empty {@code Optional} is returned.
     *
     * @param number
     *            number of the memberContact to acquire
     * @return an {@code Optional} with the memberContact, if it exists, of an empty {@code Optional} otherwise
     */
    public Optional<MemberContact> getOne(final long number);

    /**
     * Patches the memberContact for the received id with the received data.
     *
     * @param memberContact
     *            new data for the memberContact
     * @return the updated memberContact
     */
    public MemberContact patch(final MemberContact memberContact);

    /**
     * Updates the memberContact for the received id with the received data.
     *
     * @param memberContact
     *            new data for the memberContact
     * @return the updated memberContact
     */
    public MemberContact update(final MemberContact memberContact);

}
