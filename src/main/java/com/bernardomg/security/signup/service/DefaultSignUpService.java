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

package com.bernardomg.security.signup.service;

import com.bernardomg.security.signup.model.ImmutableSignUpStatus;
import com.bernardomg.security.signup.model.SignUp;
import com.bernardomg.security.signup.model.SignUpStatus;
import com.bernardomg.security.signup.validation.SignUpValidator;
import com.bernardomg.security.user.persistence.model.PersistentUser;
import com.bernardomg.security.user.persistence.repository.UserRepository;
import com.bernardomg.validation.Validator;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

/**
 * Default user sign up service.
 * <h2>Validations</h2>
 * <p>
 * If any of these fails, then the sign up fails.
 * <ul>
 * <li>Received username doesn't exists as a user</li>
 * <li>Received email is a valid email</li>
 * </ul>
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@Slf4j
public final class DefaultSignUpService implements SignUpService {

    /**
     * User repository.
     */
    private final UserRepository    repository;

    private final Validator<SignUp> signUpValidator;

    public DefaultSignUpService(@NonNull final UserRepository repo) {
        super();

        repository = repo;
        signUpValidator = new SignUpValidator(repo);
    }

    @Override
    public final SignUpStatus signUp(final SignUp signUp) {
        final PersistentUser entity;
        final PersistentUser created;
        final String         username;
        final String         email;

        signUpValidator.validate(signUp);

        username = signUp.getUsername()
            .toLowerCase();
        email = signUp.getEmail()
            .toLowerCase();
        log.debug("Creating user {} with mail {}", username, email);

        entity = PersistentUser.builder()
            .username(username)
            .password("")
            .name(username)
            .email(email)
            .credentialsExpired(false)
            .enabled(false)
            .expired(false)
            .locked(false)
            .build();

        created = repository.save(entity);

        return new ImmutableSignUpStatus(created.getUsername(), created.getEmail(), true);
    }

}
