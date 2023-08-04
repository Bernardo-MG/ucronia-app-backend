
package com.bernardomg.security.password.reset.test.service.unit;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.util.Collections;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.bernardomg.security.email.sender.SecurityMessageSender;
import com.bernardomg.security.password.reset.service.PasswordResetService;
import com.bernardomg.security.password.reset.service.SpringSecurityPasswordResetService;
import com.bernardomg.security.token.store.TokenStore;
import com.bernardomg.security.user.persistence.model.PersistentUser;
import com.bernardomg.security.user.persistence.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
@DisplayName("SpringSecurityPasswordRecoveryService - Mail generation on recovery start")
class TestSpringSecurityPasswordResetServiceStartEmail {

    @Mock
    private Authentication        authentication;

    @Mock
    private SecurityMessageSender mailSender;

    @Mock
    private PasswordEncoder       passwordEncoder;

    @Mock
    private UserRepository        repository;

    private PasswordResetService  service;

    @Mock
    private TokenStore            tokenProcessor;

    @Mock
    private UserDetailsService    userDetailsService;

    public TestSpringSecurityPasswordResetServiceStartEmail() {
        super();
    }

    @BeforeEach
    void initializeAuthentication() {
        SecurityContextHolder.getContext()
            .setAuthentication(authentication);
    }

    @BeforeEach
    void initializeService() {
        final PersistentUser user;
        final UserDetails    details;

        user = PersistentUser.builder()
            .username("admin")
            .email("email@somewhere.com")
            .build();

        details = new User("admin", "password", true, true, true, true, Collections.emptyList());

        given(userDetailsService.loadUserByUsername(ArgumentMatchers.anyString())).willReturn(details);

        given(repository.findOneByEmail(ArgumentMatchers.anyString())).willReturn(Optional.of(user));

        service = new SpringSecurityPasswordResetService(repository, userDetailsService, mailSender, tokenProcessor,
            passwordEncoder);
    }

    @Test
    @DisplayName("When recovering the password the email is sent to the user email")
    void testStartPasswordRecovery_User_Email() {
        final ArgumentCaptor<String> emailCaptor;

        emailCaptor = ArgumentCaptor.forClass(String.class);

        service.startPasswordRecovery("email@somewhere.com");

        verify(mailSender).sendPasswordRecoveryMessage(emailCaptor.capture(), ArgumentMatchers.any());

        Assertions.assertThat(emailCaptor.getValue())
            .isEqualTo("email@somewhere.com");
    }

    @Test
    @DisplayName("When recovering the password an email is sent")
    void testStartPasswordRecovery_User_EmailCall() {
        service.startPasswordRecovery("email@somewhere.com");

        verify(mailSender, Mockito.times(1)).sendPasswordRecoveryMessage(ArgumentMatchers.any(),
            ArgumentMatchers.any());
    }

}