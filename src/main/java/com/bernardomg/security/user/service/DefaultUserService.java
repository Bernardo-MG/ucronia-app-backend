
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

    private static final String         CACHE_MULTIPLE            = "security_users";

    private static final String         CACHE_SINGLE              = "security_user";

    private static final String         PERMISSION_SET_CACHE_NAME = "security_permission_set";

    private static final String         ROLE_CACHE_NAME           = "security_user_role";

    private final UserMapper            mapper;

    private final RoleRepository        roleRepository;

    private final UserRepository        userRepository;

    private final UserRolesRepository   userRolesRepository;

    private final Validator<UserRole>   validatorAddUserRole;

    private final Validator<UserCreate> validatorCreateUser;

    private final Validator<Long>       validatorDeleteUser;

    private final Validator<UserRole>   validatorRemoveUserRole;

    private final Validator<UserUpdate> validatorUpdateUser;

    public DefaultUserService(final UserRepository userRepo, final RoleRepository roleRepo,
            final UserRolesRepository userRolesRepo, final UserMapper userMapper) {
        super();

        userRepository = Objects.requireNonNull(userRepo);
        userRolesRepository = Objects.requireNonNull(userRolesRepo);
        roleRepository = Objects.requireNonNull(roleRepo);
        mapper = Objects.requireNonNull(userMapper);

        validatorCreateUser = new CreateUserValidator(userRepo);
        validatorUpdateUser = new UpdateUserValidator(userRepo);
        validatorDeleteUser = new DeleteUserValidator();

        validatorAddUserRole = new AddUserRoleValidator(userRepo, roleRepo);
        validatorRemoveUserRole = new AddUserRoleValidator(userRepo, roleRepo);
    }

    @Override
    @CacheEvict(cacheNames = { PERMISSION_SET_CACHE_NAME, ROLE_CACHE_NAME }, allEntries = true)
    @PreAuthorize("hasAuthority('USER:UPDATE')")
    public final Boolean addRole(final long id, final long role) {
        final PersistentUserRoles userRoleSample;
        final UserRole            userRole;

        userRole = ImmutableUserRole.builder()
            .user(id)
            .role(role)
            .build();
        validatorAddUserRole.validate(userRole);

        userRoleSample = getUserRoleSample(id, role);

        // Persist relationship
        userRolesRepository.save(userRoleSample);

        // TODO: Return an object to store in the cache
        return true;
    }

    @Override
    @Caching(put = { @CachePut(cacheNames = CACHE_SINGLE, key = "#result.id") },
            evict = { @CacheEvict(cacheNames = CACHE_MULTIPLE, allEntries = true) })
    @PreAuthorize("hasAuthority('USER:CREATE')")
    public final User create(final UserCreate user) {
        final PersistentUser entity;
        final PersistentUser created;

        validatorCreateUser.validate(user);

        entity = mapper.toEntity(user);
        if (entity.getUsername() != null) {
            entity.setUsername(entity.getUsername()
                .toLowerCase());
        }
        if (entity.getEmail() != null) {
            entity.setEmail(entity.getEmail()
                .toLowerCase());
        }

        // TODO: Handle this better, disable until it has a password
        // TODO: Should be the DB default value
        entity.setPassword("");

        entity.setExpired(false);
        entity.setLocked(false);
        entity.setCredentialsExpired(false);

        created = userRepository.save(entity);

        return mapper.toDto(created);
    }

    @Override
    @Caching(evict = { @CacheEvict(cacheNames = CACHE_MULTIPLE, allEntries = true),
            @CacheEvict(cacheNames = CACHE_SINGLE, key = "#id") })
    @PreAuthorize("hasAuthority('USER:DELETE')")
    public final void delete(final long id) {

        if (!userRepository.existsById(id)) {
            throw new InvalidIdException(String.format("Failed delete. No user with id %s", id));
        }

        validatorDeleteUser.validate(id);
        userRepository.deleteById(id);
    }

    @Override
    @Cacheable(cacheNames = CACHE_MULTIPLE)
    @PreAuthorize("hasAuthority('USER:READ')")
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
    @Cacheable(cacheNames = CACHE_SINGLE, key = "#id")
    @PreAuthorize("hasAuthority('USER:READ')")
    public final Optional<User> getOne(final long id) {
        return userRepository.findById(id)
            .map(mapper::toDto);
    }

    @Override
    @Cacheable(cacheNames = ROLE_CACHE_NAME)
    @PreAuthorize("hasAuthority('USER:READ')")
    public final Iterable<Role> getRoles(final long id, final Pageable pageable) {
        return roleRepository.findForUser(id, pageable);
    }

    @Override
    @CacheEvict(cacheNames = { PERMISSION_SET_CACHE_NAME, ROLE_CACHE_NAME }, allEntries = true)
    @PreAuthorize("hasAuthority('USER:UPDATE')")
    public final Boolean removeRole(final long id, final long role) {
        final PersistentUserRoles userRoleSample;
        final UserRole            userRole;

        userRole = ImmutableUserRole.builder()
            .user(id)
            .role(role)
            .build();
        validatorRemoveUserRole.validate(userRole);

        userRoleSample = getUserRoleSample(id, role);

        // Persist relationship
        userRolesRepository.delete(userRoleSample);

        // TODO: Return an object to delete only that permission

        return true;
    }

    @Override
    @Caching(put = { @CachePut(cacheNames = CACHE_SINGLE, key = "#result.id") },
            evict = { @CacheEvict(cacheNames = CACHE_MULTIPLE, allEntries = true) })
    @PreAuthorize("hasAuthority('USER:UPDATE')")
    public final User update(final long id, final UserUpdate user) {
        final PersistentUser           entity;
        final PersistentUser           created;
        final Optional<PersistentUser> old;

        if (!userRepository.existsById(id)) {
            throw new InvalidIdException(String.format("Failed update. No user with id %s", id));
        }

        validatorUpdateUser.validate(user);

        entity = mapper.toEntity(user);
        entity.setId(id);
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

        // TODO: Should initialize the values in the database
        entity.setExpired(false);
        entity.setLocked(false);
        entity.setCredentialsExpired(false);

        created = userRepository.save(entity);

        return mapper.toDto(created);
    }

    private final PersistentUserRoles getUserRoleSample(final long user, final long role) {
        return PersistentUserRoles.builder()
            .userId(user)
            .roleId(role)
            .build();
    }

}
