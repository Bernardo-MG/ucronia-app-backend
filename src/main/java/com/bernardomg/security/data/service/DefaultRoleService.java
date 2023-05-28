
package com.bernardomg.security.data.service;

import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.bernardomg.security.data.model.ImmutableRole;
import com.bernardomg.security.data.model.ImmutableRolePermission;
import com.bernardomg.security.data.model.Permission;
import com.bernardomg.security.data.model.Role;
import com.bernardomg.security.data.model.RolePermission;
import com.bernardomg.security.data.persistence.model.PersistentRole;
import com.bernardomg.security.data.persistence.model.PersistentRolePermission;
import com.bernardomg.security.data.persistence.repository.ActionRepository;
import com.bernardomg.security.data.persistence.repository.ResourceRepository;
import com.bernardomg.security.data.persistence.repository.RolePermissionRepository;
import com.bernardomg.security.data.persistence.repository.RoleRepository;
import com.bernardomg.security.data.persistence.repository.UserRolesRepository;
import com.bernardomg.security.data.service.validation.role.AddRolePermissionValidator;
import com.bernardomg.security.data.service.validation.role.CreateRoleValidator;
import com.bernardomg.security.data.service.validation.role.DeleteRoleValidator;
import com.bernardomg.security.data.service.validation.role.UpdateRoleValidator;
import com.bernardomg.validation.Validator;

@Service
public final class DefaultRoleService implements RoleService {

    private final Validator<RolePermission> addRolePermissionValidator;

    private final Validator<Role>           createRoleValidator;

    private final Validator<Long>           deleteRoleValidator;

    private final Validator<RolePermission> removeRolePermissionValidator;

    private final RolePermissionRepository  rolePermissionRepository;

    private final RoleRepository            roleRepository;

    private final Validator<Role>           updateRoleValidator;

    public DefaultRoleService(final RoleRepository roleRepo, final ResourceRepository resourceRepo,
            final ActionRepository actionRepo, final RolePermissionRepository roleActionsRepo,
            final UserRolesRepository userRolesRepo) {
        super();

        roleRepository = roleRepo;
        rolePermissionRepository = roleActionsRepo;

        createRoleValidator = new CreateRoleValidator(roleRepo);
        updateRoleValidator = new UpdateRoleValidator(roleRepo);
        deleteRoleValidator = new DeleteRoleValidator(roleRepo, userRolesRepo);

        addRolePermissionValidator = new AddRolePermissionValidator(roleRepo, resourceRepo, actionRepo);
        // TODO: Just validate if the permission exists
        removeRolePermissionValidator = new AddRolePermissionValidator(roleRepo, resourceRepo, actionRepo);
    }

    @Override
    public final Boolean addPermission(final Long id, final Long resource, final Long action) {
        final PersistentRolePermission relationship;
        final RolePermission           roleAction;

        roleAction = new ImmutableRolePermission(id, resource, action);
        addRolePermissionValidator.validate(roleAction);

        // Build relationship entities
        relationship = getRelationship(id, resource, action);

        // Persist relationship entities
        rolePermissionRepository.save(relationship);

        return true;
    }

    @Override
    public final Role create(final Role role) {
        final PersistentRole entity;
        final PersistentRole created;

        createRoleValidator.validate(role);

        entity = toEntity(role);
        entity.setId(null);

        created = roleRepository.save(entity);

        return toDto(created);
    }

    @Override
    public final Boolean delete(final Long id) {
        deleteRoleValidator.validate(id);
        rolePermissionRepository.deleteAllByRoleId(id);
        roleRepository.deleteById(id);

        return true;
    }

    @Override
    public final Iterable<Role> getAll(final Role sample, final Pageable pageable) {
        final PersistentRole entity;

        entity = toEntity(sample);

        return roleRepository.findAll(Example.of(entity), pageable)
            .map(this::toDto);
    }

    @Override
    public final Optional<Role> getOne(final Long id) {
        return roleRepository.findById(id)
            .map(this::toDto);
    }

    @Override
    public final Iterable<Permission> getPermission(final Long id, final Pageable pageable) {
        return roleRepository.findAllPermissions(id, pageable);
    }

    @Override
    public final Boolean removePermission(final Long id, final Long resource, final Long action) {
        final PersistentRolePermission relationship;
        final RolePermission           roleAction;

        roleAction = new ImmutableRolePermission(id, resource, action);
        removeRolePermissionValidator.validate(roleAction);

        // Build relationship entities
        relationship = getRelationship(id, resource, action);
        relationship.setGranted(false);

        // Delete relationship entities
        rolePermissionRepository.save(relationship);

        return true;
    }

    @Override
    public final Role update(final Role role) {
        final PersistentRole entity;
        final PersistentRole created;

        updateRoleValidator.validate(role);

        entity = toEntity(role);

        created = roleRepository.save(entity);

        return toDto(created);
    }

    private final PersistentRolePermission getRelationship(final Long role, final Long resource, final Long action) {
        return PersistentRolePermission.builder()
            .roleId(role)
            .resourceId(resource)
            .actionId(action)
            .granted(true)
            .build();
    }

    private final Role toDto(final PersistentRole entity) {
        return ImmutableRole.builder()
            .id(entity.getId())
            .name(entity.getName())
            .build();
    }

    private final PersistentRole toEntity(final Role data) {
        return PersistentRole.builder()
            .id(data.getId())
            .name(data.getName())
            .build();
    }

}
