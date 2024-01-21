
package com.bernardomg.association.member.usecase.service;

import java.util.Optional;

import org.springframework.data.domain.Pageable;

import com.bernardomg.association.member.domain.model.Member;
import com.bernardomg.association.member.infra.outbound.model.MemberChange;
import com.bernardomg.association.member.infra.outbound.model.MemberQuery;

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
    public Member create(final MemberChange member);

    /**
     * Deletes the member with the received id.
     *
     * @param memberNumber
     *            number of the member to delete
     */
    public void delete(final long memberNumber);

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
     * @param memberNumber
     *            number of the member to acquire
     * @return an {@code Optional} with the member, if it exists, of an empty {@code Optional} otherwise
     */
    public Optional<Member> getOne(final long memberNumber);

    /**
     * Updates the member for the received id with the received data.
     *
     * @param memberNumber
     *            number of the member to update
     * @param member
     *            new data for the member
     * @return the updated member
     */
    public Member update(final long memberNumber, final MemberChange member);

}
