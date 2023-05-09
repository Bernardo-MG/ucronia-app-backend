
package com.bernardomg.security.login.test.service.springframework.unit;

import java.util.function.Predicate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.bernardomg.security.login.model.DtoLogin;
import com.bernardomg.security.login.model.Login;
import com.bernardomg.security.login.model.LoginStatus;
import com.bernardomg.security.login.service.DefaultLoginService;
import com.bernardomg.security.login.service.DefaultLoginStatusProvider;
import com.bernardomg.security.login.service.LoginStatusProvider;
import com.bernardomg.security.login.service.springframework.SpringValidLoginPredicate;

@DisplayName("SpringSecurityLoginService - failure handling")
public class TestDefaultLoginServiceWithSpringUserFailure {

    public TestDefaultLoginServiceWithSpringUserFailure() {
        super();
    }

    @Test
    @DisplayName("When the user details service returns a null the login fails")
    public void testLogIn_NullUser() {
        final LoginStatus status;
        final DtoLogin    login;

        login = new DtoLogin();
        login.setUsername("admin");
        login.setPassword("1234");

        status = getServiceWithNullUser().login(login);

        Assertions.assertFalse(status.getLogged());
        Assertions.assertEquals("admin", status.getUsername());
    }

    private final DefaultLoginService getService(final UserDetails user) {
        final UserDetailsService  userDetService;
        final PasswordEncoder     passEncoder;
        final LoginStatusProvider loginStatusProvider;
        final Predicate<Login>    valid;

        userDetService = Mockito.mock(UserDetailsService.class);
        Mockito.when(userDetService.loadUserByUsername(ArgumentMatchers.anyString()))
            .thenReturn(user);

        passEncoder = Mockito.mock(PasswordEncoder.class);
        Mockito.when(passEncoder.matches(ArgumentMatchers.anyString(), ArgumentMatchers.anyString()))
            .thenReturn(true);

        loginStatusProvider = new DefaultLoginStatusProvider();
        valid = new SpringValidLoginPredicate(userDetService, passEncoder);

        return new DefaultLoginService(loginStatusProvider, valid);
    }

    private final DefaultLoginService getServiceWithNullUser() {
        return getService(null);
    }

}
