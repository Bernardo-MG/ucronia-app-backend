
package com.bernardomg.security.login.test.service.springframework.unit;

import static org.mockito.BDDMockito.given;

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
import org.springframework.security.crypto.password.PasswordEncoder;

import com.bernardomg.security.login.model.LoginStatus;
import com.bernardomg.security.login.model.TokenLoginStatus;
import com.bernardomg.security.login.model.request.DtoLoginRequest;
import com.bernardomg.security.login.model.request.LoginRequest;
import com.bernardomg.security.login.service.DefaultLoginService;
import com.bernardomg.security.login.service.springframework.SpringValidLoginPredicate;
import com.bernardomg.security.token.TokenEncoder;
import com.bernardomg.security.token.test.constant.TokenConstants;
import com.bernardomg.security.user.persistence.model.PersistentUser;
import com.bernardomg.security.user.persistence.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
@DisplayName("DefaultLoginService - token generation")
class TestDefaultLoginServiceToken {

    @Mock
    private PasswordEncoder      passEncoder;

    @Mock
    private TokenEncoder<String> tokenEncoder;

    @Mock
    private UserDetailsService   userDetService;

    @Mock
    private UserRepository       userRepository;

    public TestDefaultLoginServiceToken() {
        super();
    }

    private final DefaultLoginService getService(final Boolean match) {
        final UserDetails             user;
        final Predicate<LoginRequest> valid;

        user = new User("username", "password", true, true, true, true, Collections.emptyList());

        given(userDetService.loadUserByUsername(ArgumentMatchers.anyString())).willReturn(user);

        given(passEncoder.matches(ArgumentMatchers.anyString(), ArgumentMatchers.anyString())).willReturn(match);

        valid = new SpringValidLoginPredicate(userDetService, passEncoder);

        return new DefaultLoginService(tokenEncoder, valid, userRepository);
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
    @DisplayName("Returns a token login status when the user is logged")
    void testGetStatus_Logged() {
        final LoginStatus     status;
        final DtoLoginRequest login;

        loadUser();

        given(tokenEncoder.encode(ArgumentMatchers.anyString())).willReturn(TokenConstants.TOKEN);

        login = new DtoLoginRequest();
        login.setUsername("email@somewhere.com");
        login.setPassword("1234");

        status = getService(true).login(login);

        Assertions.assertThat(status.getLogged())
            .isTrue();
        Assertions.assertThat(status.getUsername())
            .isEqualTo("admin");
        Assertions.assertThat(((TokenLoginStatus) status).getToken())
            .isEqualTo(TokenConstants.TOKEN);
    }

    @Test
    @DisplayName("Returns a default login status when the user is logged")
    void testGetStatus_NotLogged() {
        final LoginStatus     status;
        final DtoLoginRequest login;

        loadUser();

        login = new DtoLoginRequest();
        login.setUsername("email@somewhere.com");
        login.setPassword("1234");

        status = getService(false).login(login);

        Assertions.assertThat(status)
            .isNotInstanceOf(TokenLoginStatus.class);

        Assertions.assertThat(status.getLogged())
            .isFalse();
        Assertions.assertThat(status.getUsername())
            .isEqualTo("admin");
    }

}
