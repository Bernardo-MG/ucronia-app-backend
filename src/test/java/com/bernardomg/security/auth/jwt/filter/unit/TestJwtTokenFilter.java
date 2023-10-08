
package com.bernardomg.security.auth.jwt.filter.unit;

import static org.mockito.BDDMockito.given;

import java.io.IOException;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.bernardomg.security.auth.jwt.filter.JwtTokenFilter;
import com.bernardomg.security.auth.jwt.token.ImmutableJwtTokenData;
import com.bernardomg.security.auth.jwt.token.JwtTokenData;
import com.bernardomg.security.auth.jwt.token.JwtTokenValidator;
import com.bernardomg.security.auth.jwt.token.TokenDecoder;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@ExtendWith(MockitoExtension.class)
@DisplayName("JwtTokenFilter")
class TestJwtTokenFilter {

    private static final String HEADER_BEARER = "Bearer token";

    private static final String TOKEN         = "token";

    private static final String USERNAME      = "username";

    @Mock
    private TokenDecoder        decoder;

    private JwtTokenFilter      filter;

    @Mock
    private UserDetailsService  userDetService;

    @Mock
    private JwtTokenValidator   validator;

    public TestJwtTokenFilter() {
        super();
    }

    @BeforeEach
    public void initializeFilter() {
        filter = new JwtTokenFilter(userDetService, validator, decoder);
    }

    private final UserDetails getValidUserDetails() {
        final UserDetails userDetails;

        userDetails = Mockito.mock(UserDetails.class);
        Mockito.when(userDetails.getUsername())
            .thenReturn(USERNAME);
        Mockito.when(userDetails.isAccountNonExpired())
            .thenReturn(true);
        Mockito.when(userDetails.isAccountNonLocked())
            .thenReturn(true);
        Mockito.when(userDetails.isCredentialsNonExpired())
            .thenReturn(true);
        Mockito.when(userDetails.isEnabled())
            .thenReturn(true);

        return userDetails;
    }

    @Test
    @DisplayName("With a valid token the user is stored")
    void testDoFilter() throws ServletException, IOException {
        final HttpServletRequest  request;
        final HttpServletResponse response;
        final FilterChain         filterChain;
        final JwtTokenData        jwtTokenData;
        final UserDetails         userDetails;
        final Authentication      authentication;

        // GIVEN
        given(validator.hasExpired(TOKEN)).willReturn(false);

        userDetails = getValidUserDetails();
        given(userDetService.loadUserByUsername(USERNAME)).willReturn(userDetails);

        jwtTokenData = ImmutableJwtTokenData.builder()
            .withSubject(USERNAME)
            .build();
        given(decoder.decode(TOKEN)).willReturn(jwtTokenData);

        request = Mockito.mock(HttpServletRequest.class);
        given(request.getHeader("Authorization")).willReturn(HEADER_BEARER);

        response = Mockito.mock(HttpServletResponse.class);
        filterChain = Mockito.mock(FilterChain.class);

        // WHEN
        filter.doFilter(request, response, filterChain);

        // THEN
        authentication = SecurityContextHolder.getContext()
            .getAuthentication();
        Assertions.assertThat(authentication.getName())
            .isEqualTo(USERNAME);
    }

    @Test
    @DisplayName("With a expired token no user is stored")
    void testDoFilter_InvalidToken() throws ServletException, IOException {
        final HttpServletRequest  request;
        final HttpServletResponse response;
        final FilterChain         filterChain;

        given(validator.hasExpired(TOKEN)).willReturn(true);

        request = Mockito.mock(HttpServletRequest.class);
        given(request.getHeader("Authorization")).willReturn("Bearer " + TOKEN);

        response = Mockito.mock(HttpServletResponse.class);
        filterChain = Mockito.mock(FilterChain.class);

        filter.doFilter(request, response, filterChain);

        Mockito.verify(userDetService, Mockito.never())
            .loadUserByUsername(USERNAME);
    }

    @Test
    @DisplayName("With no authorization header no user is stored")
    void testDoFilter_NoHeader() throws ServletException, IOException {
        final HttpServletRequest  request;
        final HttpServletResponse response;
        final FilterChain         filterChain;

        request = Mockito.mock(HttpServletRequest.class);
        response = Mockito.mock(HttpServletResponse.class);
        filterChain = Mockito.mock(FilterChain.class);

        filter.doFilter(request, response, filterChain);

        Mockito.verify(userDetService, Mockito.never())
            .loadUserByUsername(USERNAME);
    }

}
