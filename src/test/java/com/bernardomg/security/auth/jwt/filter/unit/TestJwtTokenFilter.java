
package com.bernardomg.security.auth.jwt.filter.unit;

import static org.mockito.BDDMockito.given;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
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

    @Mock
    private TokenDecoder       decoder;

    private JwtTokenFilter     filter;

    @Mock
    private UserDetailsService userDetService;

    @Mock
    private JwtTokenValidator  validator;

    public TestJwtTokenFilter() {
        super();
    }

    @BeforeEach
    public void initializeFilter() {
        filter = new JwtTokenFilter(userDetService, validator, decoder);
    }

    @Test
    @DisplayName("With a valid token the user is stored")
    void testDoFilter() throws ServletException, IOException {
        final HttpServletRequest  request;
        final HttpServletResponse response;
        final FilterChain         filterChain;
        final JwtTokenData        jwtTokenData;
        final UserDetails         userDetails;

        userDetails = Mockito.mock(UserDetails.class);
        given(userDetService.loadUserByUsername("username")).willReturn(userDetails);

        jwtTokenData = ImmutableJwtTokenData.builder()
            .withSubject("username")
            .build();
        given(decoder.decode("token")).willReturn(jwtTokenData);

        request = Mockito.mock(HttpServletRequest.class);
        given(request.getHeader("Authorization")).willReturn("Bearer token");

        response = Mockito.mock(HttpServletResponse.class);
        filterChain = Mockito.mock(FilterChain.class);

        filter.doFilter(request, response, filterChain);

        Mockito.verify(userDetService, Mockito.times(1))
            .loadUserByUsername("username");
    }

}
