
package com.bernardomg.security.permission.service;

import java.util.Objects;

import org.springframework.data.domain.Pageable;

import com.bernardomg.security.permission.model.Permission;
import com.bernardomg.security.permission.model.mapper.PermissionMapper;
import com.bernardomg.security.permission.model.mapper.RolePermissionMapper;
import com.bernardomg.security.permission.persistence.model.PersistentRolePermission;
import com.bernardomg.security.permission.persistence.repository.ActionRepository;
import com.bernardomg.security.permission.persistence.repository.ResourceRepository;
import com.bernardomg.security.permission.persistence.repository.RoleGrantedPermissionRepository;
import com.bernardomg.security.permission.persistence.repository.RolePermissionRepository;
import com.bernardomg.security.permission.validation.AddRolePermissionValidator;
import com.bernardomg.security.user.model.DtoRolePermission;
import com.bernardomg.security.user.model.RolePermission;
import com.bernardomg.security.user.persistence.repository.RoleRepository;
import com.bernardomg.validation.Validator;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class DefaultRolePermissionService implements RolePermissionService {

    private final PermissionMapper                permissionMapper;

    private final RoleGrantedPermissionRepository roleGrantedPermissionRepository;

    private final RolePermissionMapper            rolePermissionMapper;

    private final RolePermissionRepository        rolePermissionRepository;

    private final Validator<RolePermission>       validatorAddRolePermission;

    private final Validator<RolePermission>       validatorRemoveRolePermission;

    public DefaultRolePermissionService(final RoleRepository roleRepo, final ResourceRepository resourceRepo,
            final ActionRepository actionRepo, final RolePermissionRepository roleActionsRepo,
            final RoleGrantedPermissionRepository roleGrantedPermissionRepo, final RolePermissionMapper rolePermMapper,
            final PermissionMapper permMapper) {
        super();

        rolePermissionRepository = Objects.requireNonNull(roleActionsRepo);
        roleGrantedPermissionRepository = Objects.requireNonNull(roleGrantedPermissionRepo);
        rolePermissionMapper = Objects.requireNonNull(rolePermMapper);
        permissionMapper = Objects.requireNonNull(permMapper);

        validatorAddRolePermission = new AddRolePermissionValidator(roleRepo, resourceRepo, actionRepo);
        // TODO: Just validate if the permission exists
        validatorRemoveRolePermission = new AddRolePermissionValidator(roleRepo, resourceRepo, actionRepo);
    }

    @Override
    public final RolePermission addPermission(final long roleId, final long resourceId, final long actionId) {
        final PersistentRolePermission rolePermissionSample;
        final RolePermission           roleAction;
        final PersistentRolePermission created;

        log.debug("Adding action {} to resource {} for role {}", actionId, resourceId, roleId);

        roleAction = DtoRolePermission.builder()
            .roleId(roleId)
            .resourceId(resourceId)
            .actionId(actionId)
            .build();
        validatorAddRolePermission.validate(roleAction);

        // Build relationship entities
        rolePermissionSample = getRolePermissionSample(roleId, resourceId, actionId);
        rolePermissionSample.setGranted(true);

        // Persist relationship entities
        created = rolePermissionRepository.save(rolePermissionSample);

        return rolePermissionMapper.toDto(created);
    }

    @Override
    public final Iterable<Permission> getPermissions(final long roleId, final Pageable pageable) {

        log.debug("Getting roles for role {} and pagination {}", roleId, pageable);

        return roleGrantedPermissionRepository.findAllByRoleId(roleId, pageable)
            .map(permissionMapper::toDto);
    }

    @Override
    public final RolePermission removePermission(final long roleId, final long resourceId, final long actionId) {
        final PersistentRolePermission rolePermissionSample;
        final RolePermission           roleAction;
        final PersistentRolePermission updated;

        log.debug("Removing action {} to resource {} for role {}", actionId, resourceId, roleId);

        roleAction = DtoRolePermission.builder()
            .roleId(roleId)
            .resourceId(resourceId)
            .actionId(actionId)
            .build();
        validatorRemoveRolePermission.validate(roleAction);

        // Build relationship entities
        rolePermissionSample = getRolePermissionSample(roleId, resourceId, actionId);
        rolePermissionSample.setGranted(false);

        // Delete relationship entities
        updated = rolePermissionRepository.save(rolePermissionSample);

        return rolePermissionMapper.toDto(updated);
    }

    private final PersistentRolePermission getRolePermissionSample(final long roleId, final long resourceId,
            final long actionId) {
        return PersistentRolePermission.builder()
            .roleId(roleId)
            .resourceId(resourceId)
            .actionId(actionId)
            .build();
    }

}
