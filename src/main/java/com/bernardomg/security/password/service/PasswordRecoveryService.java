
package com.bernardomg.security.password.service;

public interface PasswordRecoveryService {

    public Boolean recoverPasswordByUsername(final String username);

    public Boolean recoverPasswordByEmail(final String username);

}
