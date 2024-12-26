
package com.bernardomg.association.security.user.test.configuration.factory;

import com.bernardomg.security.user.data.domain.model.User;

public final class Users {

    public static final User enabled() {
        return User.builder()
            .withName(UserConstants.NAME)
            .withUsername(UserConstants.USERNAME)
            .withEmail(UserConstants.EMAIL)
            .withEnabled(true)
            .withNotExpired(true)
            .withPasswordNotExpired(true)
            .withNotLocked(true)
            .build();
    }

    private Users() {
        super();
    }

}
