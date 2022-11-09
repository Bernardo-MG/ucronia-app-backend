
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
import com.bernardomg.security.signup.validation.EmailValidationRule;
import com.bernardomg.validation.ValidationRule;
import com.bernardomg.validation.exception.ValidationException;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class DefaultSignUpService implements SignUpService {

    private final ValidationRule<String> emailValidationRule = new EmailValidationRule();

    private final UserRepository         repository;

    public DefaultSignUpService(@NonNull final UserRepository repo) {
        super();

        repository = repo;
    }

    @Override
    public final User registerUser(final String username, final String email) {
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

        return toDto(created);
    }

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
