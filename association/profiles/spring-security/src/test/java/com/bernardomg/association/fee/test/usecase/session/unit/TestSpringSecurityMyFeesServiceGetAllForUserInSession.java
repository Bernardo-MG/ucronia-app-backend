package com.bernardomg.association.fee.test.usecase.session.unit;

import static org.mockito.BDDMockito.given;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import com.bernardomg.association.fee.test.configuration.factory.UserConstants;
import com.bernardomg.association.fee.usecase.session.SpringUserSessionProvider;

@ExtendWith(MockitoExtension.class)
@DisplayName("SpringUserSessionProvider - get username")
class TestSpringSecurityMyFeesServiceGetAllForUserInSession {

    @Mock
    private Authentication            authentication;

    @Mock
    private UserDetails               userDetails;

    @InjectMocks
    private SpringUserSessionProvider userSessionProvider;

    @Test
    @DisplayName("When the user is anonymous, then an exception is thrown")
    void testGetUsername_Anonymous() {
        final ThrowingCallable action;

        // GIVEN
        given(authentication.getPrincipal()).willReturn(
            new AnonymousAuthenticationToken("key", "principal", List.of(new SimpleGrantedAuthority("role"))));

        SecurityContextHolder.getContext()
            .setAuthentication(authentication);

        // WHEN
        action = () -> userSessionProvider.getUsername();

        // THEN
        Assertions.assertThatThrownBy(action).as("When the user is anonymous, then an exception is thrown")
            .isInstanceOf(AuthenticationCredentialsNotFoundException.class);
    }

    @Test
    @DisplayName("When the user is authenticated, then the username is returned")
    void testGetUsername_Authenticated() {
        final String username;

        // GIVEN
        given(userDetails.getUsername()).willReturn(UserConstants.USERNAME);
        given(authentication.getPrincipal()).willReturn(userDetails);

        SecurityContextHolder.getContext()
            .setAuthentication(authentication);

        // WHEN
        username = userSessionProvider.getUsername();

        // THEN
        Assertions.assertThat(username).as("When the user is authenticated, then the username is returned")
            .isEqualTo(UserConstants.USERNAME);
    }

}
