
package com.bernardomg.security.user.service;

import java.util.Objects;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.bernardomg.security.user.model.ImmutableRolePermission;
import com.bernardomg.security.user.model.Permission;
import com.bernardomg.security.user.model.Role;
import com.bernardomg.security.user.model.RolePermission;
import com.bernardomg.security.user.model.mapper.RoleMapper;
import com.bernardomg.security.user.model.request.RoleCreate;
import com.bernardomg.security.user.model.request.RoleQuery;
import com.bernardomg.security.user.model.request.RoleUpdate;
import com.bernardomg.security.user.persistence.model.PersistentRole;
import com.bernardomg.security.user.persistence.model.PersistentRolePermission;
import com.bernardomg.security.user.persistence.repository.ActionRepository;
import com.bernardomg.security.user.persistence.repository.ResourceRepository;
import com.bernardomg.security.user.persistence.repository.RoleGrantedPermissionRepository;
import com.bernardomg.security.user.persistence.repository.RolePermissionRepository;
import com.bernardomg.security.user.persistence.repository.RoleRepository;
import com.bernardomg.security.user.persistence.repository.UserRolesRepository;
import com.bernardomg.security.user.validation.role.AddRolePermissionValidator;
import com.bernardomg.security.user.validation.role.CreateRoleValidator;
import com.bernardomg.security.user.validation.role.DeleteRoleValidator;
import com.bernardomg.security.user.validation.role.UpdateRoleValidator;
import com.bernardomg.validation.Validator;

@Service
public final class DefaultRoleService implements RoleService {

    private final RoleMapper                      mapper;

    private final RoleGrantedPermissionRepository roleGrantedPermissionRepository;

    private final RolePermissionRepository        rolePermissionRepository;

    private final RoleRepository                  roleRepository;

    private final Validator<RolePermission>       validatorAddRolePermission;

    private final Validator<RoleCreate>           validatorCreateRole;

    private final Validator<Long>                 validatorDeleteRole;

    private final Validator<RolePermission>       validatorRemoveRolePermission;

    private final Validator<RoleUpdate>           validatorUpdateRole;

    public DefaultRoleService(final RoleRepository roleRepo, final ResourceRepository resourceRepo,
            final ActionRepository actionRepo, final RolePermissionRepository roleActionsRepo,
            final UserRolesRepository userRolesRepo, final RoleGrantedPermissionRepository roleGrantedPermissionRepo,
            final RoleMapper roleMapper) {
        super();

        roleRepository = Objects.requireNonNull(roleRepo);
        rolePermissionRepository = Objects.requireNonNull(roleActionsRepo);
        roleGrantedPermissionRepository = Objects.requireNonNull(roleGrantedPermissionRepo);
        mapper = Objects.requireNonNull(roleMapper);

        validatorCreateRole = new CreateRoleValidator(roleRepo);
        validatorUpdateRole = new UpdateRoleValidator(roleRepo);
        validatorDeleteRole = new DeleteRoleValidator(roleRepo, userRolesRepo);

        validatorAddRolePermission = new AddRolePermissionValidator(roleRepo, resourceRepo, actionRepo);
        // TODO: Just validate if the permission exists
        validatorRemoveRolePermission = new AddRolePermissionValidator(roleRepo, resourceRepo, actionRepo);
    }

    @Override
    @PreAuthorize("hasAuthority('ROLE:UPDATE')")
    public final Boolean addPermission(final long id, final long resource, final long action) {
        final PersistentRolePermission rolePermissionSample;
        final RolePermission           roleAction;

        roleAction = ImmutableRolePermission.builder()
            .role(id)
            .resource(resource)
            .action(action)
            .build();
        validatorAddRolePermission.validate(roleAction);

        // Build relationship entities
        rolePermissionSample = getRolePermissionSample(id, resource, action);
        rolePermissionSample.setGranted(true);

        // Persist relationship entities
        rolePermissionRepository.save(rolePermissionSample);

        return true;
    }

    @Override
    @PreAuthorize("hasAuthority('ROLE:CREATE')")
    public final Role create(final RoleCreate role) {
        final PersistentRole entity;
        final PersistentRole created;

        validatorCreateRole.validate(role);

        entity = mapper.toEntity(role);

        created = roleRepository.save(entity);

        return mapper.toDto(created);
    }

    @Override
    @PreAuthorize("hasAuthority('ROLE:DELETE')")
    public final Boolean delete(final long id) {
        validatorDeleteRole.validate(id);

        rolePermissionRepository.deleteAllByRoleId(id);
        roleRepository.deleteById(id);

        return true;
    }

    @Override
    @PreAuthorize("hasAuthority('ROLE:READ')")
    public final Iterable<Role> getAll(final RoleQuery sample, final Pageable pageable) {
        final PersistentRole entitySample;

        entitySample = mapper.toEntity(sample);

        return roleRepository.findAll(Example.of(entitySample), pageable)
            .map(mapper::toDto);
    }

    @Override
    @PreAuthorize("hasAuthority('ROLE:READ')")
    public final Optional<Role> getOne(final long id) {
        return roleRepository.findById(id)
            .map(mapper::toDto);
    }

    @Override
    @PreAuthorize("hasAuthority('ROLE:READ')")
    public final Iterable<Permission> getPermission(final long id, final Pageable pageable) {
        return roleGrantedPermissionRepository.findAllByRoleId(id, pageable)
            .map(mapper::toDto);
    }

    @Override
    @PreAuthorize("hasAuthority('ROLE:UPDATE')")
    public final Boolean removePermission(final long id, final long resource, final long action) {
        final PersistentRolePermission rolePermissionSample;
        final RolePermission           roleAction;

        roleAction = ImmutableRolePermission.builder()
            .role(id)
            .resource(resource)
            .action(action)
            .build();
        validatorRemoveRolePermission.validate(roleAction);

        // Build relationship entities
        rolePermissionSample = getRolePermissionSample(id, resource, action);
        rolePermissionSample.setGranted(false);

        // Delete relationship entities
        rolePermissionRepository.save(rolePermissionSample);

        return true;
    }

    @Override
    @PreAuthorize("hasAuthority('ROLE:UPDATE')")
    public final Role update(final RoleUpdate role) {
        final PersistentRole entity;
        final PersistentRole created;

        validatorUpdateRole.validate(role);

        entity = mapper.toEntity(role);
        created = roleRepository.save(entity);

        return mapper.toDto(created);
    }

    private final PersistentRolePermission getRolePermissionSample(final long role, final long resource,
            final long action) {
        return PersistentRolePermission.builder()
            .roleId(role)
            .resourceId(resource)
            .actionId(action)
            .build();
    }

}
