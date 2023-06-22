
package com.bernardomg.security.user.test.user.integration.service;

import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.association.test.config.annotation.IntegrationTest;
import com.bernardomg.security.user.model.request.DtoUserUpdateRequest;
import com.bernardomg.security.user.model.request.UserUpdateRequest;
import com.bernardomg.security.user.service.UserService;
import com.bernardomg.test.assertion.ValidationAssertions;
import com.bernardomg.validation.failure.FieldFailure;

@IntegrationTest
@DisplayName("User service - add roles validation")
public class ITUserServiceUpdateValidation {

    @Autowired
    private UserService service;

    public ITUserServiceUpdateValidation() {
        super();
    }

    @Test
    @DisplayName("Throws an exception when changing the username")
    @Sql({ "/db/queries/security/resource/single.sql", "/db/queries/security/action/crud.sql",
            "/db/queries/security/role/single.sql", "/db/queries/security/user/single.sql",
            "/db/queries/security/relationship/role_permission.sql" })
    public void testUpdate_ChangeUsername() {
        final ThrowingCallable  executable;
        final FieldFailure      failure;
        final UserUpdateRequest data;

        data = getUser("abc");

        executable = () -> service.update(data);

        // TODO: Is this value really the correct one?
        failure = FieldFailure.of("username.immutable", "username", "immutable", 1L);

        ValidationAssertions.assertThatFieldFails(executable, failure);
    }

    @Test
    @DisplayName("Throws an exception when the email already exists")
    @Sql({ "/db/queries/security/resource/single.sql", "/db/queries/security/action/crud.sql",
            "/db/queries/security/role/single.sql", "/db/queries/security/user/single.sql",
            "/db/queries/security/user/alternative.sql", "/db/queries/security/relationship/role_permission.sql" })
    public void testUpdate_ExistingMail() {
        final ThrowingCallable  executable;
        final FieldFailure      failure;
        final UserUpdateRequest data;

        data = getUser("admin", "email2@somewhere.com");

        executable = () -> service.update(data);

        failure = FieldFailure.of("email.existing", "email", "existing", "email2@somewhere.com");

        ValidationAssertions.assertThatFieldFails(executable, failure);
    }

    @Test
    @DisplayName("Throws an exception when the email doesn't match the valid pattern")
    @Sql({ "/db/queries/security/resource/single.sql", "/db/queries/security/action/crud.sql",
            "/db/queries/security/role/single.sql", "/db/queries/security/user/single.sql",
            "/db/queries/security/relationship/role_permission.sql" })
    public void testUpdate_InvalidMail() {
        final ThrowingCallable  executable;
        final FieldFailure      failure;
        final UserUpdateRequest data;

        data = getUser("admin", "abc");

        executable = () -> service.update(data);

        failure = FieldFailure.of("email.invalid", "email", "invalid", "abc");

        ValidationAssertions.assertThatFieldFails(executable, failure);
    }

    @Test
    @DisplayName("Throws an exception when the user doesn't exist")
    public void testUpdate_NotExistingUser() {
        final ThrowingCallable  executable;
        final FieldFailure      failure;
        final UserUpdateRequest data;

        data = getUser();

        executable = () -> service.update(data);

        failure = FieldFailure.of("id.notExisting", "id", "notExisting", "admin");

        ValidationAssertions.assertThatFieldFails(executable, failure);
    }

    private final UserUpdateRequest getUser() {
        return DtoUserUpdateRequest.builder()
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

    private final UserUpdateRequest getUser(final String username) {
        return DtoUserUpdateRequest.builder()
            .id(1L)
            .username(username)
            .name("Admin")
            .email("email@somewhere.com")
            .credentialsExpired(false)
            .enabled(true)
            .expired(false)
            .locked(false)
            .build();
    }

    private final UserUpdateRequest getUser(final String username, final String email) {
        return DtoUserUpdateRequest.builder()
            .id(1L)
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
