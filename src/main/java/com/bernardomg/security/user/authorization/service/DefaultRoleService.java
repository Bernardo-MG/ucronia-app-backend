
package com.bernardomg.security.user.authorization.service;

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
import com.bernardomg.security.user.authorization.persistence.model.PersistentRole;
import com.bernardomg.security.user.authorization.persistence.repository.RolePermissionRepository;
import com.bernardomg.security.user.authorization.persistence.repository.RoleRepository;
import com.bernardomg.security.user.authorization.persistence.repository.UserRoleRepository;
import com.bernardomg.security.user.authorization.validation.CreateRoleValidator;
import com.bernardomg.security.user.authorization.validation.DeleteRoleValidator;
import com.bernardomg.security.user.authorization.validation.UpdateRoleValidator;
import com.bernardomg.security.user.model.Role;
import com.bernardomg.security.user.model.mapper.RoleMapper;
import com.bernardomg.security.user.model.request.RoleCreate;
import com.bernardomg.security.user.model.request.RoleQuery;
import com.bernardomg.security.user.model.request.RoleUpdate;
import com.bernardomg.validation.Validator;

@Service
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
