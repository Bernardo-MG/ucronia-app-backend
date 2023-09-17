/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2022-2023 the original author or authors.
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

package com.bernardomg.security.jwt.filter;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

import org.apache.logging.log4j.util.Strings;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.bernardomg.security.jwt.token.JwtTokenData;
import com.bernardomg.security.token.TokenDecoder;
import com.bernardomg.security.token.TokenValidator;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

/**
 * JWT token filter. Takes the JWT token from the request, validates it and initializes the authentication.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@Slf4j
public final class JwtTokenFilter extends OncePerRequestFilter {

    /**
     * Token header identifier. This is added before the token to tell which kind of token it is.
     */
    private static final String              TOKEN_HEADER_IDENTIFIER = "Bearer";

    /**
     * Token decoder. Required to acquire the subject.
     */
    private final TokenDecoder<JwtTokenData> tokenDataDecoder;

    /**
     * Token validator.
     */
    private final TokenValidator             tokenValidator;

    /**
     * User details service. Gives access to the user, to validate the token against it.
     */
    private final UserDetailsService         userDetailsService;

    /**
     * Constructs a filter with the received arguments.
     *
     * @param userDetService
     *            user details service
     * @param validator
     *            token validator
     * @param decoder
     *            token decoder
     */
    public JwtTokenFilter(final UserDetailsService userDetService, final TokenValidator validator,
            final TokenDecoder<JwtTokenData> decoder) {
        super();

        userDetailsService = Objects.requireNonNull(userDetService);
        tokenValidator = Objects.requireNonNull(validator);
        tokenDataDecoder = Objects.requireNonNull(decoder);

        // TODO: Test this class
    }

    /**
     * Returns an authentication object created from the user and request.
     *
     * @param userDetails
     *            user for the authentication
     * @param request
     *            request details for the authentication
     * @param token
     *            parsed security token
     * @return an authentication object
     */
    private final Authentication getAuthentication(final UserDetails userDetails, final HttpServletRequest request,
            final String token) {
        final AbstractAuthenticationToken authenticationToken;

        authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, token, userDetails.getAuthorities());
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        return authenticationToken;
    }

    /**
     * Returns the subject from a token.
     *
     * @param token
     *            the token to parse
     * @return the subject from the token if found, or an empty {@code Optional} otherwise
     */
    private final Optional<String> getSubject(final String token) {
        return Optional.ofNullable(tokenDataDecoder.decode(token)
            .getSubject());
    }

    /**
     * Takes the token from the authorization header.
     *
     * @param request
     *            request containing the header with the token
     * @return the token if found, or an empty {@code Optional} otherwise
     */
    private final Optional<String> getToken(final HttpServletRequest request) {
        final String           header;
        final Optional<String> token;

        header = request.getHeader("Authorization");

        if (header == null) {
            // No token received
            token = Optional.empty();
            log.warn("Missing authorization header, can't return token", header);
        } else if ((!Strings.isEmpty(header)) && (header.trim()
            .startsWith(TOKEN_HEADER_IDENTIFIER + " "))) {
            // Token received
            // Take it by removing the identifier
            token = Optional.of(header.substring(TOKEN_HEADER_IDENTIFIER.length())
                .trim());
        } else {
            // Invalid token received
            token = Optional.empty();
            log.warn("Authorization header has an invalid structure, can't return token");
        }

        return token;
    }

    /**
     * Checks if the user is valid. This means it has no flag marking it as not usable.
     *
     * @param userDetails
     *            user the check
     * @return {@code true} if the user is valid, {@code false} otherwise
     */
    private final boolean isValid(final UserDetails userDetails) {
        return userDetails.isAccountNonExpired() && userDetails.isAccountNonLocked()
                && userDetails.isCredentialsNonExpired() && userDetails.isEnabled();
    }

    @Override
    protected final void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response,
            final FilterChain chain) throws ServletException, IOException {
        final Optional<String> token;
        final Optional<String> subject;
        final UserDetails      userDetails;
        final Authentication   authentication;

        log.debug("Authenticating {} request to {}", request.getMethod(), request.getServletPath());

        token = getToken(request);

        if (token.isEmpty()) {
            // Missing header
            log.debug("Missing authorization token");
        } else if (!tokenValidator.hasExpired(token.get())) {
            // Token not expired
            // Will load a new authentication from the token

            // Takes subject from the token
            subject = getSubject(token.get());

            if (subject.isEmpty()) {
                log.debug("Could not find subject in token {}", token.get());
            } else {
                // Found subject
                // Searchs for user
                userDetails = userDetailsService.loadUserByUsername(subject.get());

                if (isValid(userDetails)) {
                    // Create and register authentication
                    authentication = getAuthentication(userDetails, request, token.get());
                    SecurityContextHolder.getContext()
                        .setAuthentication(authentication);

                    // User valid
                    log.debug("Authenticated {} request for {} to {}", request.getMethod(), subject.get(),
                        request.getServletPath());
                } else {
                    log.debug("Invalid user {}", subject.get());
                }
            }
        } else {
            log.debug("Invalid token {}", token.get());
        }

        chain.doFilter(request, response);
    }

}
