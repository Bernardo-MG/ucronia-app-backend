
package com.bernardomg.security.user.service;

import java.util.Objects;
import java.util.Optional;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.bernardomg.exception.InvalidIdException;
import com.bernardomg.security.permission.persistence.repository.RoleGrantedPermissionRepository;
import com.bernardomg.security.user.model.DtoRolePermission;
import com.bernardomg.security.user.model.Permission;
import com.bernardomg.security.user.model.Role;
import com.bernardomg.security.user.model.RolePermission;
import com.bernardomg.security.user.model.mapper.RoleMapper;
import com.bernardomg.security.user.model.mapper.RolePermissionMapper;
import com.bernardomg.security.user.model.request.RoleCreate;
import com.bernardomg.security.user.model.request.RoleQuery;
import com.bernardomg.security.user.model.request.RoleUpdate;
import com.bernardomg.security.user.persistence.model.PersistentRole;
import com.bernardomg.security.user.persistence.model.PersistentRolePermission;
import com.bernardomg.security.user.persistence.repository.ActionRepository;
import com.bernardomg.security.user.persistence.repository.ResourceRepository;
import com.bernardomg.security.user.persistence.repository.RolePermissionRepository;
import com.bernardomg.security.user.persistence.repository.RoleRepository;
import com.bernardomg.security.user.persistence.repository.UserRoleRepository;
import com.bernardomg.security.user.validation.role.AddRolePermissionValidator;
import com.bernardomg.security.user.validation.role.CreateRoleValidator;
import com.bernardomg.security.user.validation.role.DeleteRoleValidator;
import com.bernardomg.security.user.validation.role.UpdateRoleValidator;
import com.bernardomg.validation.Validator;

@Service
public final class DefaultRoleService implements RoleService {

    private static final String                   CACHE_MULTIPLE            = "security_roles";

    private static final String                   CACHE_SINGLE              = "security_role";

    private static final String                   PERMISSION_CACHE_NAME     = "security_role_permission";

    private static final String                   PERMISSION_SET_CACHE_NAME = "security_permission_set";

    private final RoleMapper                      mapper;

    private final RoleGrantedPermissionRepository roleGrantedPermissionRepository;

    private final RolePermissionMapper            rolePermissionMapper;

    private final RolePermissionRepository        rolePermissionRepository;

    private final RoleRepository                  roleRepository;

    private final Validator<RolePermission>       validatorAddRolePermission;

    private final Validator<RoleCreate>           validatorCreateRole;

    private final Validator<Long>                 validatorDeleteRole;

    private final Validator<RolePermission>       validatorRemoveRolePermission;

    private final Validator<RoleUpdate>           validatorUpdateRole;

    public DefaultRoleService(final RoleRepository roleRepo, final ResourceRepository resourceRepo,
            final ActionRepository actionRepo, final RolePermissionRepository roleActionsRepo,
            final UserRoleRepository userRoleRepo, final RoleGrantedPermissionRepository roleGrantedPermissionRepo,
            final RoleMapper roleMapper, final RolePermissionMapper permissionMapper) {
        super();

        roleRepository = Objects.requireNonNull(roleRepo);
        rolePermissionRepository = Objects.requireNonNull(roleActionsRepo);
        roleGrantedPermissionRepository = Objects.requireNonNull(roleGrantedPermissionRepo);
        mapper = Objects.requireNonNull(roleMapper);
        rolePermissionMapper = Objects.requireNonNull(permissionMapper);

        validatorCreateRole = new CreateRoleValidator(roleRepo);
        validatorUpdateRole = new UpdateRoleValidator();
        validatorDeleteRole = new DeleteRoleValidator(roleRepo, userRoleRepo);

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
    @PreAuthorize("hasAuthority('ROLE:CREATE')")
    @Caching(put = { @CachePut(cacheNames = CACHE_SINGLE, key = "#result.id") },
            evict = { @CacheEvict(cacheNames = CACHE_MULTIPLE, allEntries = true) })
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
    @Caching(evict = { @CacheEvict(cacheNames = CACHE_MULTIPLE, allEntries = true),
            @CacheEvict(cacheNames = CACHE_SINGLE, key = "#id") })
    public final Boolean delete(final long id) {
        validatorDeleteRole.validate(id);

        rolePermissionRepository.deleteAllByRoleId(id);
        roleRepository.deleteById(id);

        return true;
    }

    @Override
    @PreAuthorize("hasAuthority('ROLE:READ')")
    @Cacheable(cacheNames = CACHE_MULTIPLE)
    public final Iterable<Role> getAll(final RoleQuery sample, final Pageable pageable) {
        final PersistentRole entitySample;

        entitySample = mapper.toEntity(sample);

        return roleRepository.findAll(Example.of(entitySample), pageable)
            .map(mapper::toDto);
    }

    @Override
    @PreAuthorize("hasAuthority('ROLE:READ')")
    @Cacheable(cacheNames = CACHE_SINGLE, key = "#id")
    public final Optional<Role> getOne(final long id) {
        return roleRepository.findById(id)
            .map(mapper::toDto);
    }

    @Override
    @PreAuthorize("hasAuthority('ROLE:READ')")
    @Cacheable(cacheNames = PERMISSION_CACHE_NAME)
    public final Iterable<Permission> getPermissions(final long id, final Pageable pageable) {
        // TODO: Maybe this should be extracted
        return roleGrantedPermissionRepository.findAllByRoleId(id, pageable)
            .map(mapper::toDto);
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

    @Override
    @PreAuthorize("hasAuthority('ROLE:UPDATE')")
    @Caching(put = { @CachePut(cacheNames = CACHE_SINGLE, key = "#result.id") },
            evict = { @CacheEvict(cacheNames = CACHE_MULTIPLE, allEntries = true) })
    public final Role update(final long id, final RoleUpdate role) {
        final PersistentRole entity;
        final PersistentRole created;

        if (!roleRepository.existsById(id)) {
            throw new InvalidIdException(String.format("Failed update. No role with id %s", id));
        }

        validatorUpdateRole.validate(role);

        entity = mapper.toEntity(role);
        entity.setId(id);

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
