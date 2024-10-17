
package com.bernardomg.association.member.usecase.service;

import java.util.Optional;

import org.springframework.data.domain.Pageable;

import com.bernardomg.association.member.domain.model.Member;
import com.bernardomg.association.member.domain.model.MemberQuery;

/**
 * Reduced member service. Reduces only non sensible information.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
public interface PublicMemberService {

    /**
     * Returns all the members matching the sample. If the sample fields are empty, then all the members are returned.
     *
     * @param query
     *            query for filtering
     * @param pageable
     *            pagination to apply
     * @return all the members matching the sample
     */
    public Iterable<Member> getAll(final MemberQuery query, final Pageable pageable);

    /**
     * Returns the member for the received id, if it exists. Otherwise an empty {@code Optional} is returned.
     *
     * @param number
     *            number of the member to acquire
     * @return an {@code Optional} with the member, if it exists, of an empty {@code Optional} otherwise
     */
    public Optional<Member> getOne(final long number);

}
