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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import com.bernardomg.security.data.persistence.model.PersistentUser;
import com.bernardomg.security.data.persistence.repository.UserRepository;
import com.bernardomg.security.signup.model.ImmutableSignUpStatus;
import com.bernardomg.security.signup.model.SignUp;
import com.bernardomg.security.signup.model.SignUpStatus;
import com.bernardomg.security.validation.EmailValidationRule;
import com.bernardomg.validation.ValidationRule;
import com.bernardomg.validation.failure.Failure;
import com.bernardomg.validation.failure.FieldFailure;
import com.bernardomg.validation.failure.exception.FailureException;

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
     * Email validation rule. To check the email fits into the valid email pattern.
     */
    private final ValidationRule<String> emailValidationRule = new EmailValidationRule();

    /**
     * User repository.
     */
    private final UserRepository         repository;

    public DefaultSignUpService(@NonNull final UserRepository repo) {
        super();

        repository = repo;
    }

    @Override
    public final SignUpStatus signUp(final SignUp signUp) {
        final PersistentUser      entity;
        final PersistentUser      created;
        final Collection<Failure> errors;
        final String              username;
        final String              email;

        errors = validate(signUp);
        if (!errors.isEmpty()) {
            // Validation errors
            throw new FailureException(errors);
        }

        username = signUp.getUsername()
            .toLowerCase();
        email = signUp.getEmail()
            .toLowerCase();
        log.debug("Creating user {} with mail {}", username, email);

        entity = new PersistentUser();
        entity.setUsername(username);
        entity.setPassword("");
        entity.setName(username);
        entity.setEmail(email);
        entity.setCredentialsExpired(false);
        entity.setEnabled(false);
        entity.setExpired(false);
        entity.setLocked(false);

        created = repository.save(entity);

        return new ImmutableSignUpStatus(created.getUsername(), created.getEmail(), true);
    }

    /**
     * Validates the sign up.
     *
     * @param signUp
     *            sign up data
     * @return any validation failure which has ocurred
     */
    private final Collection<Failure> validate(final SignUp signUp) {
        final Collection<Failure> failures;
        final Optional<Failure>   failure;
        Failure                   error;

        failures = new ArrayList<>();

        // Verify no user exists with the received username
        if (repository.existsByUsername(signUp.getUsername()
            .toLowerCase())) {
            log.error("A user already exists with the username {}", signUp.getUsername());
            error = FieldFailure.of("username", "existing", signUp.getUsername());
            failures.add(error);
        }

        // Verify no user exists with the received email
        if (repository.existsByEmail(signUp.getEmail()
            .toLowerCase())) {
            log.error("A user already exists with the email {}", signUp.getEmail());
            error = FieldFailure.of("email", "existing", signUp.getEmail());
            failures.add(error);
        }

        // Verify the email matches the valid pattern
        failure = emailValidationRule.test(signUp.getEmail());
        if (failure.isPresent()) {
            failures.add(failure.get());
        }

        return failures;
    }

}
