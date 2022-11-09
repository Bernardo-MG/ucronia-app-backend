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

import com.bernardomg.mvc.error.model.Failure;
import com.bernardomg.mvc.error.model.FieldFailure;
import com.bernardomg.security.data.model.DtoUser;
import com.bernardomg.security.data.model.User;
import com.bernardomg.security.data.persistence.model.PersistentUser;
import com.bernardomg.security.data.persistence.repository.UserRepository;
import com.bernardomg.security.email.sender.SecurityEmailSender;
import com.bernardomg.security.signup.validation.EmailValidationRule;
import com.bernardomg.validation.ValidationRule;
import com.bernardomg.validation.exception.ValidationException;

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

    private final SecurityEmailSender    mailSender;

    /**
     * User repository.
     */
    private final UserRepository         repository;

    public DefaultSignUpService(@NonNull final UserRepository repo, @NonNull final SecurityEmailSender mSender) {
        super();

        repository = repo;
        mailSender = mSender;
    }

    @Override
    public final User signUp(final String username, final String email) {
        final PersistentUser      entity;
        final PersistentUser      created;
        final Collection<Failure> errors;

        errors = validate(username, email);
        if (!errors.isEmpty()) {
            // Validation errors
            throw new ValidationException(errors);
        }

        entity = new PersistentUser();
        entity.setUsername(username);
        entity.setPassword("");
        entity.setEmail(email);
        entity.setCredentialsExpired(false);
        entity.setEnabled(false);
        entity.setExpired(false);
        entity.setLocked(false);

        created = repository.save(entity);

        // Sends success email
        mailSender.sendSignUpEmail(username, email);

        return toDto(created);
    }

    /**
     * Transforms the entity into a DTO.
     *
     * @param entity
     *            entity to transform
     * @return equivalent DTO
     */
    private final User toDto(final PersistentUser entity) {
        final DtoUser data;

        data = new DtoUser();
        data.setId(entity.getId());
        data.setUsername(entity.getUsername());
        data.setEmail(entity.getEmail());
        data.setCredentialsExpired(entity.getCredentialsExpired());
        data.setEnabled(entity.getEnabled());
        data.setExpired(entity.getExpired());
        data.setLocked(entity.getLocked());

        return data;
    }

    /**
     * Validates the sign up.
     *
     * @param username
     *            username to authenticate
     * @param password
     *            password to authenticate
     * @return any validation failure which has ocurred
     */
    private final Collection<Failure> validate(final String username, final String email) {
        final Collection<Failure> failures;
        final Optional<Failure>   failure;
        Failure                   error;

        failures = new ArrayList<>();

        // Verify no user exists with the received username
        if (repository.existsByUsername(username)) {
            log.error("A user already exists with the username {}", username);
            error = FieldFailure.of("error.username.existing", "roleForm", "memberId", username);
            failures.add(error);
        }

        // Verify the email matches the valid pattern
        failure = emailValidationRule.test(email);
        if (failure.isPresent()) {
            failures.add(failure.get());
        }

        return failures;
    }

}
