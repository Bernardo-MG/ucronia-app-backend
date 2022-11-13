
package com.bernardomg.security.password.test.service.unit;

import java.util.Collections;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.bernardomg.security.data.persistence.model.PersistentUser;
import com.bernardomg.security.data.persistence.repository.UserRepository;
import com.bernardomg.security.email.sender.SecurityEmailSender;
import com.bernardomg.security.password.service.DefaultPasswordRecoveryService;
import com.bernardomg.security.password.service.PasswordRecoveryService;
import com.bernardomg.security.token.persistence.repository.TokenRepository;

@DisplayName("DefaultPasswordRecoveryService - Mail generation on recovery start")
public class TestDefaultPasswordRecoveryServiceStartEmail {

    private SecurityEmailSender     mailSender;

    private PasswordRecoveryService service;

    public TestDefaultPasswordRecoveryServiceStartEmail() {
        super();
    }

    @BeforeEach
    public final void initializeService() {
        final UserRepository     repository;
        final UserDetailsService userDetailsService;
        final PersistentUser     user;
        final TokenRepository    tokenRepository;
        final UserDetails        details;

        repository = Mockito.mock(UserRepository.class);

        user = new PersistentUser();
        user.setUsername("user");
        user.setEmail("email@somewhere.com");

        userDetailsService = Mockito.mock(UserDetailsService.class);

        details = new User("user", "password", true, true, true, true, Collections.emptyList());

        Mockito.when(userDetailsService.loadUserByUsername(ArgumentMatchers.anyString()))
            .thenReturn(details);

        Mockito.when(repository.findOneByUsername(ArgumentMatchers.anyString()))
            .thenReturn(Optional.of(user));
        Mockito.when(repository.findOneByEmail(ArgumentMatchers.anyString()))
            .thenReturn(Optional.of(user));

        mailSender = Mockito.mock(SecurityEmailSender.class);

        tokenRepository = Mockito.mock(TokenRepository.class);

        service = new DefaultPasswordRecoveryService(repository, userDetailsService, mailSender, tokenRepository);
    }

    @Test
    @DisplayName("When recovering the password if there is a user then a email is sent")
    public final void testStartPasswordRecovery_User_Email() {
        service.startPasswordRecovery("email@somewhere.com");

        Mockito.verify(mailSender, Mockito.times(1))
            .sendPasswordRecoveryEmail(ArgumentMatchers.anyString());
    }

}
