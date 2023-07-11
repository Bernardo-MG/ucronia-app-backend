
package com.bernardomg.security.user.test.util.model;

import com.bernardomg.security.user.model.request.UserCreate;
import com.bernardomg.security.user.model.request.ValidatedUserCreate;

public final class UsersCreate {

    public static final UserCreate alternative() {
        return ValidatedUserCreate.builder()
            .username("user")
            .name("User")
            .email("email2@somewhere.com")
            .enabled(true)
            .build();
    }

    public static final UserCreate enabled() {
        return ValidatedUserCreate.builder()
            .username("admin")
            .name("Admin")
            .email("email@somewhere.com")
            .enabled(true)
            .build();
    }

    public static final UserCreate enabled(final String username, final String email) {
        return ValidatedUserCreate.builder()
            .username(username)
            .name("Admin")
            .email(email)
            .enabled(true)
            .build();
    }

    public static final UserCreate invalidEmail() {
        return ValidatedUserCreate.builder()
            .username("admin")
            .name("Admin")
            .email("abc")
            .enabled(true)
            .build();
    }

    public static final UserCreate missingEmail() {
        return ValidatedUserCreate.builder()
            .username("admin")
            .name("Admin")
            .enabled(true)
            .build();
    }

    public static final UserCreate missingEnabled() {
        return ValidatedUserCreate.builder()
            .username("admin")
            .name("Admin")
            .email("email@somewhere.com")
            .build();
    }

}
