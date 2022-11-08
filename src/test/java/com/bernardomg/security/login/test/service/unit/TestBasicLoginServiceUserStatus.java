
package com.bernardomg.security.login.test.service.unit;

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

import com.bernardomg.security.login.model.LoginStatus;
import com.bernardomg.security.login.service.BasicLoginService;

@DisplayName("Basic login service - login with various user status")
public class TestBasicLoginServiceUserStatus {

    public TestBasicLoginServiceUserStatus() {
        super();
    }

    @Test
    @DisplayName("Doesn't log in a expired user")
    public void testLogIn_AccountExpired() {
        final LoginStatus details;

        details = getServiceForAccountExpired().login("admin", "1234");

        Assertions.assertFalse(details.getLogged());
        Assertions.assertEquals("admin", details.getUsername());
    }

    @Test
    @DisplayName("Doesn't log in a user with expired credentials")
    public void testLogIn_CredentialsExpired() {
        final LoginStatus details;

        details = getServiceForCredentialsExpired().login("admin", "1234");

        Assertions.assertFalse(details.getLogged());
        Assertions.assertEquals("admin", details.getUsername());
    }

    @Test
    @DisplayName("Doesn't log in a disabled user")
    public void testLogIn_Disabled() {
        final LoginStatus details;

        details = getServiceForDisabled().login("admin", "1234");

        Assertions.assertFalse(details.getLogged());
        Assertions.assertEquals("admin", details.getUsername());
    }

    @Test
    @DisplayName("Doesn't log in a locked user")
    public void testLogIn_Locked() {
        final LoginStatus details;

        details = getServiceForLocked().login("admin", "1234");

        Assertions.assertFalse(details.getLogged());
        Assertions.assertEquals("admin", details.getUsername());
    }

    @Test
    @DisplayName("Doesn't log in a not existing user")
    public void testLogIn_NotExisting() {
        final LoginStatus details;

        details = getServiceForNotExisting().login("admin", "1234");

        Assertions.assertFalse(details.getLogged());
        Assertions.assertEquals("admin", details.getUsername());
    }

    @Test
    @DisplayName("Logs in with a valid user")
    public void testLogIn_Valid() {
        final LoginStatus details;

        details = getServiceForValid().login("admin", "1234");

        Assertions.assertTrue(details.getLogged());
        Assertions.assertEquals("admin", details.getUsername());
    }

    private final BasicLoginService getService(final UserDetails user) {
        final UserDetailsService userDetService;
        final PasswordEncoder    passEncoder;

        userDetService = Mockito.mock(UserDetailsService.class);
        Mockito.when(userDetService.loadUserByUsername(ArgumentMatchers.anyString()))
            .thenReturn(user);

        passEncoder = Mockito.mock(PasswordEncoder.class);
        Mockito.when(passEncoder.matches(ArgumentMatchers.anyString(), ArgumentMatchers.anyString()))
            .thenReturn(true);

        return new BasicLoginService(userDetService, passEncoder);
    }

    private final BasicLoginService getServiceForAccountExpired() {
        final UserDetails user;

        user = new User("username", "password", true, false, true, true, Collections.emptyList());

        return getService(user);
    }

    private final BasicLoginService getServiceForCredentialsExpired() {
        final UserDetails user;

        user = new User("username", "password", true, true, false, true, Collections.emptyList());

        return getService(user);
    }

    private final BasicLoginService getServiceForDisabled() {
        final UserDetails user;

        user = new User("username", "password", false, true, true, true, Collections.emptyList());

        return getService(user);
    }

    private final BasicLoginService getServiceForLocked() {
        final UserDetails user;

        user = new User("username", "password", true, true, false, true, Collections.emptyList());

        return getService(user);
    }

    private final BasicLoginService getServiceForNotExisting() {
        final UserDetailsService userDetService;
        final PasswordEncoder    passEncoder;

        userDetService = Mockito.mock(UserDetailsService.class);
        Mockito.when(userDetService.loadUserByUsername(ArgumentMatchers.anyString()))
            .thenThrow(UsernameNotFoundException.class);

        passEncoder = Mockito.mock(PasswordEncoder.class);

        return new BasicLoginService(userDetService, passEncoder);
    }

    private final BasicLoginService getServiceForValid() {
        final UserDetails user;

        user = new User("username", "password", true, true, true, true, Collections.emptyList());

        return getService(user);
    }

}
