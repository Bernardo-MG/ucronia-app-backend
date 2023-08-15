
package com.bernardomg.security.user.test.user.unit.service;

import static org.mockito.BDDMockito.given;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;

import com.bernardomg.security.email.sender.SecurityMessageSender;
import com.bernardomg.security.token.store.TokenStore;
import com.bernardomg.security.token.test.constant.TokenConstants;
import com.bernardomg.security.user.authorization.persistence.repository.UserRoleRepository;
import com.bernardomg.security.user.exception.UserDisabledException;
import com.bernardomg.security.user.exception.UserEnabledException;
import com.bernardomg.security.user.exception.UserExpiredException;
import com.bernardomg.security.user.exception.UserLockedException;
import com.bernardomg.security.user.exception.UserNotFoundException;
import com.bernardomg.security.user.model.mapper.UserMapper;
import com.bernardomg.security.user.persistence.model.PersistentUser;
import com.bernardomg.security.user.persistence.repository.UserRepository;
import com.bernardomg.security.user.service.DefaultUserService;
import com.bernardomg.security.user.service.UserService;

@ExtendWith(MockitoExtension.class)
@DisplayName("DefaultUserService - enable new user - authentication")
class TestUserServiceEnableUserAuth {

    private static final String   PASSWORD = "1234";

    private static final String   USERNAME = "username";

    @Mock
    private SecurityMessageSender messageSender;

    @Mock
    private PasswordEncoder       passwordEncoder;

    @Mock
    private UserRepository        repository;

    private UserService           service;

    @Mock
    private TokenStore            tokenStore;

    @Mock
    private UserMapper            userMapper;

    @Mock
    private UserRoleRepository    userRoleRepository;

    public TestUserServiceEnableUserAuth() {
        super();
    }

    @BeforeEach
    public void initializeService() {
        service = new DefaultUserService(repository, userRoleRepository, messageSender, tokenStore, passwordEncoder,
            userMapper, "");
    }

    @BeforeEach
    public void initializeToken() {
        given(tokenStore.exists(ArgumentMatchers.anyString(), ArgumentMatchers.anyString())).willReturn(true);
        given(tokenStore.isValid(ArgumentMatchers.anyString(), ArgumentMatchers.anyString())).willReturn(true);
        given(tokenStore.getUsername(ArgumentMatchers.anyString())).willReturn(USERNAME);
    }

    private final void loadCredentialsExpiredUser() {
        final PersistentUser user;

        user = new PersistentUser();
        user.setEmail("email@somewhere.com");
        user.setUsername(USERNAME);
        user.setPassword(PASSWORD);
        user.setCredentialsExpired(true);
        user.setEnabled(false);
        user.setExpired(false);
        user.setLocked(false);

        given(repository.findOneByUsername(USERNAME)).willReturn(Optional.of(user));
    }

    private final void loadDisabledUser() {
        final PersistentUser user;

        user = new PersistentUser();
        user.setEmail("email@somewhere.com");
        user.setUsername(USERNAME);
        user.setPassword(PASSWORD);
        user.setCredentialsExpired(false);
        user.setEnabled(false);
        user.setExpired(false);
        user.setLocked(false);

        given(repository.findOneByUsername(USERNAME)).willReturn(Optional.of(user));
    }

    private final void loadEnabledUser() {
        final PersistentUser user;

        user = new PersistentUser();
        user.setEmail("email@somewhere.com");
        user.setUsername(USERNAME);
        user.setPassword(PASSWORD);
        user.setCredentialsExpired(false);
        user.setEnabled(true);
        user.setExpired(false);
        user.setLocked(false);

        given(repository.findOneByUsername(USERNAME)).willReturn(Optional.of(user));
    }

