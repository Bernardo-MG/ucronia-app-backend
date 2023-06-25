
package com.bernardomg.security.signup.test.service.integration;

import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.association.test.config.annotation.IntegrationTest;
import com.bernardomg.security.signup.model.DtoSignUp;
import com.bernardomg.security.signup.service.SignUpService;
import com.bernardomg.test.assertion.ValidationAssertions;
import com.bernardomg.validation.failure.FieldFailure;

@IntegrationTest
@DisplayName("UserRegistrationService - validation")
public class ITUserRegistrationServiceValidation {

    @Autowired
    private SignUpService service;

    public ITUserRegistrationServiceValidation() {
        super();
    }

    @Test
    @DisplayName("Throws an exception when the email already exists")
    @Sql({ "/db/queries/security/user/single.sql" })
    public void testSignUp_ExistingEmail() {
        final ThrowingCallable executable;
        final FieldFailure     failure;
        final DtoSignUp        signUp;

        signUp = new DtoSignUp();
        signUp.setUsername("abc");
        signUp.setEmail("email@somewhere.com");

        executable = () -> service.signUp(signUp);

        failure = FieldFailure.of("email.existing", "email", "existing", "email@somewhere.com");

        ValidationAssertions.assertThatFieldFails(executable, failure);
    }

    @Test
    @DisplayName("Throws an exception when the email already exists, ignoring case")
    @Sql({ "/db/queries/security/user/single.sql" })
    public void testSignUp_ExistingEmail_Case() {
        final ThrowingCallable executable;
        final FieldFailure     failure;
        final DtoSignUp        signUp;

        signUp = new DtoSignUp();
        signUp.setUsername("abc");
        signUp.setEmail("EMAIL@somewhere.com");

        executable = () -> service.signUp(signUp);

        failure = FieldFailure.of("email.existing", "email", "existing", "EMAIL@somewhere.com");

        ValidationAssertions.assertThatFieldFails(executable, failure);
    }

    @Test
    @DisplayName("Throws an exception when the username already exists")
    @Sql({ "/db/queries/security/user/single.sql" })
    public void testSignUp_ExistingUsername() {
        final ThrowingCallable executable;
        final FieldFailure     failure;
        final DtoSignUp        signUp;

        signUp = new DtoSignUp();
        signUp.setUsername("admin");
        signUp.setEmail("email2@somewhere.com");

        executable = () -> service.signUp(signUp);

        failure = FieldFailure.of("username.existing", "username", "existing", "admin");

        ValidationAssertions.assertThatFieldFails(executable, failure);
    }

    @Test
    @DisplayName("Throws an exception when the username already exists, ignoring case")
    @Sql({ "/db/queries/security/user/single.sql" })
    public void testSignUp_ExistingUsername_Case() {
        final ThrowingCallable executable;
        final FieldFailure     failure;
        final DtoSignUp        signUp;

        signUp = new DtoSignUp();
        signUp.setUsername("ADMIN");
        signUp.setEmail("email2@somewhere.com");

        executable = () -> service.signUp(signUp);

        failure = FieldFailure.of("username.existing", "username", "existing", "ADMIN");

        ValidationAssertions.assertThatFieldFails(executable, failure);
    }

}
