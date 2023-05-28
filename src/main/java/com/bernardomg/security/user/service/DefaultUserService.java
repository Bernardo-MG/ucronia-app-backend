
package com.bernardomg.security.user.service;

import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.bernardomg.security.user.model.ImmutableUser;
import com.bernardomg.security.user.model.ImmutableUserRole;
import com.bernardomg.security.user.model.Role;
import com.bernardomg.security.user.model.User;
import com.bernardomg.security.user.model.UserRole;
import com.bernardomg.security.user.model.request.UserQueryRequest;
import com.bernardomg.security.user.persistence.model.PersistentUser;
import com.bernardomg.security.user.persistence.model.PersistentUserRoles;
import com.bernardomg.security.user.persistence.repository.RoleRepository;
import com.bernardomg.security.user.persistence.repository.UserRepository;
import com.bernardomg.security.user.persistence.repository.UserRolesRepository;
import com.bernardomg.security.user.service.validation.user.AddUserRoleValidator;
import com.bernardomg.security.user.service.validation.user.CreateUserValidator;
import com.bernardomg.security.user.service.validation.user.DeleteUserValidator;
import com.bernardomg.security.user.service.validation.user.UpdateUserValidator;
import com.bernardomg.validation.Validator;

@Service
public final class DefaultUserService implements UserService {

    private final Validator<UserRole> addUserRoleValidator;

    private final Validator<User>     createUserValidator;

    private final Validator<Long>     deleteUserValidator;

    private final Validator<UserRole> removeUserRoleValidator;

    private final Validator<User>     updateUserValidator;

    private final UserRepository      userRepository;

    private final UserRolesRepository userRolesRepository;

    public DefaultUserService(final UserRepository userRepo, final RoleRepository roleRepo,
            final UserRolesRepository userRolesRepo) {
        super();

        userRepository = userRepo;
        userRolesRepository = userRolesRepo;

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
    public final User create(final User user) {
        final PersistentUser entity;
        final PersistentUser created;

        createUserValidator.validate(user);

        entity = toEntity(user);
        entity.setId(null);
        entity.setPassword("");

        created = userRepository.save(entity);

        return toDto(created);
    }

    @Override
    public final Boolean delete(final Long id) {
        deleteUserValidator.validate(id);
        userRepository.deleteById(id);

        return true;
    }

    @Override
    public final Iterable<User> getAll(final UserQueryRequest sample, final Pageable pageable) {
        final PersistentUser entity;

        entity = toEntity(sample);

        return userRepository.findAll(Example.of(entity), pageable)
            .map(this::toDto);
    }

    @Override
    public final Optional<User> getOne(final Long id) {
        return userRepository.findById(id)
            .map(this::toDto);
    }

    @Override
    public final Iterable<Role> getRoles(final Long id, final Pageable pageable) {
        return userRepository.findAllRoles(id, pageable);
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
    public final User update(final User user) {
        final PersistentUser           entity;
        final PersistentUser           created;
        final Optional<PersistentUser> old;

        updateUserValidator.validate(user);

        entity = toEntity(user);
        entity.setPassword("");

        old = userRepository.findById(user.getId());
        if (old.isPresent()) {
            entity.setPassword(old.get()
                .getPassword());
        }

        created = userRepository.save(entity);

        return toDto(created);
    }

    private final PersistentUserRoles getRelationships(final Long user, final Long role) {
        return PersistentUserRoles.builder()
            .userId(user)
            .roleId(role)
            .build();
    }

    private final User toDto(final PersistentUser entity) {
        return ImmutableUser.builder()
            .id(entity.getId())
            .username(entity.getUsername())
            .name(entity.getName())
            .email(entity.getEmail())
            .credentialsExpired(entity.getCredentialsExpired())
            .enabled(entity.getEnabled())
            .expired(entity.getExpired())
            .locked(entity.getLocked())
            .build();
    }

    private final PersistentUser toEntity(final User data) {
        final String username;
        final String email;

        if (data.getUsername() != null) {
            username = data.getUsername()
                .toLowerCase();
        } else {
            username = null;
        }
        if (data.getEmail() != null) {
            email = data.getEmail()
                .toLowerCase();
        } else {
            email = null;
        }

        return PersistentUser.builder()
            .id(data.getId())
            .username(username)
            .email(email)
            .name(data.getName())
            .credentialsExpired(data.getCredentialsExpired())
            .enabled(data.getEnabled())
            .expired(data.getExpired())
            .locked(data.getLocked())
            .build();
    }

    private final PersistentUser toEntity(final UserQueryRequest data) {
        final String username;
        final String email;

        if (data.getUsername() != null) {
            username = data.getUsername()
                .toLowerCase();
        } else {
            username = null;
        }
        if (data.getEmail() != null) {
            email = data.getEmail()
                .toLowerCase();
        } else {
            email = null;
        }

        return PersistentUser.builder()
            .username(username)
            .email(email)
            .name(data.getName())
            .credentialsExpired(data.getCredentialsExpired())
            .enabled(data.getEnabled())
            .expired(data.getExpired())
            .locked(data.getLocked())
            .build();
    }

}
