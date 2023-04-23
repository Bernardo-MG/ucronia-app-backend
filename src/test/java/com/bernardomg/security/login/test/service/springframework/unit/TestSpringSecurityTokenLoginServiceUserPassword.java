
package com.bernardomg.security.login.test.service.springframework.unit;

import java.util.Collections;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.bernardomg.security.login.model.DtoLogin;
import com.bernardomg.security.login.model.LoginStatus;
import com.bernardomg.security.login.model.TokenLoginStatus;
import com.bernardomg.security.login.service.springframework.SpringSecurityTokenLoginService;
import com.bernardomg.security.test.constant.TokenConstants;
import com.bernardomg.security.token.provider.TokenProvider;

@DisplayName("SpringSecurityTokenLoginService - password validation")
public class TestSpringSecurityTokenLoginServiceUserPassword {

    public TestSpringSecurityTokenLoginServiceUserPassword() {
        super();
    }

    @Test
    @DisplayName("Doesn't log in with an invalid password")
    public void testLogIn_Invalid() {
        final LoginStatus status;
        final DtoLogin    login;

        login = new DtoLogin();
        login.setUsername("admin");
        login.setPassword("1234");

        status = getService(false).login(login);

        Assertions.assertFalse((status instanceof TokenLoginStatus));

        Assertions.assertFalse(status.getLogged());
        Assertions.assertEquals("admin", status.getUsername());
    }

    @Test
    @DisplayName("Logs in with a valid password")
    public void testLogIn_Valid() {
        final LoginStatus status;
        final DtoLogin    login;

        login = new DtoLogin();
        login.setUsername("admin");
        login.setPassword("1234");

        status = getService(true).login(login);

        Assertions.assertInstanceOf(TokenLoginStatus.class, status);

        Assertions.assertTrue(status.getLogged());
        Assertions.assertEquals("admin", status.getUsername());
        Assertions.assertEquals(TokenConstants.TOKEN, ((TokenLoginStatus) status).getToken());
    }

    private final SpringSecurityTokenLoginService getService(final Boolean match) {
        final UserDetailsService userDetService;
        final PasswordEncoder    passEncoder;
        final TokenProvider      tokenProvider;
        final UserDetails        user;

        user = new User("username", "password", true, true, true, true, Collections.emptyList());

        userDetService = Mockito.mock(UserDetailsService.class);
        Mockito.when(userDetService.loadUserByUsername(ArgumentMatchers.anyString()))
            .thenReturn(user);

        passEncoder = Mockito.mock(PasswordEncoder.class);
        Mockito.when(passEncoder.matches(ArgumentMatchers.anyString(), ArgumentMatchers.anyString()))
            .thenReturn(match);

        tokenProvider = Mockito.mock(TokenProvider.class);
        Mockito.when(tokenProvider.generateToken(ArgumentMatchers.anyString()))
            .thenReturn(TokenConstants.TOKEN);

        return new SpringSecurityTokenLoginService(userDetService, passEncoder, tokenProvider);
    }

}
