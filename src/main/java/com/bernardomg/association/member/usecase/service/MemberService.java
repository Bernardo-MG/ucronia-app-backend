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

import com.bernardomg.association.member.domain.filter.MemberQuery;
import com.bernardomg.association.member.domain.model.Member;
import com.bernardomg.data.domain.Page;
import com.bernardomg.data.domain.Pagination;
import com.bernardomg.data.domain.Sorting;

/**
 * Reduced member service. Reduces only non sensible information.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
public interface MemberService {

    /**
     * Persists the received member.
     *
     * @param member
     *            member to persist
     * @return the persisted member
     */
    public Member create(final Member member);

    /**
     * Deletes the member with the received id.
     *
     * @param number
     *            number of the member to delete
     */
    public Member delete(final long number);

    /**
     * Returns all the members matching the filter.
     *
     * @param query
     *            filter to apply
     * @param pagination
     *            pagination to apply
     * @param sorting
     *            sorting to apply
     * @return all the members matching the sample
     */
    public Page<Member> getAll(final MemberQuery query, final Pagination pagination, final Sorting sorting);

    /**
     * Returns the member for the received id, if it exists. Otherwise an empty {@code Optional} is returned.
     *
     * @param number
     *            number of the member to acquire
     * @return an {@code Optional} with the member, if it exists, of an empty {@code Optional} otherwise
     */
    public Optional<Member> getOne(final long number);

    /**
     * Patches the member for the received id with the received data.
     *
     * @param member
     *            new data for the member
     * @return the updated member
     */
    public Member patch(final Member member);

    /**
     * Updates the member for the received id with the received data.
     *
     * @param member
     *            new data for the member
     * @return the updated member
     */
    public Member update(final Member member);

}
