
package com.bernardomg.security.login.test.service.springframework.unit;

import static org.mockito.BDDMockito.given;

import java.util.Collections;
import java.util.function.Predicate;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.bernardomg.security.login.model.LoginStatus;
import com.bernardomg.security.login.model.request.DtoLoginRequest;
import com.bernardomg.security.login.model.request.LoginRequest;
import com.bernardomg.security.login.service.DefaultLoginService;
import com.bernardomg.security.login.service.DefaultLoginStatusProvider;
import com.bernardomg.security.login.service.LoginStatusProvider;
import com.bernardomg.security.login.service.springframework.SpringValidLoginPredicate;

@ExtendWith(MockitoExtension.class)
@DisplayName("SpringSecurityLoginService - login with various user status")
class TestDefaultLoginServiceWithSpringUserStatus {

    @Mock
    private PasswordEncoder    passEncoder;

    @Mock
    private UserDetailsService userDetService;

    public TestDefaultLoginServiceWithSpringUserStatus() {
        super();
    }

    private final DefaultLoginService getService(final UserDetails user) {
        final LoginStatusProvider     loginStatusProvider;
        final Predicate<LoginRequest> valid;

        given(userDetService.loadUserByUsername(ArgumentMatchers.anyString())).willReturn(user);

        loginStatusProvider = new DefaultLoginStatusProvider();
        valid = new SpringValidLoginPredicate(userDetService, passEncoder);

        return new DefaultLoginService(loginStatusProvider, valid);
    }

    private final DefaultLoginService getServiceForAccountExpired() {
        final UserDetails user;

        user = new User("username", "password", true, false, true, true, Collections.emptyList());

        return getService(user);
    }

    private final DefaultLoginService getServiceForCredentialsExpired() {
        final UserDetails user;

        user = new User("username", "password", true, true, false, true, Collections.emptyList());

        return getService(user);
    }

    private final DefaultLoginService getServiceForDisabled() {
        final UserDetails user;

        user = new User("username", "password", false, true, true, true, Collections.emptyList());

        return getService(user);
    }

    private final DefaultLoginService getServiceForLocked() {
        final UserDetails user;

        user = new User("username", "password", true, true, false, true, Collections.emptyList());

        return getService(user);
    }

    private final DefaultLoginService getServiceForNotExisting() {
        final LoginStatusProvider     loginStatusProvider;
        final Predicate<LoginRequest> valid;

        given(userDetService.loadUserByUsername(ArgumentMatchers.anyString()))
            .willThrow(UsernameNotFoundException.class);

        loginStatusProvider = new DefaultLoginStatusProvider();
        valid = new SpringValidLoginPredicate(userDetService, passEncoder);

        return new DefaultLoginService(loginStatusProvider, valid);
    }

    private final DefaultLoginService getServiceForValid() {
        final UserDetails user;

        user = new User("username", "password", true, true, true, true, Collections.emptyList());

        return getService(user);
    }

    @Test
    @DisplayName("Doesn't log in a expired user")
    void testLogIn_AccountExpired() {
        final LoginStatus     status;
        final DtoLoginRequest login;

        login = new DtoLoginRequest();
        login.setUsername("admin");
        login.setPassword("1234");

        status = getServiceForAccountExpired().login(login);

        Assertions.assertThat(status.getLogged())
            .isFalse();
        Assertions.assertThat(status.getUsername())
            .isEqualTo("admin");
    }

    @Test
    @DisplayName("Doesn't log in a user with expired credentials")
    void testLogIn_CredentialsExpired() {
        final LoginStatus     status;
        final DtoLoginRequest login;

        login = new DtoLoginRequest();
        login.setUsername("admin");
        login.setPassword("1234");

        status = getServiceForCredentialsExpired().login(login);

        Assertions.assertThat(status.getLogged())
            .isFalse();
        Assertions.assertThat(status.getUsername())
            .isEqualTo("admin");
    }

    @Test
    @DisplayName("Doesn't log in a disabled user")
    void testLogIn_Disabled() {
        final LoginStatus     status;
        final DtoLoginRequest login;

        login = new DtoLoginRequest();
        login.setUsername("admin");
        login.setPassword("1234");

        status = getServiceForDisabled().login(login);

        Assertions.assertThat(status.getLogged())
            .isFalse();
        Assertions.assertThat(status.getUsername())
            .isEqualTo("admin");
    }

    @Test
    @DisplayName("Doesn't log in a locked user")
    void testLogIn_Locked() {
        final LoginStatus     status;
        final DtoLoginRequest login;

        login = new DtoLoginRequest();
        login.setUsername("admin");
        login.setPassword("1234");

        status = getServiceForLocked().login(login);

        Assertions.assertThat(status.getLogged())
            .isFalse();
        Assertions.assertThat(status.getUsername())
            .isEqualTo("admin");
    }

    @Test
    @DisplayName("Doesn't log in a not existing user")
    void testLogIn_NotExisting() {
        final LoginStatus     status;
        final DtoLoginRequest login;

        login = new DtoLoginRequest();
        login.setUsername("admin");
        login.setPassword("1234");

        status = getServiceForNotExisting().login(login);

        Assertions.assertThat(status.getLogged())
            .isFalse();
        Assertions.assertThat(status.getUsername())
            .isEqualTo("admin");
    }

    @Test
    @DisplayName("Logs in with a valid user")
    void testLogIn_Valid() {
        final LoginStatus     status;
        final DtoLoginRequest login;

        given(passEncoder.matches(ArgumentMatchers.anyString(), ArgumentMatchers.anyString())).willReturn(true);

        login = new DtoLoginRequest();
        login.setUsername("admin");
        login.setPassword("1234");

        status = getServiceForValid().login(login);

        Assertions.assertThat(status.getLogged())
            .isTrue();
        Assertions.assertThat(status.getUsername())
            .isEqualTo("admin");
    }

}
