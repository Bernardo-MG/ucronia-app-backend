
package com.bernardomg.security.password.test.service.unit;

import java.util.Collections;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.bernardomg.security.data.persistence.repository.UserRepository;
import com.bernardomg.security.email.sender.SecurityEmailSender;
import com.bernardomg.security.password.service.DefaultPasswordRecoveryService;
import com.bernardomg.security.password.service.PasswordRecoveryService;
import com.bernardomg.security.token.provider.TokenProvider;
import com.bernardomg.security.token.provider.TokenValidator;
import com.bernardomg.validation.exception.ValidationException;

@DisplayName("DefaultPasswordRecoveryService - Mail validation on recovery start")
public class TestDefaultPasswordRecoveryServiceStartEmailValidation {

    private SecurityEmailSender     mailSender;

    private PasswordRecoveryService service;

    public TestDefaultPasswordRecoveryServiceStartEmailValidation() {
        super();
    }

    @BeforeEach
    public final void initializeService() {
        final UserRepository     repository;
        final UserDetailsService userDetailsService;
        final TokenProvider      tokenProvider;
        final TokenValidator     tokenValidator;
        final UserDetails        details;

        repository = Mockito.mock(UserRepository.class);
        Mockito.when(repository.findOneByUsername(ArgumentMatchers.anyString()))
            .thenReturn(Optional.empty());
        Mockito.when(repository.findOneByEmail(ArgumentMatchers.anyString()))
            .thenReturn(Optional.empty());

        userDetailsService = Mockito.mock(UserDetailsService.class);

        details = new User("user", "password", true, true, true, true, Collections.emptyList());

        Mockito.when(userDetailsService.loadUserByUsername(ArgumentMatchers.anyString()))
            .thenReturn(details);

        mailSender = Mockito.mock(SecurityEmailSender.class);

        tokenProvider = Mockito.mock(TokenProvider.class);
        tokenValidator = Mockito.mock(TokenValidator.class);

        service = new DefaultPasswordRecoveryService(repository, userDetailsService, mailSender, tokenProvider,
            tokenValidator);
    }

    @Test
    @DisplayName("Throws an exception when no user exists for the email")
    public final void testStartPasswordRecovery_NoUser() {
        final Executable executable;
        final Exception  exception;

        executable = () -> service.startPasswordRecovery("email@somewhere.com");

        exception = Assertions.assertThrows(ValidationException.class, executable);

        Assertions.assertEquals("error.email.notExisting", exception.getMessage());
    }

}
