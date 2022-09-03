
package com.bernardomg.association.member.service;

import java.util.Optional;

import com.bernardomg.association.member.model.Member;
import com.bernardomg.mvc.pagination.model.Pagination;
import com.bernardomg.mvc.pagination.model.Sort;

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
     * @param pagination
     *            pagination to apply
     * @param sort
     *            sorting to apply
     * @return all the members matching the sample
     */
    public Iterable<? extends Member> getAll(final Member sample, final Pagination pagination, final Sort sort);

    /**
     * Returns the member for the received id, if it exists. Otherwise an empty {@code Optional} is returned.
     *
     * @param id
     *            id of the member to acquire
     * @return an {@code Optional} with the member, if it exists, of an empty {@code Optional} otherwise
     */
    public Optional<? extends Member> getOne(final Long id);

    /**
     * Updates the member for the received id with the received data.
     *
     * @param id
     *            id of the member to update
     * @param member
     *            new data for the member
     * @return the updated member
     */
    public Member update(final Long id, final Member member);

}
