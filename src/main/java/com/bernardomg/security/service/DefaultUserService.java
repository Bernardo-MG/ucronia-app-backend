
package com.bernardomg.security.service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.bernardomg.security.model.DtoRole;
import com.bernardomg.security.model.DtoUser;
import com.bernardomg.security.model.Role;
import com.bernardomg.security.model.User;
import com.bernardomg.security.persistence.model.PersistentRole;
import com.bernardomg.security.persistence.model.PersistentUser;
import com.bernardomg.security.persistence.model.PersistentUserRoles;
import com.bernardomg.security.persistence.repository.RoleRepository;
import com.bernardomg.security.persistence.repository.UserRepository;
import com.bernardomg.security.persistence.repository.UserRolesRepository;
import com.bernardomg.security.validation.user.UserCreateValidator;
import com.bernardomg.security.validation.user.UserDeleteValidator;
import com.bernardomg.security.validation.user.UserRoleUpdateValidator;
import com.bernardomg.security.validation.user.UserUpdateValidator;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public final class DefaultUserService implements UserService {

    private final UserDeleteValidator     deleteValidator;

    private final UserRepository          repository;

    private final RoleRepository          roleRepository;

    private final UserRoleUpdateValidator roleUpdateValidator;

    private final UserCreateValidator     userCreateValidator;

    private final UserRolesRepository     userRolesRepository;

    private final UserUpdateValidator     userUpdateValidator;

    @Override
    public final User create(final User user) {
        final PersistentUser entity;
        final PersistentUser created;

        userCreateValidator.validate(user);

        entity = toEntity(user);
        entity.setId(null);

        created = repository.save(entity);

        return toDto(created);
    }

    @Override
    public final Boolean delete(final Long id) {
        final DtoUser user;

        user = new DtoUser();
        user.setId(id);

        deleteValidator.validate(user);

        repository.deleteById(id);

        return true;
    }

    @Override
    public final Iterable<? extends User> getAll(final User sample, final Pageable pageable) {
        final PersistentUser entity;

        entity = toEntity(sample);
        return repository.findAll(Example.of(entity), pageable)
            .stream()
            .map(this::toDto)
            .collect(Collectors.toList());
    }

    @Override
    public final Optional<? extends User> getOne(final Long id) {
        return repository.findById(id)
            .map(this::toDto);
    }

    @Override
    public final Iterable<Role> getRoles(final Long id) {
        return repository.findAllRoles(id);
    }

    @Override
    public final Iterable<? extends Role> setRoles(final Long id, final Iterable<Long> roles) {
        final Collection<PersistentUserRoles> relationships;
        final Iterable<Long>                  ids;
        final List<PersistentUserRoles>       created;
        final List<PersistentRole>            addedRoles;
        final PersistentUserRoles             relSample;
        final Collection<PersistentUserRoles> rels;
        final DtoUser                         user;

        user = new DtoUser();
        user.setId(id);

        userUpdateValidator.validate(user);

        StreamSupport.stream(roles.spliterator(), false)
            .forEach(p -> roleUpdateValidator.validate(p));

        // Removes exiting relationships
        relSample = new PersistentUserRoles();
        relSample.setUserId(id);
        rels = userRolesRepository.findAll(Example.of(relSample));
        userRolesRepository.deleteAll(rels);

        // Build relationship entities
        relationships = StreamSupport.stream(roles.spliterator(), false)
            .map(p -> getRelationships(id, p))
            .collect(Collectors.toList());

        // Persist relationship entities
        created = userRolesRepository.saveAll(relationships);

        // Get privileges added to the role
        ids = created.stream()
            .map(PersistentUserRoles::getRoleId)
            .collect(Collectors.toList());
        addedRoles = roleRepository.findAllById(ids);

        return addedRoles.stream()
            .map(this::toDto)
            .collect(Collectors.toList());
    }

    @Override
    public final User update(final User user) {
        final PersistentUser entity;
        final PersistentUser created;

        userUpdateValidator.validate(user);

        entity = toEntity(user);

        created = repository.save(entity);

        return toDto(created);
    }

    private final PersistentUserRoles getRelationships(final Long user, final Long role) {
        final PersistentUserRoles relationship;

        relationship = new PersistentUserRoles();
        relationship.setUserId(user);
        relationship.setRoleId(role);

        return relationship;
    }

    private final Role toDto(final PersistentRole entity) {
        final DtoRole data;

        data = new DtoRole();
        data.setId(entity.getId());
        data.setName(entity.getName());

        return data;
    }

    private final User toDto(final PersistentUser entity) {
        final DtoUser data;

        data = new DtoUser();
        data.setId(entity.getId());
        data.setUsername(entity.getUsername());
        data.setEmail(entity.getEmail());
        data.setCredentialsExpired(entity.getCredentialsExpired());
        data.setEnabled(entity.getEnabled());
        data.setExpired(entity.getExpired());
        data.setLocked(entity.getLocked());

        return data;
    }

    private final PersistentUser toEntity(final User data) {
        final PersistentUser entity;

        entity = new PersistentUser();
        entity.setId(data.getId());
        entity.setUsername(data.getUsername());
        entity.setEmail(data.getEmail());
        entity.setCredentialsExpired(data.getCredentialsExpired());
        entity.setEnabled(data.getEnabled());
        entity.setExpired(data.getExpired());
        entity.setLocked(data.getLocked());

        return entity;
    }

}
