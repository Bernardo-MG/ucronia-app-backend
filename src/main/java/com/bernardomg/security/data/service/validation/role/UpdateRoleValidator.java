
package com.bernardomg.security.data.service.validation.role;

import java.util.ArrayList;
import java.util.Collection;

import com.bernardomg.security.data.model.Role;
import com.bernardomg.security.data.persistence.model.PersistentRole;
import com.bernardomg.security.data.persistence.repository.RoleRepository;
import com.bernardomg.validation.Validator;
import com.bernardomg.validation.failure.FieldFailure;
import com.bernardomg.validation.failure.exception.FieldFailureException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class UpdateRoleValidator implements Validator<Role> {

    private final RoleRepository roleRepository;

    public UpdateRoleValidator(final RoleRepository roleRepo) {
        super();

        roleRepository = roleRepo;
    }

    @Override
    public final void validate(final Role role) {
        final Collection<FieldFailure> failures;
        FieldFailure                   failure;
        final PersistentRole           sample;

        failures = new ArrayList<>();

        sample = new PersistentRole();
        sample.setName(role.getName());

        // The role exists
        if (!roleRepository.existsById(role.getId())) {
            log.error("Found no role with id {}", role.getId());
            failure = FieldFailure.of("id", "notExisting", role.getId());
            failures.add(failure);
        }

        if (!failures.isEmpty()) {
            log.debug("Got failures: {}", failures);
            throw new FieldFailureException(failures);
        }
    }

}
