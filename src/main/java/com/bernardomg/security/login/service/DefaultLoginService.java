/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2022 the original author or authors.
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

import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.bernardomg.security.login.model.LoginStatus;
import com.bernardomg.security.login.model.request.DtoLoginRequest;
import com.bernardomg.security.login.model.request.LoginRequest;
import com.bernardomg.security.user.persistence.model.PersistentUser;
import com.bernardomg.security.user.persistence.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class DefaultLoginService implements LoginService {

    private final Predicate<LoginRequest> isValid;

    private final LoginStatusProvider     loginStatusProvider;

    private final Pattern                 pattern = Pattern.compile("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");

    private final UserRepository          userRepository;

    public DefaultLoginService(final LoginStatusProvider loginStatusProv, final Predicate<LoginRequest> valid,
            final UserRepository userRepo) {
        super();

        loginStatusProvider = Objects.requireNonNull(loginStatusProv);
        isValid = Objects.requireNonNull(valid);
        userRepository = Objects.requireNonNull(userRepo);
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
        return loginStatusProvider.getStatus(validUsername, valid);
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
