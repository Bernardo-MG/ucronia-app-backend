
package com.bernardomg.security.permission.service;

import java.util.Objects;
import java.util.Optional;

import org.springframework.data.domain.Pageable;

import com.bernardomg.security.permission.model.Permission;
import com.bernardomg.security.permission.model.mapper.PermissionMapper;
import com.bernardomg.security.permission.model.mapper.RolePermissionMapper;
import com.bernardomg.security.permission.persistence.model.PersistentPermission;
import com.bernardomg.security.permission.persistence.model.PersistentRolePermission;
import com.bernardomg.security.permission.persistence.repository.PermissionRepository;
import com.bernardomg.security.permission.persistence.repository.RolePermissionRepository;
import com.bernardomg.security.permission.validation.AddRolePermissionValidator;
import com.bernardomg.security.permission.validation.RemoveRolePermissionValidator;
import com.bernardomg.security.user.model.DtoRolePermission;
import com.bernardomg.security.user.model.RolePermission;
import com.bernardomg.security.user.persistence.repository.RoleRepository;
import com.bernardomg.validation.Validator;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class DefaultRolePermissionService implements RolePermissionService {

    private final PermissionMapper          permissionMapper;

    private final PermissionRepository      permissionRepository;

    private final RolePermissionMapper      rolePermissionMapper;

    private final RolePermissionRepository  rolePermissionRepository;

    private final Validator<RolePermission> validatorAddRolePermission;

    private final Validator<RolePermission> validatorRemoveRolePermission;

    public DefaultRolePermissionService(final RoleRepository roleRepo, final PermissionRepository permissionRepo,
            final RolePermissionRepository rolePermissionRepo, final RolePermissionMapper rolePermMapper,
            final PermissionMapper permMapper) {
        super();

        permissionRepository = Objects.requireNonNull(permissionRepo);
        rolePermissionRepository = Objects.requireNonNull(rolePermissionRepo);
        rolePermissionMapper = Objects.requireNonNull(rolePermMapper);
        permissionMapper = Objects.requireNonNull(permMapper);

        validatorAddRolePermission = new AddRolePermissionValidator(roleRepo, permissionRepo);
        validatorRemoveRolePermission = new RemoveRolePermissionValidator(rolePermissionRepo);
    }

    @Override
    public final RolePermission addPermission(final long roleId, final Long permission) {
        final PersistentRolePermission rolePermissionSample;
        final RolePermission           rolePermission;
        final PersistentRolePermission created;

        log.debug("Adding permission {} for role {}", permission, roleId);

        rolePermission = DtoRolePermission.builder()
            .roleId(roleId)
            .permissionId(permission)
            .build();
        validatorAddRolePermission.validate(rolePermission);

        // Build relationship entities
        rolePermissionSample = getRolePermissionSample(roleId, permission);
        rolePermissionSample.setGranted(true);

        // Persist relationship entities
        created = rolePermissionRepository.save(rolePermissionSample);

        return rolePermissionMapper.toDto(created);
    }

    @Override
    public final Iterable<Permission> getAvailablePermissions(final long roleId, final Pageable pageable) {
        // TODO: test this
        return permissionRepository.findAvailableToRole(roleId, pageable)
            .map(permissionMapper::toDto);
    }

    @Override
    public final Iterable<Permission> getPermissions(final long roleId, final Pageable pageable) {
        return permissionRepository.findByRole(roleId, pageable)
            .map(permissionMapper::toDto);
    }

    @Override
    public final RolePermission removePermission(final long roleId, final Long permission) {
        final PersistentRolePermission       rolePermissionSample;
        final RolePermission                 rolePermission;
        final PersistentRolePermission       updated;
        final Optional<PersistentPermission> read;
        final RolePermission                 result;

        log.debug("Removing permission {} for role {}", permission, roleId);

        rolePermission = DtoRolePermission.builder()
            .roleId(roleId)
            .permissionId(permission)
            .build();
        validatorRemoveRolePermission.validate(rolePermission);

        read = permissionRepository.findById(permission);

        if (read.isPresent()) {
            // Build relationship entities
            rolePermissionSample = getRolePermissionSample(roleId, read.get()
                .getId());
            rolePermissionSample.setGranted(false);

            // Delete relationship entities
            updated = rolePermissionRepository.save(rolePermissionSample);

            result = rolePermissionMapper.toDto(updated);
        } else {
            result = DtoRolePermission.builder()
                .build();
        }

        return result;
    }

    private final PersistentRolePermission getRolePermissionSample(final long roleId, final long permissionId) {
        return PersistentRolePermission.builder()
            .roleId(roleId)
            .permissionId(permissionId)
            .build();
    }

}
