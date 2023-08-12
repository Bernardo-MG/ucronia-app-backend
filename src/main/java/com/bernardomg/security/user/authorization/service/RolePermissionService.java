
package com.bernardomg.security.user.authorization.service;

import org.springframework.data.domain.Pageable;

import com.bernardomg.security.permission.model.Permission;
import com.bernardomg.security.user.model.RolePermission;

public interface RolePermissionService {

    /**
     * Adds a action to a role.
     *
     * @param id
     *            role id
     * @param resource
     *            resource id to add
     * @param action
     *            action id to add
     * @return the added permission
     */
    public RolePermission addPermission(final long id, final long resource, final long action);

    /**
     * Returns all action for a role.
     *
     * @param id
     *            role id
     * @param pageable
     *            pagination to apply
     * @return action for the role
     */
    public Iterable<Permission> getPermissions(final long id, final Pageable pageable);

    /**
     * Removes a action from a role.
     *
     * @param id
     *            role id
     * @param resource
     *            resource id to add
     * @param action
     *            action id to remove
     * @return the removed permission
     */
    public RolePermission removePermission(final long id, final long resource, final long action);

}
