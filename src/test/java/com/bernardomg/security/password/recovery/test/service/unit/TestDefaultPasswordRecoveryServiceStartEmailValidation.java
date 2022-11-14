
package com.bernardomg.security.password.recovery.test.service.unit;

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
import org.springframework.security.crypto.password.PasswordEncoder;

import com.bernardomg.security.data.persistence.repository.UserRepository;
import com.bernardomg.security.email.sender.SecurityMessageSender;
import com.bernardomg.security.password.recovery.service.PasswordRecoveryService;
import com.bernardomg.security.password.recovery.service.springframework.SpringSecurityPasswordRecoveryService;
import com.bernardomg.security.token.provider.TokenProcessor;
import com.bernardomg.validation.exception.ValidationException;

@DisplayName("DefaultPasswordRecoveryService - Mail validation on recovery start")
public class TestDefaultPasswordRecoveryServiceStartEmailValidation {

    private SecurityMessageSender   mailSender;

    private PasswordRecoveryService service;

    public TestDefaultPasswordRecoveryServiceStartEmailValidation() {
        super();
    }

    @BeforeEach
    public final void initializeService() {
        final UserRepository     repository;
        final UserDetailsService userDetailsService;
        final TokenProcessor     tokenProcessor;
        final PasswordEncoder    passwordEncoder;
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

        mailSender = Mockito.mock(SecurityMessageSender.class);

        tokenProcessor = Mockito.mock(TokenProcessor.class);
        passwordEncoder = Mockito.mock(PasswordEncoder.class);

        service = new SpringSecurityPasswordRecoveryService(repository, userDetailsService, mailSender, tokenProcessor,
            passwordEncoder);
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
