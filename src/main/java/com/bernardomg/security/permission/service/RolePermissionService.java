
package com.bernardomg.security.permission.service;

import org.springframework.data.domain.Pageable;

import com.bernardomg.security.permission.model.Permission;
import com.bernardomg.security.user.model.RolePermission;

public interface RolePermissionService {

    /**
     * Adds a action to a role.
     *
     * @param roleId
     *            role id
     * @param permission
     *            permission id to add
     * @return the added permission
     */
    public RolePermission addPermission(final long roleId, final Long permission);

    /**
     * Returns all action for a role.
     *
     * @param roleId
     *            role id
     * @param pageable
     *            pagination to apply
     * @return action for the role
     */
    public Iterable<Permission> getPermissions(final long roleId, final Pageable pageable);

    /**
     * Removes a action from a role.
     *
     * @param roleId
     *            role id
     * @param permission
     *            permission id to remove
     * @return the removed permission
     */
    public RolePermission removePermission(final long roleId, final Long permission);

}
