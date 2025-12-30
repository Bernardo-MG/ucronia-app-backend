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

package com.bernardomg.association.profile.usecase.service;

import java.util.Optional;

import com.bernardomg.association.profile.domain.filter.ProfileQuery;
import com.bernardomg.association.profile.domain.model.Profile;
import com.bernardomg.data.domain.Page;
import com.bernardomg.data.domain.Pagination;
import com.bernardomg.data.domain.Sorting;

/**
 * Profile service.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
public interface ProfileService {

    /**
     * Persists the received profile.
     *
     * @param profile
     *            profile to persist
     * @return the persisted profile
     */
    public Profile create(final Profile profile);

    /**
     * Deletes the profile with the received id.
     *
     * @param number
     *            number of the profile to delete
     */
    public Profile delete(final long number);

    /**
     * Returns all the profiles matching the query. If the query fields are empty, then all the profiles are returned.
     *
     * @param query
     *            query to search for
     * @param pagination
     *            pagination to apply
     * @param sorting
     *            sorting to apply
     * @return all the profiles matching the sample
     */
    public Page<Profile> getAll(final ProfileQuery query, final Pagination pagination, final Sorting sorting);

    /**
     * Returns the profile for the received id, if it exists. Otherwise an empty {@code Optional} is returned.
     *
     * @param number
     *            number of the profile to acquire
     * @return an {@code Optional} with the profile, if it exists, of an empty {@code Optional} otherwise
     */
    public Optional<Profile> getOne(final long number);

    /**
     * Patches the profile for the received id with the received data.
     *
     * @param profile
     *            new data for the profile
     * @return the updated profile
     */
    public Profile patch(final Profile profile);

    /**
     * Updates the profile for the received id with the received data.
     *
     * @param profile
     *            new data for the profile
     * @return the updated profile
     */
    public Profile update(final Profile profile);

}
