
package com.bernardomg.security.password.change.service;

import com.bernardomg.security.password.change.model.PasswordChangeStatus;

/**
 * Password change service.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
public interface PasswordChangeService {

    /**
     * Changes the password for a user. It requires the current password to authenticate.
     *
     * @param username
     *            user to change the password
     * @param currentPassword
     *            current password for the user
     * @param password
     *            new password for the user
     * @return the password change status
     */
    public PasswordChangeStatus changePassword(final String username, final String currentPassword,
            final String password);

}
