/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2023 the original author or authors.
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

package com.bernardomg.security.login.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.bernardomg.security.jwt.token.ImmutableJwtTokenData;
import com.bernardomg.security.jwt.token.JwtTokenData;
import com.bernardomg.security.login.model.ImmutableLoginStatus;
import com.bernardomg.security.login.model.ImmutableTokenLoginStatus;
import com.bernardomg.security.login.model.LoginStatus;
import com.bernardomg.security.login.model.request.DtoLoginRequest;
import com.bernardomg.security.login.model.request.LoginRequest;
import com.bernardomg.security.permission.persistence.model.PersistentUserGrantedPermission;
import com.bernardomg.security.permission.persistence.repository.UserGrantedPermissionRepository;
import com.bernardomg.security.token.TokenEncoder;
import com.bernardomg.security.user.persistence.model.PersistentUser;
import com.bernardomg.security.user.persistence.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class DefaultLoginService implements LoginService {

    private final Predicate<LoginRequest>         isValid;

    private final Pattern                         pattern = Pattern.compile("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");

    /**
     * Token encoder for creating authentication tokens.
     */
    private final TokenEncoder                    tokenEncoder;

    private final UserGrantedPermissionRepository userGrantedPermissionRepository;

    private final UserRepository                  userRepository;

    /**
     * Token validity time in seconds.
     */
    private final Duration                        validity;

    public DefaultLoginService(final TokenEncoder tknEncoder, final Predicate<LoginRequest> valid,
            final UserRepository userRepo, final UserGrantedPermissionRepository userGrantedPermissionRepo,
            final Duration vldt) {
        super();

        tokenEncoder = Objects.requireNonNull(tknEncoder);
        isValid = Objects.requireNonNull(valid);
        userRepository = Objects.requireNonNull(userRepo);
        userGrantedPermissionRepository = Objects.requireNonNull(userGrantedPermissionRepo);
        validity = Objects.requireNonNull(vldt);
    }

    @Override
    public final LoginStatus login(final LoginRequest login) {
        final Boolean      valid;
        final String       username;
        final String       validUsername;
        final LoginRequest validLogin;

        username = login.getUsername()
            .toLowerCase();

        log.debug("Log in attempt for {}", username);
        validLogin = getLogin(login);

        valid = isValid(validLogin);

        validUsername = validLogin.getUsername()
            .toLowerCase();
        return getStatus(validUsername, valid);
    }

    private final String encode(final String subject, final Map<String, List<String>> permissions) {
        final LocalDateTime expiration;
        final LocalDateTime issuedAt;
        final String        token;
        final JwtTokenData  data;

        // Issued right now
        issuedAt = LocalDateTime.now();
        // Expires in a number of seconds equal to validity
        // TODO: handle validity in the encoder
        expiration = LocalDateTime.now()
            .plus(validity);

        // Build token data for the wrapped encoder
        data = ImmutableJwtTokenData.builder()
            .withSubject(subject)
            .withIssuedAt(issuedAt)
            .withNotBefore(issuedAt)
            .withExpiration(expiration)
            // TODO: Test permissions are added
            .withPermissions(permissions)
            .build();

        token = tokenEncoder.encode(data);

        log.debug("Created token for subject {} with expiration date {}", subject, expiration);

        return token;
    }

    private final LoginRequest getLogin(final LoginRequest login) {
        final Matcher                  matcher;
        final Optional<PersistentUser> readUser;
        final LoginRequest             validLogin;

        matcher = pattern.matcher(login.getUsername());

        if (matcher.find()) {
            // Using email for login
            log.debug("Login attempt with email");
            readUser = userRepository.findOneByEmail(login.getUsername());
            if (readUser.isPresent()) {
                // Get the actual username and continue
                validLogin = DtoLoginRequest.builder()
                    .username(readUser.get()
                        .getUsername())
                    .password(login.getPassword())
                    .build();
            } else {
                log.debug("No user found for email {}", login.getUsername());
                validLogin = login;
            }
        } else {
            // Using username for login
            log.debug("Login attempt with username");
            validLogin = login;
        }

        return validLogin;
    }

    private final Map<String, List<String>> getPermissionsMap(final String username) {
        Function<PersistentUserGrantedPermission, String> resourceMapper;
        Function<PersistentUserGrantedPermission, String> actionMapper;

        // Resource name in lower case
        resourceMapper = PersistentUserGrantedPermission::getResource;
        resourceMapper = resourceMapper.andThen(String::toLowerCase);

        // Action name in lower case
        actionMapper = PersistentUserGrantedPermission::getAction;
        actionMapper = actionMapper.andThen(String::toLowerCase);

        // Transform into a map, with the resource as key, and the list of actions as value
        return userGrantedPermissionRepository.findAllByUsername(username)
            .stream()
            .collect(Collectors.groupingBy(resourceMapper, Collectors.mapping(actionMapper, Collectors.toList())));
    }

    private final LoginStatus getStatus(final String username, final boolean logged) {
        final LoginStatus               status;
        final String                    token;
        final Map<String, List<String>> permissions;

        if (logged) {
            permissions = getPermissionsMap(username);
            // TODO: add permissions to token
            token = encode(username, permissions);
            status = ImmutableTokenLoginStatus.builder()
                .username(username)
                .logged(logged)
                .token(token)
                .build();
        } else {
            status = ImmutableLoginStatus.builder()
                .username(username)
                .logged(logged)
                .build();
        }

        return status;
    }

    private final boolean isValid(final LoginRequest login) {
        final Matcher                  matcher;
        final Optional<PersistentUser> readUser;
        final LoginRequest             validLogin;

        matcher = pattern.matcher(login.getUsername());

        if (matcher.find()) {
            // Using email for login
            log.debug("Login attempt with email");
            readUser = userRepository.findOneByEmail(login.getUsername());
            if (readUser.isPresent()) {
                // Get the actual username and continue
                validLogin = DtoLoginRequest.builder()
                    .username(readUser.get()
                        .getUsername())
                    .password(login.getPassword())
                    .build();
            } else {
                log.debug("No user found for email {}", login.getUsername());
                validLogin = login;
            }
        } else {
            // Using username for login
            log.debug("Login attempt with username");
            validLogin = login;
        }

        return isValid.test(validLogin);
    }

}
