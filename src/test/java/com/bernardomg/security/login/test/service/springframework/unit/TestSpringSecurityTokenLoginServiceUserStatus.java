
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
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.bernardomg.security.login.model.DtoLogin;
import com.bernardomg.security.login.model.LoginStatus;
import com.bernardomg.security.login.model.TokenLoginStatus;
import com.bernardomg.security.login.service.springframework.SpringSecurityTokenLoginService;
import com.bernardomg.security.test.constant.TokenConstants;
import com.bernardomg.security.token.provider.TokenProvider;

@DisplayName("SpringSecurityTokenLoginService - login with various user status")
public class TestSpringSecurityTokenLoginServiceUserStatus {

    public TestSpringSecurityTokenLoginServiceUserStatus() {
        super();
    }

    @Test
    @DisplayName("Doesn't log in a expired user")
    public void testLogIn_AccountExpired() {
        final LoginStatus status;
        final DtoLogin    login;

        login = new DtoLogin();
        login.setUsername("admin");
        login.setPassword("1234");

        status = getServiceForAccountExpired().login(login);

        Assertions.assertFalse((status instanceof TokenLoginStatus));

        Assertions.assertFalse(status.getLogged());
        Assertions.assertEquals("admin", status.getUsername());
    }

    @Test
    @DisplayName("Doesn't log in a user with expired credentials")
    public void testLogIn_CredentialsExpired() {
        final LoginStatus status;
        final DtoLogin    login;

        login = new DtoLogin();
        login.setUsername("admin");
        login.setPassword("1234");

        status = getServiceForCredentialsExpired().login(login);

        Assertions.assertFalse((status instanceof TokenLoginStatus));

        Assertions.assertFalse(status.getLogged());
        Assertions.assertEquals("admin", status.getUsername());
    }

    @Test
    @DisplayName("Doesn't log in a disabled user")
    public void testLogIn_Disabled() {
        final LoginStatus status;
        final DtoLogin    login;

        login = new DtoLogin();
        login.setUsername("admin");
        login.setPassword("1234");

        status = getServiceForDisabled().login(login);

        Assertions.assertFalse((status instanceof TokenLoginStatus));

        Assertions.assertFalse(status.getLogged());
        Assertions.assertEquals("admin", status.getUsername());
    }

    @Test
    @DisplayName("Doesn't log in a locked user")
    public void testLogIn_Locked() {
        final LoginStatus status;
        final DtoLogin    login;

        login = new DtoLogin();
        login.setUsername("admin");
        login.setPassword("1234");

        status = getServiceForLocked().login(login);

        Assertions.assertFalse((status instanceof TokenLoginStatus));

        Assertions.assertFalse(status.getLogged());
        Assertions.assertEquals("admin", status.getUsername());
    }

    @Test
    @DisplayName("Doesn't log in a not existing user")
    public void testLogIn_NotExisting() {
        final LoginStatus status;
        final DtoLogin    login;

        login = new DtoLogin();
        login.setUsername("admin");
        login.setPassword("1234");

        status = getServiceForNotExisting().login(login);

        Assertions.assertFalse((status instanceof TokenLoginStatus));

        Assertions.assertFalse(status.getLogged());
        Assertions.assertEquals("admin", status.getUsername());
    }

    @Test
    @DisplayName("Logs in with a valid user")
    public void testLogIn_Valid() {
        final LoginStatus status;
        final DtoLogin    login;

        login = new DtoLogin();
        login.setUsername("admin");
        login.setPassword("1234");

        status = getServiceForValid().login(login);

        Assertions.assertTrue(status.getLogged());
        Assertions.assertEquals("admin", status.getUsername());
        Assertions.assertEquals(TokenConstants.TOKEN, ((TokenLoginStatus) status).getToken());
    }

    private final SpringSecurityTokenLoginService getService(final UserDetails user) {
        final UserDetailsService userDetService;
        final PasswordEncoder    passEncoder;
        final TokenProvider      tokenProvider;

        userDetService = Mockito.mock(UserDetailsService.class);
        Mockito.when(userDetService.loadUserByUsername(ArgumentMatchers.anyString()))
            .thenReturn(user);

        passEncoder = Mockito.mock(PasswordEncoder.class);
        Mockito.when(passEncoder.matches(ArgumentMatchers.anyString(), ArgumentMatchers.anyString()))
            .thenReturn(true);

        tokenProvider = Mockito.mock(TokenProvider.class);
        Mockito.when(tokenProvider.generateToken(ArgumentMatchers.anyString()))
            .thenReturn(TokenConstants.TOKEN);

        return new SpringSecurityTokenLoginService(userDetService, passEncoder, tokenProvider);
    }

    private final SpringSecurityTokenLoginService getServiceForAccountExpired() {
        final UserDetails user;

        user = new User("username", "password", true, false, true, true, Collections.emptyList());

        return getService(user);
    }

    private final SpringSecurityTokenLoginService getServiceForCredentialsExpired() {
        final UserDetails user;

        user = new User("username", "password", true, true, false, true, Collections.emptyList());

        return getService(user);
    }

    private final SpringSecurityTokenLoginService getServiceForDisabled() {
        final UserDetails user;

        user = new User("username", "password", false, true, true, true, Collections.emptyList());

        return getService(user);
    }

    private final SpringSecurityTokenLoginService getServiceForLocked() {
        final UserDetails user;

        user = new User("username", "password", true, true, false, true, Collections.emptyList());

        return getService(user);
    }

    private final SpringSecurityTokenLoginService getServiceForNotExisting() {
        final UserDetailsService userDetService;
        final PasswordEncoder    passEncoder;
        final TokenProvider      tokenProvider;

        userDetService = Mockito.mock(UserDetailsService.class);
        Mockito.when(userDetService.loadUserByUsername(ArgumentMatchers.anyString()))
            .thenThrow(UsernameNotFoundException.class);

        passEncoder = Mockito.mock(PasswordEncoder.class);

        tokenProvider = Mockito.mock(TokenProvider.class);
        Mockito.when(tokenProvider.generateToken(ArgumentMatchers.anyString()))
            .thenReturn(TokenConstants.TOKEN);

        return new SpringSecurityTokenLoginService(userDetService, passEncoder, tokenProvider);
    }

    private final SpringSecurityTokenLoginService getServiceForValid() {
        final UserDetails user;

        user = new User("username", "password", true, true, true, true, Collections.emptyList());

        return getService(user);
    }

}
