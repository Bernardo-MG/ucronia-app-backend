
package com.bernardomg.security.login.service;

import com.bernardomg.security.login.model.LoginStatus;

public interface LoginStatusProvider {

    public LoginStatus getStatus(final String username, final Boolean logged);

}
