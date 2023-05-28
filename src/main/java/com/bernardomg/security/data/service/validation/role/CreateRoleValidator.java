
package com.bernardomg.security.data.service.validation.role;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.data.domain.Example;

import com.bernardomg.security.data.model.Role;
import com.bernardomg.security.data.persistence.model.PersistentRole;
import com.bernardomg.security.data.persistence.repository.RoleRepository;
import com.bernardomg.validation.Validator;
import com.bernardomg.validation.failure.FieldFailure;
import com.bernardomg.validation.failure.exception.FieldFailureException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class CreateRoleValidator implements Validator<Role> {

    private final RoleRepository roleRepository;

    public CreateRoleValidator(final RoleRepository roleRepo) {
        super();

        roleRepository = roleRepo;
    }

    @Override
    public final void validate(final Role role) {
        final Collection<FieldFailure> failures;
        final FieldFailure             failure;
        final PersistentRole           sample;

        failures = new ArrayList<>();

        sample = PersistentRole.builder()
            .name(role.getName())
            .build();

        // The role doesn't exist
        if (roleRepository.exists(Example.of(sample))) {
            log.error("A role already exists with the name {}", role.getName());
            failure = FieldFailure.of("name", "existing", role.getName());
            failures.add(failure);
        }

        if (!failures.isEmpty()) {
            log.debug("Got failures: {}", failures);
            throw new FieldFailureException(failures);
        }
    }

}