    private final void loadExpiredUser() {
        final PersistentUser user;

        user = new PersistentUser();
        user.setEmail("email@somewhere.com");
        user.setUsername(USERNAME);
        user.setPassword(PASSWORD);
        user.setCredentialsExpired(false);
        user.setEnabled(true);
        user.setExpired(true);
        user.setLocked(false);

        given(repository.findOneByUsername(USERNAME)).willReturn(Optional.of(user));
    }

    private final void loadLockedUser() {
        final PersistentUser user;

        user = new PersistentUser();
        user.setEmail("email@somewhere.com");
        user.setUsername(USERNAME);
        user.setPassword(PASSWORD);
        user.setCredentialsExpired(false);
        user.setEnabled(true);
        user.setExpired(false);
        user.setLocked(true);

        given(repository.findOneByUsername(USERNAME)).willReturn(Optional.of(user));
    }

    @Test
    @WithMockUser(username = "admin")
    @DisplayName("Changing password with a user with expired credentials gives a failure")
    @Disabled
    void testEnableNewUser_CredentialsExpired_Exception() {
        final ThrowingCallable executable;
        final Exception        exception;

        loadCredentialsExpiredUser();

        executable = () -> service.activateNewUser(TokenConstants.TOKEN, "1234");

        exception = Assertions.catchThrowableOfType(executable, UserExpiredException.class);

        Assertions.assertThat(exception.getMessage())
            .isEqualTo("User username is expired");
    }

    @Test
    @WithMockUser(username = "admin")
    @DisplayName("Changing password with a disabled user gives a failure")
    @Disabled
    void testEnableNewUser_Disabled_Exception() {
        final ThrowingCallable executable;
        final Exception        exception;

        loadDisabledUser();

        executable = () -> service.activateNewUser(TokenConstants.TOKEN, "1234");

        exception = Assertions.catchThrowableOfType(executable, UserDisabledException.class);

        Assertions.assertThat(exception.getMessage())
            .isEqualTo("User username is disabled");
    }

    @Test
    @WithMockUser(username = "admin")
    @DisplayName("Changing password with an enabled user gives a failure")
    void testEnableNewUser_Enabled_Exception() {
        final ThrowingCallable executable;
        final Exception        exception;

        loadEnabledUser();

        executable = () -> service.activateNewUser(TokenConstants.TOKEN, "1234");

        exception = Assertions.catchThrowableOfType(executable, UserEnabledException.class);

        Assertions.assertThat(exception.getMessage())
            .isEqualTo("User username is enabled");
    }

    @Test
    @WithMockUser(username = "admin")
    @DisplayName("Changing password with a expired user gives a failure")
    void testEnableNewUser_Expired_Exception() {
        final ThrowingCallable executable;
        final Exception        exception;

        loadExpiredUser();

        executable = () -> service.activateNewUser(TokenConstants.TOKEN, "1234");

        exception = Assertions.catchThrowableOfType(executable, UserExpiredException.class);

        Assertions.assertThat(exception.getMessage())
            .isEqualTo("User username is expired");
    }

    @Test
    @WithMockUser(username = "admin")
    @DisplayName("Changing password with a locked user gives a failure")
    void testEnableNewUser_Locked_Exception() {
        final ThrowingCallable executable;
        final Exception        exception;

        loadLockedUser();

        executable = () -> service.activateNewUser(TokenConstants.TOKEN, "1234");

        exception = Assertions.catchThrowableOfType(executable, UserLockedException.class);

        Assertions.assertThat(exception.getMessage())
            .isEqualTo("User username is locked");
    }

    @Test
    @WithMockUser(username = "admin")
    @DisplayName("Changing password with a not existing user gives a failure")
    void testEnableNewUser_NotExistingUser_Exception() {
        final ThrowingCallable executable;
        final Exception        exception;

        executable = () -> service.activateNewUser(TokenConstants.TOKEN, "1234");

        exception = Assertions.catchThrowableOfType(executable, UserNotFoundException.class);

        Assertions.assertThat(exception.getMessage())
            .isEqualTo("Couldn't find user username");
    }

}
