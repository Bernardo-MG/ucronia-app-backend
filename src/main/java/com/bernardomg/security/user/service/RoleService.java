
package com.bernardomg.security.user.service;

import java.util.Optional;

import org.springframework.data.domain.Pageable;

import com.bernardomg.security.user.model.Permission;
import com.bernardomg.security.user.model.Role;
import com.bernardomg.security.user.model.request.RoleCreate;
import com.bernardomg.security.user.model.request.RoleQuery;
import com.bernardomg.security.user.model.request.RoleUpdate;

public interface RoleService {

    /**
     * Adds a action to a role.
     *
     * @param id
     *            role id
     * @param resource
     *            resource id to add
     * @param action
     *            action id to add
     * @return {@code true} if it managed to add the action, {@code false} otherwise
     */
    public Boolean addPermission(final Long id, final Long resource, final Long action);

    /**
     * Persists the received user.
     *
     * @param role
     *            role to persist
     * @return the persisted role
     */
    public Role create(final RoleCreate role);

    /**
     * Deletes the role with the received id.
     *
     * @param id
     *            id of the role to delete
     * @return {@code true} if it managed to delete, {@code false} otherwise
     */
    public Boolean delete(final Long id);

    /**
     * Returns all the roles matching the sample. If the sample fields are empty, then all the roles are returned.
     *
     * @param sample
     *            sample for filtering
     * @param pageable
     *            pagination to apply
     * @return all the roles matching the sample
     */
    public Iterable<Role> getAll(final RoleQuery sample, final Pageable pageable);

    /**
     * Returns the role for the received id, if it exists. Otherwise an empty {@code Optional} is returned.
     *
     * @param id
     *            id of the role to acquire
     * @return an {@code Optional} with the role, if it exists, of an empty {@code Optional} otherwise
     */
    public Optional<Role> getOne(final Long id);

    /**
     * Returns all action for a role.
     *
     * @param id
     *            role id
     * @param pageable
     *            pagination to apply
     * @return action for the role
     */
    public Iterable<Permission> getPermission(final Long id, final Pageable pageable);

    /**
     * Removes a action from a role.
     *
     * @param id
     *            role id
     * @param resource
     *            resource id to add
     * @param action
     *            action id to remove
     * @return {@code true} if it managed to remove the action, {@code false} otherwise
     */
    public Boolean removePermission(final Long id, final Long resource, final Long action);

    /**
     * Updates the role for the received id with the received data.
     *
     * @param role
     *            new data for the role
     * @return the updated role
     */
    public Role update(final RoleUpdate role);

}
