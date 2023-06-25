
package com.bernardomg.security.user.service;

import java.util.Objects;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.bernardomg.security.user.model.ImmutableUserRole;
import com.bernardomg.security.user.model.Role;
import com.bernardomg.security.user.model.User;
import com.bernardomg.security.user.model.UserRole;
import com.bernardomg.security.user.model.mapper.UserMapper;
import com.bernardomg.security.user.model.request.UserCreate;
import com.bernardomg.security.user.model.request.UserQuery;
import com.bernardomg.security.user.model.request.UserUpdate;
import com.bernardomg.security.user.persistence.model.PersistentUser;
import com.bernardomg.security.user.persistence.model.PersistentUserRoles;
import com.bernardomg.security.user.persistence.repository.RoleRepository;
import com.bernardomg.security.user.persistence.repository.UserRepository;
import com.bernardomg.security.user.persistence.repository.UserRolesRepository;
import com.bernardomg.security.user.validation.user.AddUserRoleValidator;
import com.bernardomg.security.user.validation.user.CreateUserValidator;
import com.bernardomg.security.user.validation.user.DeleteUserValidator;
import com.bernardomg.security.user.validation.user.UpdateUserValidator;
import com.bernardomg.validation.Validator;

@Service
public final class DefaultUserService implements UserService {

    private final Validator<UserRole>   addUserRoleValidator;

    private final Validator<UserCreate> createUserValidator;

    private final Validator<Long>       deleteUserValidator;

    private final UserMapper            mapper;

    private final Validator<UserRole>   removeUserRoleValidator;

    private final RoleRepository        roleRepository;

    private final Validator<UserUpdate> updateUserValidator;

    private final UserRepository        userRepository;

    private final UserRolesRepository   userRolesRepository;

    public DefaultUserService(final UserRepository userRepo, final RoleRepository roleRepo,
            final UserRolesRepository userRolesRepo, final UserMapper userMapper) {
        super();

        userRepository = Objects.requireNonNull(userRepo);
        userRolesRepository = Objects.requireNonNull(userRolesRepo);
        roleRepository = Objects.requireNonNull(roleRepo);
        mapper = Objects.requireNonNull(userMapper);

        createUserValidator = new CreateUserValidator(userRepo);
        updateUserValidator = new UpdateUserValidator(userRepo);
        deleteUserValidator = new DeleteUserValidator(userRepo);

        addUserRoleValidator = new AddUserRoleValidator(userRepo, roleRepo);
        removeUserRoleValidator = new AddUserRoleValidator(userRepo, roleRepo);
    }

    @Override
    public final Boolean addRole(final Long id, final Long role) {
        final PersistentUserRoles relationship;
        final UserRole            userRole;

        userRole = ImmutableUserRole.builder()
            .user(id)
            .role(role)
            .build();
        addUserRoleValidator.validate(userRole);

        relationship = getRelationships(id, role);

        // Persist relationship
        userRolesRepository.save(relationship);

        return true;
    }

    @Override
    public final User create(final UserCreate user) {
        final PersistentUser entity;
        final PersistentUser created;

        createUserValidator.validate(user);

        entity = mapper.toEntity(user);
        entity.setId(null);
        entity.setPassword("");
        if (entity.getUsername() != null) {
            entity.setUsername(entity.getUsername()
                .toLowerCase());
        }
        if (entity.getEmail() != null) {
            entity.setEmail(entity.getEmail()
                .toLowerCase());
        }

        entity.setExpired(false);
        entity.setLocked(false);
        entity.setCredentialsExpired(false);

        created = userRepository.save(entity);

        return mapper.toDto(created);
    }

    @Override
    public final Boolean delete(final Long id) {
        deleteUserValidator.validate(id);
        userRepository.deleteById(id);

        return true;
    }

    @Override
    public final Iterable<User> getAll(final UserQuery sample, final Pageable pageable) {
        final PersistentUser entity;

        entity = mapper.toEntity(sample);
        if (entity.getUsername() != null) {
            entity.setUsername(entity.getUsername()
                .toLowerCase());
        }
        if (entity.getEmail() != null) {
            entity.setEmail(entity.getEmail()
                .toLowerCase());
        }

        return userRepository.findAll(Example.of(entity), pageable)
            .map(mapper::toDto);
    }

    @Override
    public final Optional<User> getOne(final Long id) {
        return userRepository.findById(id)
            .map(mapper::toDto);
    }

    @Override
    public final Iterable<Role> getRoles(final Long id, final Pageable pageable) {
        return roleRepository.findForUser(id, pageable);
    }

    @Override
    public final Boolean removeRole(final Long id, final Long role) {
        final PersistentUserRoles relationship;
        final UserRole            userRole;

        userRole = ImmutableUserRole.builder()
            .user(id)
            .role(role)
            .build();
        removeUserRoleValidator.validate(userRole);

        relationship = getRelationships(id, role);

        // Persist relationship
        userRolesRepository.delete(relationship);

        return true;
    }

    @Override
    public final User update(final UserUpdate user) {
        final PersistentUser           entity;
        final PersistentUser           created;
        final Optional<PersistentUser> old;

        updateUserValidator.validate(user);

        entity = mapper.toEntity(user);
        entity.setPassword("");
        if (entity.getUsername() != null) {
            entity.setUsername(entity.getUsername()
                .toLowerCase());
        }
        if (entity.getEmail() != null) {
            entity.setEmail(entity.getEmail()
                .toLowerCase());
        }

        old = userRepository.findById(user.getId());
        if (old.isPresent()) {
            entity.setPassword(old.get()
                .getPassword());
        }

        entity.setExpired(false);
        entity.setLocked(false);
        entity.setCredentialsExpired(false);

        created = userRepository.save(entity);

        return mapper.toDto(created);
    }

    private final PersistentUserRoles getRelationships(final Long user, final Long role) {
        return PersistentUserRoles.builder()
            .userId(user)
            .roleId(role)
            .build();
    }

}
