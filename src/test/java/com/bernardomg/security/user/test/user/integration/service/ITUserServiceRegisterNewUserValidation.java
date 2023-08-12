
package com.bernardomg.security.user.test.user.integration.service;

import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.security.user.model.request.UserCreate;
import com.bernardomg.security.user.service.UserService;
import com.bernardomg.security.user.test.util.model.UsersCreate;
import com.bernardomg.test.assertion.ValidationAssertions;
import com.bernardomg.test.config.annotation.AllAuthoritiesMockUser;
import com.bernardomg.test.config.annotation.IntegrationTest;
import com.bernardomg.validation.failure.FieldFailure;

@IntegrationTest
@AllAuthoritiesMockUser
@DisplayName("User service - register new user - validation")
class ITUserServiceRegisterNewUserValidation {

    @Autowired
    private UserService service;

    public ITUserServiceRegisterNewUserValidation() {
        super();
    }

    @Test
    @DisplayName("Throws an exception when the email already exists")
    @Sql({ "/db/queries/security/user/single.sql" })
    void testRegisterNewUser_ExistingEmail() {
        final UserCreate       data;
        final ThrowingCallable executable;
        final FieldFailure     failure;

        data = UsersCreate.enabled("abc", "email@somewhere.com");

        executable = () -> service.registerNewUser(data);

        failure = FieldFailure.of("email.existing", "email", "existing", "email@somewhere.com");

        ValidationAssertions.assertThatFieldFails(executable, failure);
    }

    @Test
    @DisplayName("Throws an exception when the username already exists")
    @Sql({ "/db/queries/security/user/single.sql" })
    void testRegisterNewUser_ExistingUsername() {
        final UserCreate       data;
        final ThrowingCallable executable;
        final FieldFailure     failure;

        data = UsersCreate.enabled("admin", "email2@somewhere.com");

        executable = () -> service.registerNewUser(data);

        failure = FieldFailure.of("username.existing", "username", "existing", "admin");

        ValidationAssertions.assertThatFieldFails(executable, failure);
    }

}
