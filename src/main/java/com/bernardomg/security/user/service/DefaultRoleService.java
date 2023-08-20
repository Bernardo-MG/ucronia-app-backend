
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

import com.bernardomg.exception.InvalidIdException;
import com.bernardomg.security.permission.persistence.repository.RolePermissionRepository;
import com.bernardomg.security.user.model.Role;
import com.bernardomg.security.user.model.mapper.RoleMapper;
import com.bernardomg.security.user.model.request.RoleCreate;
import com.bernardomg.security.user.model.request.RoleQuery;
import com.bernardomg.security.user.model.request.RoleUpdate;
import com.bernardomg.security.user.persistence.model.PersistentRole;
import com.bernardomg.security.user.persistence.repository.RoleRepository;
import com.bernardomg.security.user.persistence.repository.UserRoleRepository;
import com.bernardomg.security.user.validation.CreateRoleValidator;
import com.bernardomg.security.user.validation.DeleteRoleValidator;
import com.bernardomg.security.user.validation.UpdateRoleValidator;
import com.bernardomg.validation.Validator;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class DefaultRoleService implements RoleService {

    private static final String            CACHE_MULTIPLE = "security_roles";

    private static final String            CACHE_SINGLE   = "security_role";

    private final RoleMapper               mapper;

    private final RolePermissionRepository rolePermissionRepository;

    private final RoleRepository           roleRepository;

    private final Validator<RoleCreate>    validatorCreateRole;

    private final Validator<Long>          validatorDeleteRole;

    private final Validator<RoleUpdate>    validatorUpdateRole;

    public DefaultRoleService(final RoleRepository roleRepo, final RolePermissionRepository roleActionsRepo,
            final UserRoleRepository userRoleRepo, final RoleMapper roleMapper) {
        super();

        roleRepository = Objects.requireNonNull(roleRepo);
        rolePermissionRepository = Objects.requireNonNull(roleActionsRepo);
        mapper = Objects.requireNonNull(roleMapper);

        validatorCreateRole = new CreateRoleValidator(roleRepo);
        validatorUpdateRole = new UpdateRoleValidator();
        validatorDeleteRole = new DeleteRoleValidator(roleRepo, userRoleRepo);
    }

    @Override
    @PreAuthorize("hasAuthority('ROLE:CREATE')")
    @Caching(put = { @CachePut(cacheNames = CACHE_SINGLE, key = "#result.id") },
            evict = { @CacheEvict(cacheNames = CACHE_MULTIPLE, allEntries = true) })
    public final Role create(final RoleCreate role) {
        final PersistentRole entity;
        final PersistentRole created;

        log.debug("Creating role {}", role);

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

        log.debug("Deleting role {}", id);

        validatorDeleteRole.validate(id);

        // TODO: use delete in cascade
        rolePermissionRepository.deleteAllByRoleId(id);
        roleRepository.deleteById(id);

        return true;
    }

    @Override
    @PreAuthorize("hasAuthority('ROLE:READ')")
    @Cacheable(cacheNames = CACHE_MULTIPLE)
    public final Iterable<Role> getAll(final RoleQuery sample, final Pageable pageable) {
        final PersistentRole entitySample;

        log.debug("Reading roles with sample {} and pagination {}", sample, pageable);

        entitySample = mapper.toEntity(sample);

        return roleRepository.findAll(Example.of(entitySample), pageable)
            .map(mapper::toDto);
    }

    @Override
    @PreAuthorize("hasAuthority('ROLE:READ')")
    @Cacheable(cacheNames = CACHE_SINGLE, key = "#id")
    public final Optional<Role> getOne(final long id) {

        log.debug("Reading role with id {}", id);

        if (!roleRepository.existsById(id)) {
            throw new InvalidIdException("role", id);
        }

        return roleRepository.findById(id)
            .map(mapper::toDto);
    }

    @Override
    @PreAuthorize("hasAuthority('ROLE:UPDATE')")
    @Caching(put = { @CachePut(cacheNames = CACHE_SINGLE, key = "#result.id") },
            evict = { @CacheEvict(cacheNames = CACHE_MULTIPLE, allEntries = true) })
    public final Role update(final long id, final RoleUpdate role) {
        final PersistentRole entity;
        final PersistentRole created;

        log.debug("Updating role with id {} using data {}", id, role);

        if (!roleRepository.existsById(id)) {
            throw new InvalidIdException("role", id);
        }

        validatorUpdateRole.validate(role);

        entity = mapper.toEntity(role);
        entity.setId(id);

        created = roleRepository.save(entity);

        return mapper.toDto(created);
    }

}
