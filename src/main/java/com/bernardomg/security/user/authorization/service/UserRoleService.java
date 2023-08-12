
package com.bernardomg.security.user.authorization.service;

import org.springframework.data.domain.Pageable;

import com.bernardomg.security.user.model.Role;
import com.bernardomg.security.user.model.UserRole;

public interface UserRoleService {

    /**
     * Adds a role to a user.
     *
     * @param id
     *            user id
     * @param role
     *            role id to add
     * @return the added role
     */
    public UserRole addRole(final long id, final long role);

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
     * @return the removed role
     */
    public UserRole removeRole(final long id, final long role);

}
