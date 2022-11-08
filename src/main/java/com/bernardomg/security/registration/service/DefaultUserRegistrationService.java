
package com.bernardomg.security.registration.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.regex.Pattern;

import org.springframework.security.crypto.password.PasswordEncoder;

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

    private final Pattern         emailPattern;

    private final String          emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

    /**
     * Password encoder, for saving passwords.
     */
    private final PasswordEncoder passwordEncoder;

    private final UserRepository  repository;

    public DefaultUserRegistrationService(@NonNull final UserRepository repo, final PasswordEncoder passEncoder) {
        super();

        repository = repo;
        passwordEncoder = passEncoder;

        emailPattern = Pattern.compile(emailRegex);
    }

    @Override
    public final User registerUser(final String username, final String email, final String password) {
        final PersistentUser      entity;
        final PersistentUser      created;
        final Collection<Failure> errors;
        final String              encodedPassword;

        errors = validate(username, email);
        if (!errors.isEmpty()) {
            // Validation errors
            throw new ValidationException(errors);
        }

        entity = new PersistentUser();
        entity.setUsername(username);
        entity.setEmail(email);
        entity.setCredentialsExpired(false);
        entity.setEnabled(true);
        entity.setExpired(false);
        entity.setLocked(false);

        // Encode password
        encodedPassword = passwordEncoder.encode(password);
        entity.setPassword(encodedPassword);

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
        final Collection<Failure> result;
        Failure                   error;

        result = new ArrayList<>();

        // Verify no user exists with the received username
        if (repository.existsByUsername(username)) {
            log.error("A user already exists with the username {}", username);
            error = FieldFailure.of("error.username.existing", "roleForm", "memberId", username);
            result.add(error);
        }

        // Verify the email matches the valid pattern
        if (!emailPattern.matcher(email)
            .matches()) {
            log.error("Email {} doesn't follow a valid pattern", email);
            error = FieldFailure.of("error.email.invalid", "roleForm", "memberId", username);
            result.add(error);
        }

        return result;
    }

}
