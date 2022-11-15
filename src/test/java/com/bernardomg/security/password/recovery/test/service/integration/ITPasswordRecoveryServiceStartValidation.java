
package com.bernardomg.security.password.recovery.test.service.integration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;

import com.bernardomg.association.test.config.annotation.IntegrationTest;
import com.bernardomg.security.password.recovery.service.PasswordRecoveryService;
import com.bernardomg.validation.exception.ValidationException;

@IntegrationTest
@DisplayName("PasswordRecoveryService - recovery start - validation")
public class ITPasswordRecoveryServiceStartValidation {

    @Autowired
    private PasswordRecoveryService service;

    public ITPasswordRecoveryServiceStartValidation() {
        super();
    }

    @Test
    @DisplayName("Throws an exception when there is no user")
    public final void testStartPasswordRecovery_NoUser() {
        final Executable executable;
        final Exception  exception;

        executable = () -> service.startPasswordRecovery("email@somewhere.com");

        exception = Assertions.assertThrows(ValidationException.class, executable);

        Assertions.assertEquals("error.email.notExisting", exception.getMessage());
    }

}
