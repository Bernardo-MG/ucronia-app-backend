
package com.bernardomg.association.member.usecase.service;

import java.util.Optional;

import org.springframework.data.domain.Pageable;

import com.bernardomg.association.member.domain.model.Member;
import com.bernardomg.association.member.domain.model.MemberQuery;

/**
 * Member service. Supports all the CRUD operations.
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
    public void delete(final long number);

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
