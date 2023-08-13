
package com.bernardomg.security.user.test.integration;

import java.util.Collection;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import com.bernardomg.security.token.persistence.repository.TokenRepository;
import com.bernardomg.security.user.model.request.UserCreate;
import com.bernardomg.security.user.model.request.ValidatedUserCreate;
import com.bernardomg.security.user.persistence.model.PersistentUser;
import com.bernardomg.security.user.persistence.repository.UserRepository;
import com.bernardomg.security.user.service.UserService;
import com.bernardomg.test.config.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("Full new user register process")
class ITFullNewUserRegisterProcess {

    @Autowired
    private UserService     service;

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private UserRepository  userRepository;

    public ITFullNewUserRegisterProcess() {
        super();
    }

    private final void changeToAdmin() {
        final Authentication                         auth;
        final Collection<? extends GrantedAuthority> authorities;
        final GrantedAuthority                       authority;

        authority = new SimpleGrantedAuthority("USER:CREATE");
        authorities = List.of(authority);

        auth = new UsernamePasswordAuthenticationToken("admin", "1234", authorities);
        SecurityContextHolder.getContext()
            .setAuthentication(auth);
    }

    private final void changeToAnonymous() {
        final Authentication                         auth;
        final Collection<? extends GrantedAuthority> authorities;
        final GrantedAuthority                       authority;

        authority = new SimpleGrantedAuthority("NOTHING");
        authorities = List.of(authority);

        auth = new AnonymousAuthenticationToken("anonymous", "principal", authorities);
        SecurityContextHolder.getContext()
            .setAuthentication(auth);
    }

    @Test
    @DisplayName("Can follow the new user process from start to end")
    void testResetPassword_Valid() {
        final boolean        validTokenStatus;
        final String         token;
        final PersistentUser user;
        final UserCreate     newUser;

        // TODO: Set authentication to admin
        changeToAdmin();

        // Register new user
        newUser = ValidatedUserCreate.builder()
            .email("email@somewhere.com")
            .username("username")
            .name("user")
            .build();
        service.registerNewUser(newUser);

        // Validate new token
        token = tokenRepository.findAll()
            .stream()
            .findFirst()
            .get()
            .getToken();

        validTokenStatus = service.validateToken(token);

        Assertions.assertThat(validTokenStatus)
            .isTrue();

        // TODO: Set authentication to anonymous user
        changeToAnonymous();

        // Enable new user
        service.enableNewUser(token, "username");

        user = userRepository.findAll()
            .stream()
            .findFirst()
            .get();

        Assertions.assertThat(user.getEmail())
            .isEqualTo("email@somewhere.com");
        Assertions.assertThat(user.getUsername())
            .isEqualTo("username");
        Assertions.assertThat(user.getName())
            .isEqualTo("user");
        Assertions.assertThat(user.getEnabled())
            .isTrue();
    }

}
