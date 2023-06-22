
package com.bernardomg.security.user.test.util.model;

import com.bernardomg.security.user.model.request.UserCreate;
import com.bernardomg.security.user.model.request.ValidatedUserCreate;

public final class UsersCreate {

    public static final UserCreate alternative() {
        return ValidatedUserCreate.builder()
            .username("user")
            .name("User")
            .email("email2@somewhere.com")
            .credentialsExpired(false)
            .enabled(true)
            .expired(false)
            .locked(false)
            .build();
    }

    public static final UserCreate enabled() {
        return ValidatedUserCreate.builder()
            .username("admin")
            .name("Admin")
            .email("email@somewhere.com")
            .credentialsExpired(false)
            .enabled(true)
            .expired(false)
            .locked(false)
            .build();
    }

    public static final UserCreate enabled(final String username, final String email) {
        return ValidatedUserCreate.builder()
            .username(username)
            .name("Admin")
            .email(email)
            .credentialsExpired(false)
            .enabled(true)
            .expired(false)
            .locked(false)
            .build();
    }

}
