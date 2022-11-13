
package com.bernardomg.security.password.service;

public interface PasswordRecoveryService {

    public Boolean recoverPasswordByEmail(final String email);

    public Boolean recoverPasswordByUsername(final String username);

}
