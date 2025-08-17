
package com.bernardomg.association.member.usecase.service;

import java.util.Optional;

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
     * Returns all the members matching the sample. If the sample fields are empty, then all the members are returned.
     *
     * @param pagination
     *            pagination to apply
     * @param sorting
     *            sorting to apply
     * @return all the members matching the sample
     */
    public Page<Member> getAll(final Pagination pagination, final Sorting sorting);

    /**
     * Returns the member for the received id, if it exists. Otherwise an empty {@code Optional} is returned.
     *
     * @param number
     *            number of the member to acquire
     * @return an {@code Optional} with the member, if it exists, of an empty {@code Optional} otherwise
     */
    public Optional<Member> getOne(final long number);

}
