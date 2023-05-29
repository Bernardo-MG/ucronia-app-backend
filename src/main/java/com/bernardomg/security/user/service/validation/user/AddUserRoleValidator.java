
package com.bernardomg.security.user.service.validation.user;

import java.util.ArrayList;
import java.util.Collection;

import com.bernardomg.security.user.model.UserRole;
import com.bernardomg.security.user.persistence.repository.RoleRepository;
import com.bernardomg.security.user.persistence.repository.UserRepository;
import com.bernardomg.validation.Validator;
import com.bernardomg.validation.failure.FieldFailure;
import com.bernardomg.validation.failure.exception.FieldFailureException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class AddUserRoleValidator implements Validator<UserRole> {

    private final RoleRepository roleRepository;

    private final UserRepository userRepository;

    public AddUserRoleValidator(final UserRepository userRepo, final RoleRepository roleRepo) {
        super();

        userRepository = userRepo;
        roleRepository = roleRepo;
    }

    @Override
    public final void validate(final UserRole relationship) {
        final Collection<FieldFailure> failures;
        FieldFailure                   failure;

        failures = new ArrayList<>();

        // The user exists
        if (!userRepository.existsById(relationship.getUser())) {
            log.error("Found no user with id {}", relationship.getUser());
            failure = FieldFailure.of("id", "notExisting", relationship.getUser());
            failures.add(failure);
        }

        // The action exists
        if (!roleRepository.existsById(relationship.getRole())) {
            log.error("Found no role with id {}", relationship.getRole());
            failure = FieldFailure.of("role", "notExisting", relationship.getRole());
            failures.add(failure);
        }

        if (!failures.isEmpty()) {
            log.debug("Got failures: {}", failures);
            throw new FieldFailureException(failures);
        }
    }

}
