
package com.bernardomg.security.user.authorization.service;

import java.util.Objects;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.bernardomg.security.permission.model.Permission;
import com.bernardomg.security.permission.model.mapper.PermissionMapper;
import com.bernardomg.security.permission.persistence.repository.RoleGrantedPermissionRepository;
import com.bernardomg.security.user.authorization.persistence.model.PersistentRolePermission;
import com.bernardomg.security.user.authorization.persistence.repository.ActionRepository;
import com.bernardomg.security.user.authorization.persistence.repository.ResourceRepository;
import com.bernardomg.security.user.authorization.persistence.repository.RolePermissionRepository;
import com.bernardomg.security.user.authorization.persistence.repository.RoleRepository;
import com.bernardomg.security.user.authorization.validation.AddRolePermissionValidator;
import com.bernardomg.security.user.model.DtoRolePermission;
import com.bernardomg.security.user.model.RolePermission;
import com.bernardomg.security.user.model.mapper.RolePermissionMapper;
import com.bernardomg.validation.Validator;

@Service
public final class DefaultRolePermissionService implements RolePermissionService {

    private static final String                   PERMISSION_CACHE_NAME     = "security_role_permission";

    private static final String                   PERMISSION_SET_CACHE_NAME = "security_permission_set";

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
    @PreAuthorize("hasAuthority('ROLE:UPDATE')")
    @CacheEvict(cacheNames = { PERMISSION_SET_CACHE_NAME, PERMISSION_CACHE_NAME }, allEntries = true)
    public final RolePermission addPermission(final long id, final long resource, final long action) {
        final PersistentRolePermission rolePermissionSample;
        final RolePermission           roleAction;
        final PersistentRolePermission created;

        roleAction = DtoRolePermission.builder()
            .roleId(id)
            .resourceId(resource)
            .actionId(action)
            .build();
        validatorAddRolePermission.validate(roleAction);

        // Build relationship entities
        rolePermissionSample = getRolePermissionSample(id, resource, action);
        rolePermissionSample.setGranted(true);

        // Persist relationship entities
        created = rolePermissionRepository.save(rolePermissionSample);

        return rolePermissionMapper.toDto(created);
    }

    @Override
    @PreAuthorize("hasAuthority('ROLE:READ')")
    @Cacheable(cacheNames = PERMISSION_CACHE_NAME)
    public final Iterable<Permission> getPermissions(final long id, final Pageable pageable) {
        // TODO: Maybe this should be extracted
        return roleGrantedPermissionRepository.findAllByRoleId(id, pageable)
            .map(permissionMapper::toDto);
    }

    @Override
    @PreAuthorize("hasAuthority('ROLE:UPDATE')")
    @CacheEvict(cacheNames = { PERMISSION_SET_CACHE_NAME, PERMISSION_CACHE_NAME }, allEntries = true)
    public final RolePermission removePermission(final long id, final long resource, final long action) {
        final PersistentRolePermission rolePermissionSample;
        final RolePermission           roleAction;
        final PersistentRolePermission updated;

        roleAction = DtoRolePermission.builder()
            .roleId(id)
            .resourceId(resource)
            .actionId(action)
            .build();
        validatorRemoveRolePermission.validate(roleAction);

        // Build relationship entities
        rolePermissionSample = getRolePermissionSample(id, resource, action);
        rolePermissionSample.setGranted(false);

        // Delete relationship entities
        updated = rolePermissionRepository.save(rolePermissionSample);

        return rolePermissionMapper.toDto(updated);
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
