
package com.bernardomg.security.user.authorization.validation;

import java.util.ArrayList;
import java.util.Collection;

import com.bernardomg.security.user.authorization.persistence.repository.ActionRepository;
import com.bernardomg.security.user.authorization.persistence.repository.ResourceRepository;
import com.bernardomg.security.user.authorization.persistence.repository.RoleRepository;
import com.bernardomg.security.user.model.RolePermission;
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
        if (!roleRepository.existsById(relationship.getRoleId())) {
            log.error("Found no role with id {}", relationship.getRoleId());
            // TODO: Is the code not exists or is it not existing? Make sure all use the same
            failure = FieldFailure.of("id", "notExisting", relationship.getRoleId());
            failures.add(failure);
        }

        // The resource exists
        if (!resourceRepository.existsById(relationship.getResourceId())) {
            log.error("Found no resource with id {}", relationship.getResourceId());
            // TODO: Is the code not exists or is it not existing? Make sure all use the same
            failure = FieldFailure.of("resource", "notExisting", relationship.getResourceId());
            failures.add(failure);
        }

        // The action exists
        if (!actionRepository.existsById(relationship.getActionId())) {
            log.error("Found no action with id {}", relationship.getActionId());
            // TODO: Is the code not exists or is it not existing? Make sure all use the same
            failure = FieldFailure.of("action", "notExisting", relationship.getActionId());
            failures.add(failure);
        }

        if (!failures.isEmpty()) {
            log.debug("Got failures: {}", failures);
            throw new FieldFailureException(failures);
        }
    }

}