
package com.bernardomg.security.data.service.validation.role;

import java.util.ArrayList;
import java.util.Collection;

import com.bernardomg.security.data.model.RolePrivilege;
import com.bernardomg.security.data.persistence.repository.PrivilegeRepository;
import com.bernardomg.security.data.persistence.repository.RoleRepository;
import com.bernardomg.validation.Validator;
import com.bernardomg.validation.failure.FieldFailure;
import com.bernardomg.validation.failure.exception.FieldFailureException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class AddRolePrivilegeValidator implements Validator<RolePrivilege> {

    private final PrivilegeRepository privilegeRepository;

    private final RoleRepository      roleRepository;

    public AddRolePrivilegeValidator(final RoleRepository roleRepo, final PrivilegeRepository privilegeRepo) {
        super();

        roleRepository = roleRepo;
        privilegeRepository = privilegeRepo;
    }

    @Override
    public final void validate(final RolePrivilege relationship) {
        final Collection<FieldFailure> failures;
        FieldFailure                   failure;

        failures = new ArrayList<>();

        // The role exists
        if (!roleRepository.existsById(relationship.getRole())) {
            log.error("Found no role with id {}", relationship.getRole());
            failure = FieldFailure.of("id", "notExisting", relationship.getRole());
            failures.add(failure);
        }

        // The privilege exists
        if (!privilegeRepository.existsById(relationship.getPrivilege())) {
            log.error("Found no privilege with id {}", relationship.getPrivilege());
            failure = FieldFailure.of("privilege", "notExisting", relationship.getPrivilege());
            failures.add(failure);
        }

        if (!failures.isEmpty()) {
            log.debug("Got failures: {}", failures);
            throw new FieldFailureException(failures);
        }
    }

}
