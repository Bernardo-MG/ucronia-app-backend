
package com.bernardomg.association.member.service;

import java.util.Optional;

import org.springframework.data.domain.Pageable;

import com.bernardomg.association.member.model.Member;
import com.bernardomg.association.member.model.request.MemberCreateRequest;
import com.bernardomg.association.member.model.request.MemberQueryRequest;
import com.bernardomg.association.member.model.request.MemberUpdateRequest;

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
    public Member create(final MemberCreateRequest member);

    /**
     * Deletes the member with the received id.
     *
     * @param id
     *            id of the member to delete
     * @return {@code true} if it managed to delete, {@code false} otherwise
     */
    public Boolean delete(final Long id);

    /**
     * Returns all the members matching the sample. If the sample fields are empty, then all the members are returned.
     *
     * @param sample
     *            sample for filtering
     * @param pageable
     *            pagination to apply
     * @return all the members matching the sample
     */
    public Iterable<Member> getAll(final MemberQueryRequest sample, final Pageable pageable);

    /**
     * Returns the member for the received id, if it exists. Otherwise an empty {@code Optional} is returned.
     *
     * @param id
     *            id of the member to acquire
     * @return an {@code Optional} with the member, if it exists, of an empty {@code Optional} otherwise
     */
    public Optional<Member> getOne(final Long id);

    /**
     * Updates the member for the received id with the received data.
     *
     * @param id
     *            id of the member to update
     * @param member
     *            new data for the member
     * @return the updated member
     */
    public Member update(final Long id, final MemberUpdateRequest member);

}
