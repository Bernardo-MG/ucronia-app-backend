
package com.bernardomg.association.membership.member.service;

import java.util.Optional;

import org.springframework.data.domain.Pageable;

import com.bernardomg.association.membership.member.model.Member;
import com.bernardomg.association.membership.member.model.request.MemberCreate;
import com.bernardomg.association.membership.member.model.request.MemberQuery;
import com.bernardomg.association.membership.member.model.request.MemberUpdate;

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
    public Member create(final MemberCreate member);

    /**
     * Deletes the member with the received id.
     *
     * @param id
     *            id of the member to delete
     */
    public void delete(final long id);

    /**
     * Returns all the members matching the sample. If the sample fields are empty, then all the members are returned.
     *
     * @param sample
     *            sample for filtering
     * @param pageable
     *            pagination to apply
     * @return all the members matching the sample
     */
    public Iterable<Member> getAll(final MemberQuery sample, final Pageable pageable);

    /**
     * Returns the member for the received id, if it exists. Otherwise an empty {@code Optional} is returned.
     *
     * @param id
     *            id of the member to acquire
     * @return an {@code Optional} with the member, if it exists, of an empty {@code Optional} otherwise
     */
    public Optional<Member> getOne(final long id);

    /**
     * Updates the member for the received id with the received data.
     *
     * @param id
     *            id of the member to update
     * @param member
     *            new data for the member
     * @return the updated member
     */
    public Member update(final long id, final MemberUpdate member);

}
