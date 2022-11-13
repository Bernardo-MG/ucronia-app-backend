
package com.bernardomg.security.password.service;

public interface PasswordRecoveryService {

    public Boolean startPasswordRecovery(final String email);

    public Boolean verifyToken(final String Token);

}
