
package com.bernardomg.security.login.test.service.unit;

import static org.mockito.BDDMockito.given;

import java.time.Duration;
import java.util.Collections;
import java.util.Optional;
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

import com.bernardomg.security.auth.jwt.token.TokenEncoder;
import com.bernardomg.security.login.model.LoginStatus;
import com.bernardomg.security.login.model.request.DtoLoginRequest;
import com.bernardomg.security.login.model.request.LoginRequest;
import com.bernardomg.security.login.service.DefaultLoginService;
import com.bernardomg.security.login.service.JwtPermissionTokenEncoder;
import com.bernardomg.security.login.service.LoginTokenEncoder;
import com.bernardomg.security.login.service.springframework.SpringValidLoginPredicate;
import com.bernardomg.security.permission.persistence.repository.UserGrantedPermissionRepository;
import com.bernardomg.security.user.persistence.model.PersistentUser;
import com.bernardomg.security.user.persistence.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
@DisplayName("DefaultLoginService - login with various user status")
class TestDefaultLoginServiceAuth {

    @Mock
    private PasswordEncoder                 passEncoder;

    @Mock
    private TokenEncoder                    tokenEncoder;

    @Mock
    private UserDetailsService              userDetService;

    @Mock
    private UserGrantedPermissionRepository userGrantedPermissionRepository;

    @Mock
    private UserRepository                  userRepository;

    public TestDefaultLoginServiceAuth() {
        super();
    }

    private final DefaultLoginService getService(final UserDetails user) {
        final Predicate<LoginRequest> valid;
        final LoginTokenEncoder       loginTokenEncoder;

        given(userDetService.loadUserByUsername(ArgumentMatchers.anyString())).willReturn(user);

        valid = new SpringValidLoginPredicate(userDetService, passEncoder);

        loginTokenEncoder = new JwtPermissionTokenEncoder(tokenEncoder, userGrantedPermissionRepository, Duration.ZERO);

        return new DefaultLoginService(valid, userRepository, loginTokenEncoder);
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
        final Predicate<LoginRequest> valid;
        final LoginTokenEncoder       loginTokenEncoder;

        given(userDetService.loadUserByUsername(ArgumentMatchers.anyString()))
            .willThrow(UsernameNotFoundException.class);

        valid = new SpringValidLoginPredicate(userDetService, passEncoder);

        loginTokenEncoder = new JwtPermissionTokenEncoder(tokenEncoder, userGrantedPermissionRepository, Duration.ZERO);

        return new DefaultLoginService(valid, userRepository, loginTokenEncoder);
    }

    private final DefaultLoginService getServiceForValid() {
        final UserDetails user;

        user = new User("username", "password", true, true, true, true, Collections.emptyList());

        return getService(user);
    }

    private final void loadUser() {
        final PersistentUser persistentUser;

        persistentUser = new PersistentUser();
        persistentUser.setId(1l);
        persistentUser.setUsername("admin");
        persistentUser.setPassword("email@somewhere.com");
        given(userRepository.findOneByEmail(ArgumentMatchers.anyString())).willReturn(Optional.of(persistentUser));
    }

    @Test
    @DisplayName("Doesn't log in using the email a expired user")
    void testLogIn_Email_AccountExpired() {
        final LoginStatus     status;
        final DtoLoginRequest login;

        loadUser();

        login = new DtoLoginRequest();
        login.setUsername("email@somewhere.com");
        login.setPassword("1234");

        status = getServiceForAccountExpired().login(login);

        Assertions.assertThat(status.getLogged())
            .isFalse();
        Assertions.assertThat(status.getUsername())
            .isEqualTo("admin");
    }

    @Test
    @DisplayName("Doesn't log in using the email a user with expired credentials")
    void testLogIn_Email_CredentialsExpired() {
        final LoginStatus     status;
        final DtoLoginRequest login;

        loadUser();

        login = new DtoLoginRequest();
        login.setUsername("email@somewhere.com");
        login.setPassword("1234");

        status = getServiceForCredentialsExpired().login(login);

        Assertions.assertThat(status.getLogged())
            .isFalse();
        Assertions.assertThat(status.getUsername())
            .isEqualTo("admin");
    }

    @Test
    @DisplayName("Doesn't log in using the email a disabled user")
    void testLogIn_Email_Disabled() {
        final LoginStatus     status;
        final DtoLoginRequest login;

        loadUser();

        login = new DtoLoginRequest();
        login.setUsername("email@somewhere.com");
        login.setPassword("1234");

        status = getServiceForDisabled().login(login);

        Assertions.assertThat(status.getLogged())
            .isFalse();
        Assertions.assertThat(status.getUsername())
            .isEqualTo("admin");
    }

    @Test
    @DisplayName("Doesn't log in using the email a locked user")
    void testLogIn_Email_Locked() {
        final LoginStatus     status;
        final DtoLoginRequest login;

        loadUser();

        login = new DtoLoginRequest();
        login.setUsername("email@somewhere.com");
        login.setPassword("1234");

        status = getServiceForLocked().login(login);

        Assertions.assertThat(status.getLogged())
            .isFalse();
        Assertions.assertThat(status.getUsername())
            .isEqualTo("admin");
    }

    @Test
    @DisplayName("Doesn't log in using the email a not existing user")
    void testLogIn_Email_NotExisting() {
        final LoginStatus     status;
        final DtoLoginRequest login;

        login = new DtoLoginRequest();
        login.setUsername("email@somewhere.com");
        login.setPassword("1234");

        status = getServiceForNotExisting().login(login);

        Assertions.assertThat(status.getLogged())
            .isFalse();
        Assertions.assertThat(status.getUsername())
            .isEqualTo("email@somewhere.com");
    }

    @Test
    @DisplayName("Logs in with a valid email")
    void testLogIn_Email_Valid() {
        final LoginStatus     status;
        final DtoLoginRequest login;

        loadUser();

        given(passEncoder.matches(ArgumentMatchers.anyString(), ArgumentMatchers.anyString())).willReturn(true);
        given(tokenEncoder.encode(ArgumentMatchers.any())).willReturn("token");

        login = new DtoLoginRequest();
        login.setUsername("email@somewhere.com");
        login.setPassword("1234");

        status = getServiceForValid().login(login);

        Assertions.assertThat(status.getLogged())
            .isTrue();
        Assertions.assertThat(status.getUsername())
            .isEqualTo("admin");
    }

    @Test
    @DisplayName("Doesn't log in using the username a expired user")
    void testLogIn_Username_AccountExpired() {
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
    @DisplayName("Doesn't log in using the username a user with expired credentials")
    void testLogIn_Username_CredentialsExpired() {
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
    @DisplayName("Doesn't log in using the username a disabled user")
    void testLogIn_Username_Disabled() {
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
    @DisplayName("Doesn't log in using the username a locked user")
    void testLogIn_Username_Locked() {
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
    @DisplayName("Doesn't log in using the username a not existing user")
    void testLogIn_Username_NotExisting() {
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
    @DisplayName("Logs in with a valid username")
    void testLogIn_Username_Valid() {
        final LoginStatus     status;
        final DtoLoginRequest login;

        given(passEncoder.matches(ArgumentMatchers.anyString(), ArgumentMatchers.anyString())).willReturn(true);
        given(tokenEncoder.encode(ArgumentMatchers.any())).willReturn("token");

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
