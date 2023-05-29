
package com.bernardomg.security.login.test.service.springframework.unit;

import java.util.Collections;
import java.util.function.Predicate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.bernardomg.security.login.model.LoginStatus;
import com.bernardomg.security.login.model.request.DtoLoginRequest;
import com.bernardomg.security.login.model.request.LoginRequest;
import com.bernardomg.security.login.service.DefaultLoginService;
import com.bernardomg.security.login.service.DefaultLoginStatusProvider;
import com.bernardomg.security.login.service.LoginStatusProvider;
import com.bernardomg.security.login.service.springframework.SpringValidLoginPredicate;

@DisplayName("SpringSecurityLoginService - password validation")
public class TestDefaultLoginServiceWithSpringUserPassword {

    public TestDefaultLoginServiceWithSpringUserPassword() {
        super();
    }

    @Test
    @DisplayName("Doesn't log in with an invalid password")
    public void testLogIn_Invalid() {
        final LoginStatus     status;
        final DtoLoginRequest login;

        login = new DtoLoginRequest();
        login.setUsername("admin");
        login.setPassword("1234");

        status = getService(false).login(login);

        Assertions.assertFalse(status.getLogged());
        Assertions.assertEquals("admin", status.getUsername());
    }

    @Test
    @DisplayName("Logs in with a valid password")
    public void testLogIn_Valid() {
        final LoginStatus     status;
        final DtoLoginRequest login;

        login = new DtoLoginRequest();
        login.setUsername("admin");
        login.setPassword("1234");

        status = getService(true).login(login);

        Assertions.assertTrue(status.getLogged());
        Assertions.assertEquals("admin", status.getUsername());
    }

    private final DefaultLoginService getService(final Boolean match) {
        final UserDetailsService      userDetService;
        final PasswordEncoder         passEncoder;
        final UserDetails             user;
        final LoginStatusProvider     loginStatusProvider;
        final Predicate<LoginRequest> valid;

        user = new User("username", "password", true, true, true, true, Collections.emptyList());

        userDetService = Mockito.mock(UserDetailsService.class);
        Mockito.when(userDetService.loadUserByUsername(ArgumentMatchers.anyString()))
            .thenReturn(user);

        passEncoder = Mockito.mock(PasswordEncoder.class);
        Mockito.when(passEncoder.matches(ArgumentMatchers.anyString(), ArgumentMatchers.anyString()))
            .thenReturn(match);

        loginStatusProvider = new DefaultLoginStatusProvider();
        valid = new SpringValidLoginPredicate(userDetService, passEncoder);

        return new DefaultLoginService(loginStatusProvider, valid);
    }

}
