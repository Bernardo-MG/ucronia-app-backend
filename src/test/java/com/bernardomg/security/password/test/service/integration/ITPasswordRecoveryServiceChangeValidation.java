
package com.bernardomg.security.password.test.service.integration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.association.test.config.annotation.IntegrationTest;
import com.bernardomg.security.password.service.PasswordRecoveryService;
import com.bernardomg.validation.exception.ValidationException;

@IntegrationTest
@DisplayName("PasswordRecoveryService - change password - validation")
public class ITPasswordRecoveryServiceChangeValidation {

    @Autowired
    private PasswordRecoveryService service;

    public ITPasswordRecoveryServiceChangeValidation() {
        super();
    }

    @Test
    @DisplayName("Throws an exception when the token is expired")
    @Sql({ "/db/queries/security/privilege/multiple.sql", "/db/queries/security/role/single.sql",
            "/db/queries/security/user/single.sql", "/db/queries/security/relationship/role_privilege.sql",
            "/db/queries/security/relationship/user_role.sql" })
    @Sql({ "/db/queries/security/token/expired.sql" })
    public final void testChangePassword_ExpiredToken() {
        final Executable executable;
        final Exception  exception;

        executable = () -> service.changePassword(
            "MTY2ODM4MjMxMDUzMDo2M2I3ODI4ZTUzZThjNTIwOTI1MjFkZTMwMTNhY2Y3ZDcxMjIwMDRlYzZiMmI5NDlhNzRjZWUzMjA4ZDIwYzY2OmVtYWlsQHNvbWV3aGVyZS5jb206ZGI1NWE0NmQ1MjBlMmJmMjUwZDIzY2U3YzQxZTA2ZDRlYzY0MzkxZDY3ZTcxNzEzNzU3MTUwNzkyZDgwYTQ0MzVjMGQ4ZDc3NjBmN2M4Mzc4NWJmM2I4NjE4ZGU2NjM4NWY4N2I1ZmM3MmNjNzdlMzUxNWMzYTZhYzQwMmEzY2Y",
            "1234", "abc");

        exception = Assertions.assertThrows(ValidationException.class, executable);

        Assertions.assertEquals("error.token.expired", exception.getMessage());
    }

    @Test
    @DisplayName("Throws an exception when the current password is incorrect")
    @Sql({ "/db/queries/security/privilege/multiple.sql", "/db/queries/security/role/single.sql",
            "/db/queries/security/user/single.sql", "/db/queries/security/relationship/role_privilege.sql",
            "/db/queries/security/relationship/user_role.sql" })
    @Sql({ "/db/queries/security/token/valid.sql" })
    public final void testChangePassword_IncorrectPassword() {
        final Executable executable;
        final Exception  exception;

        executable = () -> service.changePassword(
            "MTY2ODM4MjMxMDUzMDo2M2I3ODI4ZTUzZThjNTIwOTI1MjFkZTMwMTNhY2Y3ZDcxMjIwMDRlYzZiMmI5NDlhNzRjZWUzMjA4ZDIwYzY2OmVtYWlsQHNvbWV3aGVyZS5jb206ZGI1NWE0NmQ1MjBlMmJmMjUwZDIzY2U3YzQxZTA2ZDRlYzY0MzkxZDY3ZTcxNzEzNzU3MTUwNzkyZDgwYTQ0MzVjMGQ4ZDc3NjBmN2M4Mzc4NWJmM2I4NjE4ZGU2NjM4NWY4N2I1ZmM3MmNjNzdlMzUxNWMzYTZhYzQwMmEzY2Y",
            "def", "abc");

        exception = Assertions.assertThrows(ValidationException.class, executable);

        Assertions.assertEquals("error.password.invalid", exception.getMessage());
    }

