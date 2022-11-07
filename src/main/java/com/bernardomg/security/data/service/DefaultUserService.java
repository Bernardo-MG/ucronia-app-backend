
package com.bernardomg.security.data.service;

import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.bernardomg.security.data.model.DtoUser;
import com.bernardomg.security.data.model.Role;
import com.bernardomg.security.data.model.User;
import com.bernardomg.security.data.persistence.model.PersistentUser;
import com.bernardomg.security.data.persistence.model.PersistentUserRoles;
import com.bernardomg.security.data.persistence.repository.UserRepository;
import com.bernardomg.security.data.persistence.repository.UserRolesRepository;
import com.bernardomg.security.data.validation.user.RoleInUserUpdateValidator;
import com.bernardomg.security.data.validation.user.UserCreateValidator;
import com.bernardomg.security.data.validation.user.UserDeleteValidator;
import com.bernardomg.security.data.validation.user.UserRoleUpdateValidator;
import com.bernardomg.security.data.validation.user.UserUpdateValidator;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public final class DefaultUserService implements UserService {

    private final UserDeleteValidator       deleteValidator;

    private final UserRepository            repository;

    private final RoleInUserUpdateValidator roleUpdateValidator;

    private final UserCreateValidator       userCreateValidator;

    private final UserRolesRepository       userRolesRepository;

    private final UserRoleUpdateValidator   userRoleUpdateValidator;

    private final UserUpdateValidator       userUpdateValidator;

    @Override
    public final Boolean addRole(final Long id, final Long role) {
        final PersistentUserRoles relationship;
        final DtoUser             user;

        user = new DtoUser();
        user.setId(id);

        userRoleUpdateValidator.validate(user);
        roleUpdateValidator.validate(role);

        relationship = getRelationships(id, role);

        // Persist relationship
        userRolesRepository.save(relationship);

        return true;
    }

    @Override
    public final User create(final User user) {
        final PersistentUser entity;
        final PersistentUser created;

        userCreateValidator.validate(user);

        entity = toEntity(user);
        entity.setId(null);
        entity.setPassword("");

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
            .map(this::toDto);
    }

    @Override
    public final Optional<? extends User> getOne(final Long id) {
        return repository.findById(id)
            .map(this::toDto);
    }

    @Override
    public final Iterable<Role> getRoles(final Long id, final Pageable pageable) {
        return repository.findAllRoles(id, pageable);
    }

    @Override
    public final Boolean removeRole(final Long id, final Long role) {
        final PersistentUserRoles relationship;
        final DtoUser             user;

        user = new DtoUser();
        user.setId(id);

        userRoleUpdateValidator.validate(user);
        roleUpdateValidator.validate(role);

        relationship = getRelationships(id, role);

        // Persist relationship
        userRolesRepository.delete(relationship);

        return true;
    }

    @Override
    public final User update(final User user) {
        final PersistentUser entity;
        final PersistentUser created;
        final PersistentUser old;

        userUpdateValidator.validate(user);

        entity = toEntity(user);
        entity.setPassword("");

        old = repository.findById(user.getId())
            .get();
        entity.setPassword(old.getPassword());

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
