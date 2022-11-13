
package com.bernardomg.security.password.service;

public interface PasswordRecoveryService {

    public Boolean changePassword(final String token, final String currentPassword, final String newPassword);

    public Boolean startPasswordRecovery(final String email);

}
