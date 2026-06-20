/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2022-2025 Bernardo Martínez Garrido
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.bernardomg.association.fee.test.usecase.service.unit;

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
import com.bernardomg.association.fee.usecase.service.SpringUserSessionProvider;

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
    @DisplayName("When the user is anonymous, nothing is returned")
    void testGetUsername_Anonymous() {
        final ThrowingCallable execution;

        // GIVEN
        given(authentication.getPrincipal()).willReturn(
            new AnonymousAuthenticationToken("key", "principal", List.of(new SimpleGrantedAuthority("role"))));

        SecurityContextHolder.getContext()
            .setAuthentication(authentication);

        // WHEN
        execution = () -> userSessionProvider.getUsername();

        // THEN
        Assertions.assertThatThrownBy(execution)
            .isInstanceOf(AuthenticationCredentialsNotFoundException.class);
    }

    @Test
    @DisplayName("When there is no data nothing is returned")
    void testGetUsername_NoData() {
        final String username;

        // GIVEN
        given(userDetails.getUsername()).willReturn(UserConstants.USERNAME);
        given(authentication.getPrincipal()).willReturn(userDetails);

        SecurityContextHolder.getContext()
            .setAuthentication(authentication);

        // WHEN
        username = userSessionProvider.getUsername();

        // THEN
        Assertions.assertThat(username)
            .isEqualTo(UserConstants.USERNAME);
    }

    @Test
    @DisplayName("When the user has no member, nothing is returned")
    void testGetUsername_NoMember() {
        final String username;

        // GIVEN
        given(userDetails.getUsername()).willReturn(UserConstants.USERNAME);
        given(authentication.getPrincipal()).willReturn(userDetails);

        SecurityContextHolder.getContext()
            .setAuthentication(authentication);

        // WHEN
        username = userSessionProvider.getUsername();

        // THEN
        Assertions.assertThat(username)
            .isEqualTo(UserConstants.USERNAME);
    }

}
