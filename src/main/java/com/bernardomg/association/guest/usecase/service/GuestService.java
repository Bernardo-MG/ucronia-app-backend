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

package com.bernardomg.association.guest.usecase.service;

import java.util.Optional;

import com.bernardomg.association.guest.domain.filter.GuestFilter;
import com.bernardomg.association.guest.domain.model.Guest;
import com.bernardomg.data.domain.Page;
import com.bernardomg.data.domain.Pagination;
import com.bernardomg.data.domain.Sorting;

/**
 * Guest service. Without sensitive information.
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
    public Guest delete(final long number);

    /**
     * Returns all the public guests.
     *
     * @param filter
     *            query to filter by
     * @param pagination
     *            pagination to apply
     * @param sorting
     *            sorting to apply
     * @return all the guests matching the sample
     */
    public Page<Guest> getAll(final GuestFilter filter, final Pagination pagination, final Sorting sorting);

    /**
     * Returns the guest for the received id, if it exists. Otherwise an empty {@code Optional} is returned.
     *
     * @param number
     *            number of the guest to acquire
     * @return an {@code Optional} with the guest, if it exists, of an empty {@code Optional} otherwise
     */
    public Optional<Guest> getOne(final long number);

    /**
     * Patches the guest for the received id with the received data.
     *
     * @param guest
     *            new data for the guest
     * @return the updated guest
     */
    public Guest patch(final Guest guest);

    /**
     * Updates the guest for the received id with the received data.
     *
     * @param guest
     *            new data for the guest
     * @return the updated guest
     */
    public Guest update(final Guest guest);

}
