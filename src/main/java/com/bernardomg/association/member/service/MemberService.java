
package com.bernardomg.association.member.service;

import java.util.Optional;

import com.bernardomg.association.member.model.Member;

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

    public Iterable<? extends Member> getAll(final Member sample);

    public Optional<? extends Member> getOne(final Long id);

    public Member update(final Long id, final Member member);

}
