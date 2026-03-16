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

import com.bernardomg.association.member.domain.filter.MemberProfileFilter;
import com.bernardomg.association.member.domain.model.MemberProfile;
import com.bernardomg.data.domain.Page;
import com.bernardomg.data.domain.Pagination;
import com.bernardomg.data.domain.Sorting;

/**
 * Member profile service.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
public interface MemberProfileService {

    /**
     * Persists the received member profile.
     *
     * @param memberProfile
     *            memberProfile to persist
     * @return the persisted member profile
     */
    public MemberProfile create(final MemberProfile memberProfile);

    /**
     * Deletes the member profile with the received id.
     *
     * @param number
     *            number of the member profile to delete
     */
    public MemberProfile delete(final long number);

    /**
     * Returns all the member profiles.
     *
     * @param filter
     *            query to filter by
     * @param pagination
     *            pagination to apply
     * @param sorting
     *            sorting to apply
     * @return all the member profiles matching the sample
     */
    public Page<MemberProfile> getAll(final MemberProfileFilter filter, final Pagination pagination,
            final Sorting sorting);

    /**
     * Returns the memberProfile for the received id, if it exists. Otherwise an empty {@code Optional} is returned.
     *
     * @param number
     *            number of the member profile to acquire
     * @return an {@code Optional} with the memberProfile, if it exists, of an empty {@code Optional} otherwise
     */
    public Optional<MemberProfile> getOne(final long number);

    /**
     * Patches the memberProfile for the received id with the received data.
     *
     * @param memberProfile
     *            new data for the member profile
     * @return the updated memberProfile
     */
    public MemberProfile patch(final MemberProfile memberProfile);

    /**
     * Updates the memberProfile for the received id with the received data.
     *
     * @param memberProfile
     *            new data for the member profile
     * @return the updated memberProfile
     */
    public MemberProfile update(final MemberProfile memberProfile);

}
