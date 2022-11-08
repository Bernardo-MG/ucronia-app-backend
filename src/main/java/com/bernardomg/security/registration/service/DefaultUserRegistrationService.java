
package com.bernardomg.security.registration.service;

import java.util.ArrayList;
import java.util.Collection;

import com.bernardomg.mvc.error.model.Failure;
import com.bernardomg.mvc.error.model.FieldFailure;
import com.bernardomg.security.data.model.DtoUser;
import com.bernardomg.security.data.model.User;
import com.bernardomg.security.data.persistence.model.PersistentUser;
import com.bernardomg.security.data.persistence.repository.UserRepository;
import com.bernardomg.validation.exception.ValidationException;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class DefaultUserRegistrationService implements UserRegistrationService {

    private final UserRepository repository;

    public DefaultUserRegistrationService(@NonNull final UserRepository repo) {
        super();

        repository = repo;
    }

    @Override
    public final User registerUser(final String username, final String email, final String password) {
        final PersistentUser      entity;
        final PersistentUser      created;
        final DtoUser             user;
        final Collection<Failure> errors;

        user = new DtoUser();
        user.setUsername(username);
        user.setEmail(email);

        errors = validate(user);
        if (!errors.isEmpty()) {
            // Validation errors
            throw new ValidationException(errors);
        }

        entity = new PersistentUser();
        entity.setUsername(username);
        entity.setEmail(email);
        // TODO: Encode
        entity.setPassword(password);
        entity.setCredentialsExpired(false);
        entity.setEnabled(true);
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

    private final Collection<Failure> validate(final User user) {
        final Collection<Failure> result;
        final Failure             error;

        result = new ArrayList<>();

        // Verify no user exists with the received username
        if (repository.existsByUsername(user.getUsername())) {
            log.error("A user already exists with the username {}", user.getUsername());
            error = FieldFailure.of("error.username.existing", "roleForm", "memberId", user.getUsername());
            result.add(error);
        }

        return result;
    }

}
