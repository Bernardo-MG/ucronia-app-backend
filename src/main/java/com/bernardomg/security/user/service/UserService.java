
package com.bernardomg.security.user.service;

import java.util.Optional;

import org.springframework.data.domain.Pageable;

import com.bernardomg.security.user.model.Role;
import com.bernardomg.security.user.model.User;
import com.bernardomg.security.user.model.request.UserCreate;
import com.bernardomg.security.user.model.request.UserQuery;
import com.bernardomg.security.user.model.request.UserUpdate;

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
    public Boolean addRole(final long id, final long role);

    /**
     * Persists the received user.
     *
     * @param user
     *            user to persist
     * @return the persisted user
     */
    public User create(final UserCreate user);

    /**
     * Deletes the user with the received id.
     *
     * @param id
     *            id of the user to delete
     */
    public void delete(final long id);

    /**
     * Returns all the users matching the sample. If the sample fields are empty, then all the users are returned.
     *
     * @param sample
     *            sample for filtering
     * @param pageable
     *            pagination to apply
     * @return all the users matching the sample
     */
    public Iterable<User> getAll(final UserQuery sample, final Pageable pageable);

    /**
     * Returns the user for the received id, if it exists. Otherwise an empty {@code Optional} is returned.
     *
     * @param id
     *            id of the user to acquire
     * @return an {@code Optional} with the user, if it exists, of an empty {@code Optional} otherwise
     */
    public Optional<User> getOne(final long id);

    /**
     * Returns all the roles for the user.
     *
     * @param id
     *            user id
     * @param pageable
     *            pagination to apply
     * @return roles for the rules
     */
    public Iterable<Role> getRoles(final long id, final Pageable pageable);

    /**
     * Removes a role from a user.
     *
     * @param id
     *            user id
     * @param role
     *            role id to remove
     * @return {@code true} if it managed to remove the role, {@code false} otherwise
     */
    public Boolean removeRole(final long id, final long role);

    /**
     * Updates the user for the received id with the received data.
     *
     * @param id
     *            id of the user to update
     * @param user
     *            new data for the user
     * @return the updated user
     */
    public User update(final long id, final UserUpdate user);

}
