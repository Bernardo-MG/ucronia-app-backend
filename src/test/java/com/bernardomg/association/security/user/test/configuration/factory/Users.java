
package com.bernardomg.association.security.user.test.configuration.factory;

import java.util.List;

import com.bernardomg.security.user.data.domain.model.User;

public final class Users {

    public static final User enabled() {
        return new User(UserConstants.EMAIL, UserConstants.USERNAME, UserConstants.NAME, true, true, true, true,
            List.of());
    }

    private Users() {
        super();
    }

}
