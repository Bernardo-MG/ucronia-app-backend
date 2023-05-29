
package com.bernardomg.security.user.service.validation.user;

import java.util.ArrayList;
import java.util.Collection;

import com.bernardomg.security.user.persistence.repository.UserRepository;
import com.bernardomg.validation.Validator;
import com.bernardomg.validation.failure.FieldFailure;
import com.bernardomg.validation.failure.exception.FieldFailureException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class DeleteUserValidator implements Validator<Long> {

    private final UserRepository userRepository;

    public DeleteUserValidator(final UserRepository userRepo) {
        super();

        userRepository = userRepo;
    }

    @Override
    public final void validate(final Long id) {
        final Collection<FieldFailure> failures;
        FieldFailure                   failure;

        failures = new ArrayList<>();

        // User exists
        if (!userRepository.existsById(id)) {
            log.error("Found no user with id {}", id);
            failure = FieldFailure.of("id", "notExisting", id);
            failures.add(failure);
        }

        if (!failures.isEmpty()) {
            log.debug("Got failures: {}", failures);
            throw new FieldFailureException(failures);
        }
    }

}
