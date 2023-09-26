
package com.bernardomg.security.user.service;

import java.util.Objects;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Pageable;

import com.bernardomg.exception.InvalidIdException;
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

    private final RoleMapper            mapper;

    private final RoleRepository        roleRepository;

    private final Validator<RoleCreate> validatorCreateRole;

    private final Validator<Long>       validatorDeleteRole;

    private final Validator<RoleUpdate> validatorUpdateRole;

    public DefaultRoleService(final RoleRepository roleRepo, final UserRoleRepository userRoleRepo,
            final RoleMapper roleMapper) {
        super();

        roleRepository = Objects.requireNonNull(roleRepo);
        mapper = Objects.requireNonNull(roleMapper);

        validatorCreateRole = new CreateRoleValidator(roleRepo);
        validatorUpdateRole = new UpdateRoleValidator();
        validatorDeleteRole = new DeleteRoleValidator(roleRepo, userRoleRepo);
    }

    @Override
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
    public final Boolean delete(final long id) {

        log.debug("Deleting role {}", id);

        validatorDeleteRole.validate(id);

        roleRepository.deleteById(id);

        return true;
    }

    @Override
    public final Iterable<Role> getAll(final RoleQuery sample, final Pageable pageable) {
        final PersistentRole entitySample;

        log.debug("Reading roles with sample {} and pagination {}", sample, pageable);

        entitySample = mapper.toEntity(sample);

        return roleRepository.findAll(Example.of(entitySample), pageable)
            .map(mapper::toDto);
    }

    @Override
    public final Optional<Role> getOne(final long id) {

        log.debug("Reading role with id {}", id);

        if (!roleRepository.existsById(id)) {
            throw new InvalidIdException("role", id);
        }

        return roleRepository.findById(id)
            .map(mapper::toDto);
    }

    @Override
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
