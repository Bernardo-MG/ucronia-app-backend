
package com.bernardomg.security.user.test.util.model;

import com.bernardomg.security.user.model.request.UserUpdate;
import com.bernardomg.security.user.model.request.ValidatedUserUpdate;

public final class UsersUpdate {

    public static final UserUpdate emailChange() {
        return ValidatedUserUpdate.builder()
            .id(1L)
            .username("admin")
            .name("Admin")
            .email("email2@somewhere.com")
            .credentialsExpired(false)
            .enabled(true)
            .expired(false)
            .locked(false)
            .build();
    }

    public static final UserUpdate emailChangeUpperCase() {
        return ValidatedUserUpdate.builder()
            .id(1L)
            .username("admin")
            .name("Admin")
            .email("EMAIL2@SOMEWHERE.COM")
            .credentialsExpired(false)
            .enabled(true)
            .expired(false)
            .locked(false)
            .build();
    }

    public static final UserUpdate invalidEmail() {
        return ValidatedUserUpdate.builder()
            .id(1L)
            .username("admin")
            .name("Admin")
            .email("abc")
            .credentialsExpired(false)
            .enabled(true)
            .expired(false)
            .locked(false)
            .build();
    }

    public static final UserUpdate usernameChange() {
        return ValidatedUserUpdate.builder()
            .id(1L)
            .username("abc")
            .name("Admin")
            .email("email@somewhere.com")
            .credentialsExpired(false)
            .enabled(true)
            .expired(false)
            .locked(false)
            .build();
    }

    public static final UserUpdate valid() {
        return ValidatedUserUpdate.builder()
            .id(1L)
            .username("admin")
            .name("Admin")
            .email("email@somewhere.com")
            .credentialsExpired(false)
            .enabled(true)
            .expired(false)
            .locked(false)
            .build();
    }

}
