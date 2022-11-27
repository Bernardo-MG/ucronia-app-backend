
package com.bernardomg.security.data.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.bernardomg.security.data.model.DtoRole;
import com.bernardomg.security.data.model.Privilege;
import com.bernardomg.security.data.model.Role;
import com.bernardomg.security.data.persistence.model.PersistentRole;
import com.bernardomg.security.data.persistence.model.PersistentRolePrivileges;
import com.bernardomg.security.data.persistence.model.PersistentUserRoles;
import com.bernardomg.security.data.persistence.repository.PrivilegeRepository;
import com.bernardomg.security.data.persistence.repository.RolePrivilegesRepository;
import com.bernardomg.security.data.persistence.repository.RoleRepository;
import com.bernardomg.security.data.persistence.repository.UserRolesRepository;
import com.bernardomg.validation.failure.FieldFailure;
import com.bernardomg.validation.failure.exception.FieldFailureException;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public final class DefaultRoleService implements RoleService {

    private final PrivilegeRepository      privilegeRepository;

    private final RoleRepository           repository;

    private final RolePrivilegesRepository rolePrivilegesRepository;

    private final UserRolesRepository      userRolesRepository;

    @Override
    public final Boolean addPrivilege(final Long id, final Long privilege) {
        final PersistentRolePrivileges relationship;
        final DtoRole                  role;
        final Collection<FieldFailure> failures;

        failures = validateRolePrivilegeChange(id);
        failures.addAll(validateAddRolePrivilege(privilege));

        if (!failures.isEmpty()) {
            log.debug("Got failures: {}", failures);
            throw new FieldFailureException(failures);
        }

        role = new DtoRole();
        role.setId(id);

        // Build relationship entities
        relationship = getRelationships(id, privilege);

        // Persist relationship entities
        rolePrivilegesRepository.save(relationship);

        return true;
    }

    @Override
    public final Role create(final Role role) {
        final PersistentRole           entity;
        final PersistentRole           created;
        final Collection<FieldFailure> failures;

        failures = validateCreate(role);

        if (!failures.isEmpty()) {
            log.debug("Got failures: {}", failures);
            throw new FieldFailureException(failures);
        }

        entity = toEntity(role);
        entity.setId(null);

        created = repository.save(entity);

        return toDto(created);
    }

    @Override
    public final Boolean delete(final Long id) {
        final DtoRole                  role;
        final Collection<FieldFailure> failures;

        failures = validateDelete(id);

        if (!failures.isEmpty()) {
            log.debug("Got failures: {}", failures);
            throw new FieldFailureException(failures);
        }

        role = new DtoRole();
        role.setId(id);

        repository.deleteById(id);

        return true;
    }

    @Override
    public final Iterable<? extends Role> getAll(final Role sample, final Pageable pageable) {
        final PersistentRole entity;

        entity = toEntity(sample);

        return repository.findAll(Example.of(entity), pageable)
            .map(this::toDto);
    }

    @Override
    public final Optional<? extends Role> getOne(final Long id) {
        return repository.findById(id)
            .map(this::toDto);
    }

    @Override
    public final Iterable<? extends Privilege> getPrivileges(final Long id, final Pageable pageable) {
        return repository.findAllPrivileges(id, pageable);
    }

    @Override
    public final Boolean removePrivilege(final Long id, final Long privilege) {
        final PersistentRolePrivileges relationship;
        final DtoRole                  role;
        final Collection<FieldFailure> failures;

        failures = validateRolePrivilegeChange(id);
        failures.addAll(validateAddRolePrivilege(privilege));

        if (!failures.isEmpty()) {
            log.debug("Got failures: {}", failures);
            throw new FieldFailureException(failures);
        }

        role = new DtoRole();
        role.setId(id);

        // Build relationship entities
        relationship = getRelationships(id, privilege);

        // Delete relationship entities
        rolePrivilegesRepository.delete(relationship);

        return true;
    }

    @Override
    public final Role update(final Role role) {
        final PersistentRole           entity;
        final PersistentRole           created;
        final Collection<FieldFailure> failures;

        failures = validateUpdate(role);

        if (!failures.isEmpty()) {
            log.debug("Got failures: {}", failures);
            throw new FieldFailureException(failures);
        }

        entity = toEntity(role);

        created = repository.save(entity);

        return toDto(created);
    }

    private final PersistentRolePrivileges getRelationships(final Long role, final Long privilege) {
        final PersistentRolePrivileges relationship;

        relationship = new PersistentRolePrivileges();
        relationship.setRoleId(role);
        relationship.setPrivilegeId(privilege);

        return relationship;
    }

    private final Role toDto(final PersistentRole entity) {
        final DtoRole data;

        data = new DtoRole();
        data.setId(entity.getId());
        data.setName(entity.getName());

        return data;
    }

    private final PersistentRole toEntity(final Role data) {
        final PersistentRole entity;

        entity = new PersistentRole();
        entity.setId(data.getId());
        entity.setName(data.getName());

        return entity;
    }

    private final Collection<FieldFailure> validateAddRolePrivilege(final Long id) {
        final Collection<FieldFailure> failures;
        final FieldFailure             failure;

        failures = new ArrayList<>();

        if (!privilegeRepository.existsById(id)) {
            log.error("Found no privilege with id {}", id);
            failure = FieldFailure.of("privilege", "notExisting", id);
            failures.add(failure);
        }

        return failures;
    }

    private final Collection<FieldFailure> validateCreate(final Role role) {
        final Collection<FieldFailure> failures;
        final FieldFailure             failure;
        final PersistentRole           sample;

        failures = new ArrayList<>();

        if (!failures.isEmpty()) {
            log.debug("Got failures: {}", failures);
            throw new FieldFailureException(failures);
        }

        sample = new PersistentRole();
        sample.setName(role.getName());

        if (repository.exists(Example.of(sample))) {
            log.error("A role already exists with the name {}", role.getName());
            failure = FieldFailure.of("name", "existing", role.getName());
            failures.add(failure);
        }

        return failures;
    }

    private final Collection<FieldFailure> validateDelete(final Long id) {
        final PersistentUserRoles      sample;
        final Collection<FieldFailure> failures;
        FieldFailure                   failure;

        failures = new ArrayList<>();

        if (!repository.existsById(id)) {
            log.error("Found no role with id {}", id);
            failure = FieldFailure.of("id", "notExisting", id);
            failures.add(failure);
        }

        sample = new PersistentUserRoles();
        sample.setRoleId(id);

        if (userRolesRepository.exists(Example.of(sample))) {
            log.error("Role with id {} has a relationship with a user", id);
            failure = FieldFailure.of("user", "existing", id);
            failures.add(failure);
        }

        return failures;
    }

    private final Collection<FieldFailure> validateRolePrivilegeChange(final Long id) {
        final Collection<FieldFailure> failures;
        final FieldFailure             failure;

        failures = new ArrayList<>();

        if (!repository.existsById(id)) {
            log.error("Found no role with id {}", id);
            failure = FieldFailure.of("id", "notExisting", id);
            failures.add(failure);
        }

        return failures;
    }

    private final Collection<FieldFailure> validateUpdate(final Role role) {
        final Collection<FieldFailure> failures;
        final FieldFailure             failure;

        failures = new ArrayList<>();

        if (!repository.existsById(role.getId())) {
            log.error("Found no role with id {}", role.getId());
            failure = FieldFailure.of("id", "notExisting", role.getId());
            failures.add(failure);
        }

        return failures;
    }

}
