
package com.bernardomg.security.data.service.validation.role;

import java.util.ArrayList;
import java.util.Collection;

import com.bernardomg.security.data.model.RolePermission;
import com.bernardomg.security.data.persistence.repository.ActionRepository;
import com.bernardomg.security.data.persistence.repository.ResourceRepository;
import com.bernardomg.security.data.persistence.repository.RoleRepository;
import com.bernardomg.validation.Validator;
import com.bernardomg.validation.failure.FieldFailure;
import com.bernardomg.validation.failure.exception.FieldFailureException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class AddRolePermissionValidator implements Validator<RolePermission> {

    private final ActionRepository   actionRepository;

    private final ResourceRepository resourceRepository;

    private final RoleRepository     roleRepository;

    public AddRolePermissionValidator(final RoleRepository roleRepo, final ResourceRepository resourceRepo,
            final ActionRepository actionRepo) {
        super();

        roleRepository = roleRepo;
        resourceRepository = resourceRepo;
        actionRepository = actionRepo;
    }

    @Override
    public final void validate(final RolePermission relationship) {
        final Collection<FieldFailure> failures;
        FieldFailure                   failure;

        failures = new ArrayList<>();

        // The role exists
        if (!roleRepository.existsById(relationship.getRole())) {
            log.error("Found no role with id {}", relationship.getRole());
            failure = FieldFailure.of("id", "notExisting", relationship.getRole());
            failures.add(failure);
        }

        // The resource exists
        if (!resourceRepository.existsById(relationship.getResource())) {
            log.error("Found no resource with id {}", relationship.getResource());
            failure = FieldFailure.of("resource", "notExisting", relationship.getResource());
            failures.add(failure);
        }

        // The action exists
        if (!actionRepository.existsById(relationship.getAction())) {
            log.error("Found no action with id {}", relationship.getAction());
            failure = FieldFailure.of("action", "notExisting", relationship.getAction());
            failures.add(failure);
        }

        if (!failures.isEmpty()) {
            log.debug("Got failures: {}", failures);
            throw new FieldFailureException(failures);
        }
    }

}
