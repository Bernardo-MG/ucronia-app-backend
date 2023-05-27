
package com.bernardomg.security.data.service;

import java.util.Optional;

import org.springframework.data.domain.Pageable;

import com.bernardomg.security.data.model.Role;
import com.bernardomg.security.data.model.User;

public interface UserService {

    /**
     * Adds a role to a user.
     *
     * @param id
     *            user id
     * @param role
     *            role id to add
     * @return {@code true} if it managed to add the role, {@code false} otherwise
     */
    public Boolean addRole(final Long id, final Long role);

    /**
     * Persists the received user.
     *
     * @param user
     *            user to persist
     * @return the persisted user
     */
    public User create(final User user);

    /**
     * Deletes the user with the received id.
     *
     * @param id
     *            id of the user to delete
     * @return {@code true} if it managed to delete, {@code false} otherwise
     */
    public Boolean delete(final Long id);

    /**
     * Returns all the users matching the sample. If the sample fields are empty, then all the users are returned.
     *
     * @param sample
     *            sample for filtering
     * @param pageable
     *            pagination to apply
     * @return all the users matching the sample
     */
    public Iterable<User> getAll(final User sample, final Pageable pageable);

    /**
     * Returns the user for the received id, if it exists. Otherwise an empty {@code Optional} is returned.
     *
     * @param id
     *            id of the user to acquire
     * @return an {@code Optional} with the user, if it exists, of an empty {@code Optional} otherwise
     */
    public Optional<User> getOne(final Long id);

    /**
     * Returns all the roles for the user.
     *
     * @param id
     *            user id
     * @param pageable
     *            pagination to apply
     * @return roles for the rules
     */
    public Iterable<Role> getRoles(final Long id, final Pageable pageable);

    /**
     * Removes a role from a user.
     *
     * @param id
     *            user id
     * @param role
     *            role id to remove
     * @return {@code true} if it managed to remove the role, {@code false} otherwise
     */
    public Boolean removeRole(final Long id, final Long role);

    /**
     * Updates the user for the received id with the received data.
     *
     * @param user
     *            new data for the user
     * @return the updated user
     */
    public User update(final User user);

}
