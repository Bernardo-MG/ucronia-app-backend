
package com.bernardomg.security.login.service;

import com.bernardomg.security.login.model.ImmutableLoginStatus;
import com.bernardomg.security.login.model.LoginStatus;

public final class DefaultLoginStatusProvider implements LoginStatusProvider {

    public DefaultLoginStatusProvider() {
        super();
    }

    @Override
    public final LoginStatus getStatus(final String username, final Boolean logged) {
        return new ImmutableLoginStatus(username, logged);
    }

}
