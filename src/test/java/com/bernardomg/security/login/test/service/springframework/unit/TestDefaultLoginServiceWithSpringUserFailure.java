
package com.bernardomg.security.login.test.service.springframework.unit;

import static org.mockito.BDDMockito.given;

import java.util.function.Predicate;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
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

@ExtendWith(MockitoExtension.class)
@DisplayName("SpringSecurityLoginService - failure handling")
class TestDefaultLoginServiceWithSpringUserFailure {

    @Mock
    private PasswordEncoder    passEncoder;

    @Mock
    private UserDetailsService userDetService;

    public TestDefaultLoginServiceWithSpringUserFailure() {
        super();
    }

    @Test
    @DisplayName("When the user details service returns a null the login fails")
    void testLogIn_NullUser() {
        final LoginStatus     status;
        final DtoLoginRequest login;

        login = new DtoLoginRequest();
        login.setUsername("admin");
        login.setPassword("1234");

        status = getServiceWithNullUser().login(login);

        Assertions.assertThat(status.getLogged())
            .isFalse();
        Assertions.assertThat(status.getUsername())
            .isEqualTo("admin");
    }

    private final DefaultLoginService getService(final UserDetails user) {
        final LoginStatusProvider     loginStatusProvider;
        final Predicate<LoginRequest> valid;

        given(userDetService.loadUserByUsername(ArgumentMatchers.anyString())).willReturn(user);

        loginStatusProvider = new DefaultLoginStatusProvider();
        valid = new SpringValidLoginPredicate(userDetService, passEncoder);

        return new DefaultLoginService(loginStatusProvider, valid);
    }

    private final DefaultLoginService getServiceWithNullUser() {
        return getService(null);
    }

}
