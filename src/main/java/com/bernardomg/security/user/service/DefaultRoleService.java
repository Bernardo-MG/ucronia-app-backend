
package com.bernardomg.security.user.service;

import java.util.Objects;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.bernardomg.security.user.model.ImmutablePermission;
import com.bernardomg.security.user.model.ImmutableRole;
import com.bernardomg.security.user.model.ImmutableRolePermission;
import com.bernardomg.security.user.model.Permission;
import com.bernardomg.security.user.model.Role;
import com.bernardomg.security.user.model.RolePermission;
import com.bernardomg.security.user.model.request.RoleCreateRequest;
import com.bernardomg.security.user.model.request.RoleQueryRequest;
import com.bernardomg.security.user.model.request.RoleUpdateRequest;
import com.bernardomg.security.user.persistence.model.PersistentRole;
import com.bernardomg.security.user.persistence.model.PersistentRoleGrantedPermission;
import com.bernardomg.security.user.persistence.model.PersistentRolePermission;
import com.bernardomg.security.user.persistence.repository.ActionRepository;
import com.bernardomg.security.user.persistence.repository.ResourceRepository;
import com.bernardomg.security.user.persistence.repository.RoleGrantedPermissionRepository;
import com.bernardomg.security.user.persistence.repository.RolePermissionRepository;
import com.bernardomg.security.user.persistence.repository.RoleRepository;
import com.bernardomg.security.user.persistence.repository.UserRolesRepository;
import com.bernardomg.security.user.service.validation.role.AddRolePermissionValidator;
import com.bernardomg.security.user.service.validation.role.CreateRoleValidator;
import com.bernardomg.security.user.service.validation.role.DeleteRoleValidator;
import com.bernardomg.security.user.service.validation.role.UpdateRoleValidator;
import com.bernardomg.validation.Validator;

@Service
public final class DefaultRoleService implements RoleService {

    private final Validator<RolePermission>       addRolePermissionValidator;

    private final Validator<RoleCreateRequest>    createRoleValidator;

    private final Validator<Long>                 deleteRoleValidator;

    private final Validator<RolePermission>       removeRolePermissionValidator;

    private final RoleGrantedPermissionRepository roleGrantedPermissionRepository;

    private final RolePermissionRepository        rolePermissionRepository;

    private final RoleRepository                  roleRepository;

    private final Validator<RoleUpdateRequest>    updateRoleValidator;

    public DefaultRoleService(final RoleRepository roleRepo, final ResourceRepository resourceRepo,
            final ActionRepository actionRepo, final RolePermissionRepository roleActionsRepo,
            final UserRolesRepository userRolesRepo, final RoleGrantedPermissionRepository roleGrantedPermissionRepo) {
        super();

        roleRepository = Objects.requireNonNull(roleRepo);
        rolePermissionRepository = Objects.requireNonNull(roleActionsRepo);
        roleGrantedPermissionRepository = Objects.requireNonNull(roleGrantedPermissionRepo);

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

        roleAction = ImmutableRolePermission.builder()
            .role(id)
            .resource(resource)
            .action(action)
            .build();
        addRolePermissionValidator.validate(roleAction);

        // Build relationship entities
        relationship = getRelationship(id, resource, action);
        relationship.setGranted(true);

        // Persist relationship entities
        rolePermissionRepository.save(relationship);

        return true;
    }

    @Override
    public final Role create(final RoleCreateRequest role) {
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
    public final Iterable<Role> getAll(final RoleQueryRequest sample, final Pageable pageable) {
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
        return roleGrantedPermissionRepository.findAllByRoleId(id, pageable)
            .map(this::toDto);
    }

    @Override
    public final Boolean removePermission(final Long id, final Long resource, final Long action) {
        final PersistentRolePermission relationship;
        final RolePermission           roleAction;

        roleAction = ImmutableRolePermission.builder()
            .role(id)
            .resource(resource)
            .action(action)
            .build();
        removeRolePermissionValidator.validate(roleAction);

        // Build relationship entities
        relationship = getRelationship(id, resource, action);
        relationship.setGranted(false);

        // Delete relationship entities
        rolePermissionRepository.save(relationship);

        return true;
    }

    @Override
    public final Role update(final RoleUpdateRequest role) {
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
            .build();
    }

    private final Role toDto(final PersistentRole entity) {
        return ImmutableRole.builder()
            .id(entity.getId())
            .name(entity.getName())
            .build();
    }

    private final Permission toDto(final PersistentRoleGrantedPermission entity) {
        return ImmutablePermission.builder()
            .actionId(entity.getActionId())
            .action(entity.getAction())
            .resourceId(entity.getResourceId())
            .resource(entity.getResource())
            .build();
    }

    private final PersistentRole toEntity(final RoleCreateRequest data) {
        return PersistentRole.builder()
            .name(data.getName())
            .build();
    }

    private final PersistentRole toEntity(final RoleQueryRequest data) {
        return PersistentRole.builder()
            .name(data.getName())
            .build();
    }

    private final PersistentRole toEntity(final RoleUpdateRequest data) {
        return PersistentRole.builder()
            .id(data.getId())
            .name(data.getName())
            .build();
    }

}
