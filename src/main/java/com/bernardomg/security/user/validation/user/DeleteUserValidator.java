
package com.bernardomg.security.user.validation.user;

import java.util.Collection;

import com.bernardomg.security.user.persistence.repository.UserRepository;
import com.bernardomg.validation.AbstractValidator;
import com.bernardomg.validation.failure.FieldFailure;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class DeleteUserValidator extends AbstractValidator<Long> {

    private final UserRepository userRepository;

    public DeleteUserValidator(final UserRepository userRepo) {
        super();

        userRepository = userRepo;
    }

    @Override
    protected final void checkRules(final Long id, final Collection<FieldFailure> failures) {
        FieldFailure failure;

        // User exists
        if (!userRepository.existsById(id)) {
            log.error("Found no user with id {}", id);
            // TODO: Is the code exists or is it existing? Make sure all use the same
            failure = FieldFailure.of("id", "notExisting", id);
            failures.add(failure);
        }
    }

}