    @Test
    @DisplayName("Throws an exception when the token doesn't exist")
    @Sql({ "/db/queries/security/privilege/multiple.sql", "/db/queries/security/role/single.sql",
            "/db/queries/security/user/single.sql", "/db/queries/security/relationship/role_privilege.sql",
            "/db/queries/security/relationship/user_role.sql" })
    public final void testChangePassword_MissingToken() {
        final Executable executable;
        final Exception  exception;

        executable = () -> service.changePassword(
            "MTY2ODM4MjMxMDUzMDo2M2I3ODI4ZTUzZThjNTIwOTI1MjFkZTMwMTNhY2Y3ZDcxMjIwMDRlYzZiMmI5NDlhNzRjZWUzMjA4ZDIwYzY2OmVtYWlsQHNvbWV3aGVyZS5jb206ZGI1NWE0NmQ1MjBlMmJmMjUwZDIzY2U3YzQxZTA2ZDRlYzY0MzkxZDY3ZTcxNzEzNzU3MTUwNzkyZDgwYTQ0MzVjMGQ4ZDc3NjBmN2M4Mzc4NWJmM2I4NjE4ZGU2NjM4NWY4N2I1ZmM3MmNjNzdlMzUxNWMzYTZhYzQwMmEzY2Y",
            "1234", "abc");

        exception = Assertions.assertThrows(ValidationException.class, executable);

        Assertions.assertEquals("error.token.notExisting", exception.getMessage());
    }

    @Test
    @DisplayName("Throws an exception when the user doesn't exist")
    @Sql({ "/db/queries/security/token/valid.sql" })
    public final void testChangePassword_MissingUser() {
        final Executable executable;
        final Exception  exception;

        executable = () -> service.changePassword(
            "MTY2ODM4MjMxMDUzMDo2M2I3ODI4ZTUzZThjNTIwOTI1MjFkZTMwMTNhY2Y3ZDcxMjIwMDRlYzZiMmI5NDlhNzRjZWUzMjA4ZDIwYzY2OmVtYWlsQHNvbWV3aGVyZS5jb206ZGI1NWE0NmQ1MjBlMmJmMjUwZDIzY2U3YzQxZTA2ZDRlYzY0MzkxZDY3ZTcxNzEzNzU3MTUwNzkyZDgwYTQ0MzVjMGQ4ZDc3NjBmN2M4Mzc4NWJmM2I4NjE4ZGU2NjM4NWY4N2I1ZmM3MmNjNzdlMzUxNWMzYTZhYzQwMmEzY2Y",
            "1234", "abc");

        exception = Assertions.assertThrows(ValidationException.class, executable);

        Assertions.assertEquals("error.user.notExisting", exception.getMessage());
    }

    @Test
    @DisplayName("Throws an exception when the token is valid but after the expiration date")
    @Sql({ "/db/queries/security/privilege/multiple.sql", "/db/queries/security/role/single.sql",
            "/db/queries/security/user/single.sql", "/db/queries/security/relationship/role_privilege.sql",
            "/db/queries/security/relationship/user_role.sql" })
    @Sql({ "/db/queries/security/token/not_expired_after_expiration.sql" })
    public final void testChangePassword_TokenAfterExpirationDate() {
        final Executable executable;
        final Exception  exception;

        executable = () -> service.changePassword(
            "MTY2ODM4MjMxMDUzMDo2M2I3ODI4ZTUzZThjNTIwOTI1MjFkZTMwMTNhY2Y3ZDcxMjIwMDRlYzZiMmI5NDlhNzRjZWUzMjA4ZDIwYzY2OmVtYWlsQHNvbWV3aGVyZS5jb206ZGI1NWE0NmQ1MjBlMmJmMjUwZDIzY2U3YzQxZTA2ZDRlYzY0MzkxZDY3ZTcxNzEzNzU3MTUwNzkyZDgwYTQ0MzVjMGQ4ZDc3NjBmN2M4Mzc4NWJmM2I4NjE4ZGU2NjM4NWY4N2I1ZmM3MmNjNzdlMzUxNWMzYTZhYzQwMmEzY2Y",
            "1234", "abc");

        exception = Assertions.assertThrows(ValidationException.class, executable);

        Assertions.assertEquals("error.token.expired", exception.getMessage());
    }

}
