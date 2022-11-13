
package com.bernardomg.security.password.test.service.unit;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import com.bernardomg.security.data.persistence.repository.UserRepository;
import com.bernardomg.security.email.sender.SecurityEmailSender;
import com.bernardomg.security.password.service.DefaultPasswordRecoveryService;
import com.bernardomg.security.password.service.PasswordRecoveryService;
import com.bernardomg.validation.exception.ValidationException;

@DisplayName("DefaultPasswordRecoveryService")
public class TestDefaultPasswordRecoveryServiceValidation {

    private SecurityEmailSender     mailSender;

    private PasswordRecoveryService service;

    public TestDefaultPasswordRecoveryServiceValidation() {
        super();
    }

    @BeforeEach
    public final void initializeService() {
        final UserRepository repository;

        repository = Mockito.mock(UserRepository.class);
        Mockito.when(repository.findOneByUsername(ArgumentMatchers.anyString()))
            .thenReturn(Optional.empty());
        Mockito.when(repository.findOneByEmail(ArgumentMatchers.anyString()))
            .thenReturn(Optional.empty());

        mailSender = Mockito.mock(SecurityEmailSender.class);

        service = new DefaultPasswordRecoveryService(repository, mailSender);
    }

    @Test
    @DisplayName("When recovering the password by email if there is no user then no email is sent")
    public final void testRecoverPassword_NoUser_NoEmail() {
        final Executable executable;
        final Exception  exception;

        executable = () -> service.recoverPassword("email@somewhere.com");

        exception = Assertions.assertThrows(ValidationException.class, executable);

        Assertions.assertEquals("error.username.notExisting", exception.getMessage());
    }

}
