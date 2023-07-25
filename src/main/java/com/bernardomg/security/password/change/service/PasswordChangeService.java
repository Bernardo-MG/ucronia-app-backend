
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
     * Changes the password for the user currently in session. It requires the current password to authenticate.
     *
     * @param currentPassword
     *            current password for the user
     * @param password
     *            new password for the user
     * @return the password change status
     */
    public PasswordChangeStatus changePasswordForUserInSession(final String currentPassword, final String password);

}
